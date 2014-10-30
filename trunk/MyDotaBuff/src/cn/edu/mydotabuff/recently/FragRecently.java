package cn.edu.mydotabuff.recently;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.APIConstants;
import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.MainActivity;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.bean.AbilityBean;
import cn.edu.mydotabuff.bean.MatchBean;
import cn.edu.mydotabuff.bean.PlayerBean;
import cn.edu.mydotabuff.bean.PlayerDetailBean;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.custom.LoadingDialog;
import cn.edu.mydotabuff.custom.TipsToast;
import cn.edu.mydotabuff.custom.TipsToast.DialogType;
import cn.edu.mydotabuff.http.IInfoReceive;
import cn.edu.mydotabuff.http.IInfoReceive.ResponseObj;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.view.XListView;
import cn.edu.mydotabuff.view.XListView.IXListViewListener;

public class FragRecently extends Fragment {
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
	private String lastId = "";
	private MyHandler myHandler;
	private View convertView;
	private ImageView h[] = new ImageView[11];
	private TextView onlineNum;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		recentLayout = inflater.inflate(R.layout.frag_recently, container,
				false);
		convertView = inflater.inflate(R.layout.frag_recently_item, container,
				false);
		h[1] = (ImageView) convertView.findViewById(R.id.hero_1);
		h[2] = (ImageView) convertView.findViewById(R.id.hero_2);
		h[3] = (ImageView) convertView.findViewById(R.id.hero_3);
		h[4] = (ImageView) convertView.findViewById(R.id.hero_4);
		h[5] = (ImageView) convertView.findViewById(R.id.hero_5);
		h[6] = (ImageView) convertView.findViewById(R.id.hero_6);
		h[7] = (ImageView) convertView.findViewById(R.id.hero_7);
		h[8] = (ImageView) convertView.findViewById(R.id.hero_8);
		h[9] = (ImageView) convertView.findViewById(R.id.hero_9);
		h[10] = (ImageView) convertView.findViewById(R.id.hero_10);
		activity = getActivity();
		dialog = new LoadingDialog(activity, getString(R.string.send_info));
		// 获得用户ID
		Intent intent = activity.getIntent();
		userID = intent.getStringExtra("userID");
		// initView();
		myHandler = new MyHandler();
		matchIds = new ArrayList<String>();
		allMatchBeans = new ArrayList<MatchBean>();
		initView();
		fetchData(FETCH_ONLINE_NUM, "");
		fetchData(FETCH_MATCH, lastId);
		return recentLayout;
	}

	public void initView() {
		listView = (XListView) recentLayout
				.findViewById(R.id.frag_player_details_list);
		listView.setVerticalScrollBarEnabled(true);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
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
//					Bundle mBundle = new Bundle();
					ArrayList<String> ids = new ArrayList<String>();
					for(PlayerBean bean: allMatchBeans.get(position - 1).getPlayers()){
						ids.add(bean.getAccountId());
					}
//					mBundle.putSerializable("MatchBean",
//							allMatchBeans.get(position - 1).getPlayers());
//					intent.putExtras(mBundle);
					intent.putExtra("matchId", allMatchBeans.get(position - 1).getMatchId());
					intent.putStringArrayListExtra("ids", ids);
					startActivity(intent);
				}
			}
		});
		onlineNum = (TextView) recentLayout.findViewById(R.id.online_num);
	}

	void fetchData(final int type, final String lastId) {
		PersonalRequestImpl request = new PersonalRequestImpl(
				new IInfoReceive() {

					@Override
					public void onMsgReceiver(ResponseObj receiveInfo) {
						// TODO Auto-generated method stub
						switch (type) {
						case FETCH_MATCH:
							ArrayList<MatchBean> matches = new ArrayList<MatchBean>();
							String num1="";
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
												//没有accountid时 为电脑
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
									Message msg = myHandler.obtainMessage();
									msg.arg1 = type;
									msg.obj = matches;
									myHandler.sendMessage(msg);
								} else {
									dialog.cancel();
									activity.runOnUiThread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											TipsToast.showToast(activity,
													"steam被墙了，你懂得",
													Toast.LENGTH_SHORT,
													DialogType.LOAD_FAILURE);
											listView.setPullLoadEnable(false);
											listView.setPullRefreshEnable(true);
											listView.stopLoadMore();
											listView.stopRefresh();
										}
									});
								}
							} catch (JSONException e) {
								e.printStackTrace();
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
							if (map.size() > 0) {
								DotaApplication.getApplication().setHeroes(map);
							}
							break;
						case FETCH_ONLINE_NUM:
							String num="";
							try {
								JSONObject jsonObj = new JSONObject(receiveInfo
										.getJsonStr());
								JSONObject resultObj = jsonObj
										.getJSONObject("response");
								num = resultObj.getString("player_count");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							Message msg = myHandler.obtainMessage();
							msg.arg1 = type;
							msg.obj = num;
							myHandler.sendMessage(msg);
							break;
						default:
							break;
						}
					}

				});
		request.setActivity(activity);
		request.setDialogTitle("获取中");
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
				allMatchBeans.addAll(beans);
				if (mAdapter == null) {
					mAdapter = new FragItemAdapter(activity, beans);
					listView.setAdapter(mAdapter);
				} else {
					mAdapter.addMoreData(beans);
				}
				break;
			case FETCH_ONLINE_NUM:
				String num = (String) msg.obj;
				onlineNum.setText("当前在线人数："+num);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onStop() {
		// TODO 自动生成的方法存根
		SharedPreferences mySharedPreferences = activity.getSharedPreferences(
				"userID", Activity.MODE_PRIVATE);
		Editor editor = mySharedPreferences.edit();
		editor.putString("userID", userID);
		editor.commit();
		super.onStop();
	}
}
