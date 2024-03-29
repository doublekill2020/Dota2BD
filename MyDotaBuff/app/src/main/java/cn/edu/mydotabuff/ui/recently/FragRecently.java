package cn.edu.mydotabuff.ui.recently;



import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.DotaApplication.LocalDataType;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.model.MatchBean;
import cn.edu.mydotabuff.model.PlayerBean;
import cn.edu.mydotabuff.common.http.IInfoReceive;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.view.LoadingDialog;
import cn.edu.mydotabuff.view.TipsToast;
import cn.edu.mydotabuff.view.XListView;
import cn.edu.mydotabuff.view.TipsToast.DialogType;
import cn.edu.mydotabuff.view.XListView.IXListViewListener;

public class FragRecently extends BaseFragment {
	private XListView listView;
	private View recentLayout;
	private String userID;
	private Activity activity;
	private FragItemAdapter mAdapter;
	private LoadingDialog dialog;
	private ArrayList<String> matchIds;
	private ArrayList<MatchBean> allMatchBeans;
	private static final int FETCH_MATCH = 1;
	private static final int FETCH_ONLINE_NUM = 2;
	private static final int FETCH_HERO_LIST = 3;
	private static final int FETCH_FAILED = 4;
	private static final int NO_DATA = 5;// 玩家没有开启比赛数据共享
	private String lastId = "";
	private MyHandler myHandler;
	private TextView onlineNum;
	private SharedPreferences myPreferences;
	private boolean flag = false;// 当拿本地数据时 会导致加载更多的第一天与本地最后一条重复，此变量用来标识

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		recentLayout = inflater.inflate(R.layout.frag_recently, container,
				false);
		setHasOptionsMenu(true);
		activity = getActivity();
		dialog = new LoadingDialog(activity);
		// 获得用户ID
		myPreferences = activity.getSharedPreferences("user_info",
				Activity.MODE_PRIVATE);
		userID = myPreferences.getString("userID", "");
		// initView();
		myHandler = new MyHandler();
		matchIds = new ArrayList<String>();
		allMatchBeans = new ArrayList<MatchBean>();
		initView();
		fetchData(FETCH_ONLINE_NUM, "");
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				DotaApplication.getApplication().destoryData(
						LocalDataType.MATCHES);
				mAdapter = null;
				allMatchBeans.clear();
				lastId = "";
				fetchData(FETCH_MATCH, lastId);
			}

			@Override
			public void onLoadMore() {
				fetchData(FETCH_MATCH, lastId);
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				if (allMatchBeans.size() > 0) {
					Intent intent = new Intent(activity, ActMatchDetail.class);
					// Bundle mBundle = new Bundle();
					ArrayList<String> ids = new ArrayList<String>();
					for (PlayerBean bean : allMatchBeans.get(position - 1)
							.getPlayers()) {
						ids.add(bean.getAccountId());
					}
					// mBundle.putSerializable("MatchBean",
					// allMatchBeans.get(position - 1).getPlayers());
					// intent.putExtras(mBundle);
					intent.putExtra("matchId", allMatchBeans.get(position - 1)
							.getMatchId());
					intent.putStringArrayListExtra("ids", ids);
					startActivity(intent);
				}
			}
		});
		return recentLayout;
	}

	public void initView() {
		listView = (XListView) recentLayout
				.findViewById(R.id.frag_player_details_list);
		listView.setVerticalScrollBarEnabled(true);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(false);
		onlineNum = (TextView) recentLayout.findViewById(R.id.online_num);
	}

	void fetchData(final int type, final String lastId) {
		PersonalRequestImpl request = new PersonalRequestImpl(
				new IInfoReceive() {

					@Override
					public void onMsgReceiver(ResponseObj receiveInfo) {
						// TODO Auto-generated method stub
						Message msg = myHandler.obtainMessage();
						switch (type) {
						case FETCH_MATCH:
							ArrayList<MatchBean> matches = new ArrayList<MatchBean>();
							String num1 = "";
							try {
								JSONObject jsonObj = new JSONObject(receiveInfo
										.getJsonStr());
								if (jsonObj.has("result")) {
									JSONObject resultObj = jsonObj
											.getJSONObject("result");
									JSONArray matchesArr = resultObj
											.getJSONArray("matches");
									for (int i = 0; i < matchesArr.length(); i++) {
										JSONObject matchObj = matchesArr
												.getJSONObject(i);
										String matchId = matchObj
												.getString("match_id");
										num1 = matchId;
										if (i == matchesArr.length() - 1) {
											FragRecently.this.lastId = matchId;
											break;
										}
										matchIds.add(matchId);
										String startTime = matchObj
												.getString("start_time");
										int lobbyType = matchObj
												.getInt("lobby_type");
										int radiantId = matchObj
												.getInt("radiant_team_id");
										int direId = matchObj
												.getInt("dire_team_id");
										JSONArray players = matchObj
												.getJSONArray("players");
										ArrayList<PlayerBean> playerList = new ArrayList<PlayerBean>();
										for (int j = 0; j < players.length(); j++) {
											JSONObject player = players
													.getJSONObject(j);
											String accountId = "";
											if (player.has("account_id")) {
												accountId = player
														.getString("account_id");
											} else {
												// 没有accountid时 为电脑
												accountId = "000000000";
											}
											int playerSlot = player
													.getInt("player_slot");
											int heroId = player
													.getInt("hero_id");
											playerList.add(new PlayerBean(
													accountId, playerSlot,
													heroId));
										}
										matches.add(new MatchBean(matchId,
												startTime, lobbyType,
												radiantId, direId, playerList));
									}
									if (matches.size() > 0) {
										msg.arg1 = type;
										// 去除重复数据
										if (flag) {
											matches.remove(matches.size() - 1);
										}
										msg.obj = matches;
									} else {
										msg.arg1 = NO_DATA;
									}
								} else {
									msg.arg1 = FETCH_FAILED;
								}
							} catch (JSONException e) {
								e.printStackTrace();
								msg.arg1 = FETCH_FAILED;
							}
							break;
						case FETCH_HERO_LIST:
							HashMap<Integer, String> map = new HashMap<Integer, String>();
							try {
								JSONObject jsonObj = new JSONObject(receiveInfo
										.getJsonStr());
								JSONObject resultObj = jsonObj
										.getJSONObject("result");
								JSONArray matchesArr = resultObj
										.getJSONArray("heroes");
								for (int i = 0; i < matchesArr.length(); i++) {
									JSONObject matchObj = matchesArr
											.getJSONObject(i);
									map.put(matchObj.getInt("id"), matchObj
											.getString("localized_name"));
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
							// if (map.size() > 0) {
							// DotaApplication.getApplication().setHeroes(map);
							// }
							break;
						case FETCH_ONLINE_NUM:
							String num = "";
							try {
								JSONObject jsonObj = new JSONObject(receiveInfo
										.getJsonStr());
								if (jsonObj.has("response")) {
									JSONObject resultObj = jsonObj
											.getJSONObject("response");
									num = resultObj.getString("player_count");
									msg.arg1 = type;
									msg.obj = num;
								} else {
									msg.arg1 = type;
									msg.obj = "未知";
								}
							} catch (JSONException e) {
								e.printStackTrace();
								msg.arg1 = type;
								msg.obj = "未知";
							}
							break;
						default:
							break;
						}
						myHandler.sendMessage(msg);
					}

				});
		request.setActivity(activity);
		switch (type) {
		case FETCH_MATCH:
			request.getMatchHistory(userID, lastId);
			break;
		case FETCH_HERO_LIST:
			request.getHeroList();
			break;
		case FETCH_ONLINE_NUM:
			request.getOnlineNum();
		default:
			break;
		}
	}

	class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			case FETCH_MATCH:
				listView.setPullLoadEnable(true);
				listView.stopLoadMore();
				listView.stopRefresh();
				ArrayList<MatchBean> beans = (ArrayList<MatchBean>) msg.obj;
				if (mAdapter == null) {
					mAdapter = new FragItemAdapter(activity, beans);
					listView.setAdapter(mAdapter);
					allMatchBeans.addAll(beans);
				} else {
					allMatchBeans.addAll(beans);
					mAdapter.addMoreData(beans);
                    mAdapter.notifyDataSetChanged();
				}
				break;
			case FETCH_ONLINE_NUM:
				String num = (String) msg.obj;
				onlineNum.setText("当前在线人数：" + num);
				break;
			case FETCH_FAILED:
				dialog.cancel();
				TipsToast.showToast(activity, "steam被墙了，你懂得",
						Toast.LENGTH_SHORT, DialogType.LOAD_FAILURE);
				listView.setPullLoadEnable(false);
				listView.setPullRefreshEnable(true);
				listView.stopLoadMore();
				listView.stopRefresh();
				break;
			case NO_DATA:
				dialog.cancel();
				TipsToast.showToast(activity, "没有更多数据~", Toast.LENGTH_SHORT,
						DialogType.LOAD_FAILURE);
				listView.setPullLoadEnable(false);
				listView.setPullRefreshEnable(true);
				listView.stopLoadMore();
				listView.stopRefresh();
			default:
				break;
			}
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		DotaApplication.getApplication().saveData(allMatchBeans,
				LocalDataType.MATCHES);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		inflater.inflate(R.menu.frag_recently_menu, menu);
		final SearchView searchView = (SearchView) menu.findItem(
				R.id.action_search).getActionView();
		searchView.setQueryHint("输入比赛ID");
		searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// TODO Auto-generated method stub
				if (arg0.length() == 10) {
					Intent intent = new Intent(activity, ActMatchDetail.class);
					intent.putExtra("matchId", arg0);
					startActivity(intent);
				} else {
					Toast.makeText(activity, "比赛ID有误，请重新输入~",
							Toast.LENGTH_SHORT).show();
				}
				return false;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

}
