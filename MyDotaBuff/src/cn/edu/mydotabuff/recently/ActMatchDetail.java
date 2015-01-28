package cn.edu.mydotabuff.recently;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.bean.AbilityBean;
import cn.edu.mydotabuff.bean.MatchBean;
import cn.edu.mydotabuff.bean.PlayerBean;
import cn.edu.mydotabuff.bean.PlayerDetailBean;
import cn.edu.mydotabuff.bean.PlayerInfoBean;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.http.IInfoReceive;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.LoadingDialog;
import cn.edu.mydotabuff.view.TipsToast;
import cn.edu.mydotabuff.view.TipsToast.DialogType;

public class ActMatchDetail extends BaseActivity {
	private String matchId;
	private static final int FETCH_DETAIL = 1;
	private static final int FETCH_PLAYER_DETAIL = 2;
	private static final int FETCH_FAILED = 3;
	private MyHandler myHandler;
	private TextView startTimeView, durationView, matchTypeView,
			win_total_kill, win_total_death, win_total_assist, win_total_money,
			lose_total_kill, lose_total_death, lose_total_assist,
			lose_total_money;
	private ListView list;
	private String steamIDs = "";
	private ArrayList<PlayerDetailBean> playerDetailBeans;
	private ArrayList<PlayerInfoBean> playerInfoBeans;
	private boolean loadPlayerInfoSuccess = false;
	private boolean loadPlayerDetailSuccess = false;
	private String radiant_win, duration, start_time, first_blood_time;
	private int lobby_type;
	private ArrayList<String> ids, idsFromWeb;
	private LoadingDialog dialog;

	@Override
	protected void initViewAndData() {
		// TODO Auto-generated method stub
		setContentView(R.layout.act_match_detail);
		myHandler = new MyHandler();
		matchId = getIntent().getStringExtra("matchId");
		ids = getIntent().getStringArrayListExtra("ids");
		idsFromWeb = new ArrayList<String>();
		dialog = new LoadingDialog(this);

		dialog.show();
		fetchData(FETCH_DETAIL);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("比赛详情");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		startTimeView = (TextView) findViewById(R.id.start_time);
		durationView = (TextView) findViewById(R.id.duration);
		matchTypeView = (TextView) findViewById(R.id.match_type);
		list = (ListView) findViewById(R.id.list);
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub

	}

	void fetchData(final int type) {
		PersonalRequestImpl request = new PersonalRequestImpl(
				new IInfoReceive() {

					@Override
					public void onMsgReceiver(ResponseObj receiveInfo) {
						// TODO Auto-generated method stub
						Message msg = myHandler.obtainMessage();
						switch (type) {
						case FETCH_DETAIL:
							ArrayList<PlayerDetailBean> playerDetailBeans = new ArrayList<PlayerDetailBean>();
							try {
								JSONObject jsonObj = new JSONObject(receiveInfo
										.getJsonStr());
								if (jsonObj.has("result")) {
									JSONObject resultObj = jsonObj
											.getJSONObject("result");
									JSONArray playersArr = resultObj
											.getJSONArray("players");
									JSONObject detailObj;
									for (int i = 0; i < playersArr.length(); i++) {
										detailObj = playersArr.getJSONObject(i);
										ArrayList<AbilityBean> abilitys = new ArrayList<AbilityBean>();
										if (detailObj.has("ability_upgrades")) {
											JSONArray array = detailObj
													.getJSONArray("ability_upgrades");
											for (int j = 0; j < array.length(); j++) {
												JSONObject obj = array
														.getJSONObject(j);
												abilitys.add(new AbilityBean(
														obj.getInt("ability"),
														obj.getInt("time"),
														obj.getInt("level")));
											}
										}
										radiant_win = resultObj
												.getString("radiant_win");
										duration = resultObj
												.getString("duration");
										start_time = resultObj
												.getString("start_time");
										first_blood_time = resultObj
												.getString("first_blood_time");
										lobby_type = resultObj
												.getInt("lobby_type");
										String accountId = "";
										if (detailObj.has("account_id")) {
											accountId = detailObj
													.getString("account_id");
										} else {
											accountId = "4294967295";
										}
										idsFromWeb.add(accountId);
										int leaver_status = 0;
										if (detailObj.has("leaver_status")) {
											leaver_status = detailObj
													.getInt("leaver_status");
										}
										playerDetailBeans.add(new PlayerDetailBean(
												accountId,
												detailObj.getInt("hero_id"),
												detailObj.getInt("item_0"),
												detailObj.getInt("item_1"),
												detailObj.getInt("item_2"),
												detailObj.getInt("item_3"),
												detailObj.getInt("item_4"),
												detailObj.getInt("item_5"),
												detailObj.getInt("kills"),
												detailObj.getInt("deaths"),
												detailObj.getInt("assists"),
												leaver_status,
												detailObj.getInt("gold"),
												detailObj.getInt("last_hits"),
												detailObj.getInt("denies"),
												detailObj
														.getInt("gold_per_min"),
												detailObj.getInt("xp_per_min"),
												detailObj.getInt("gold_spent"),
												detailObj.getInt("hero_damage"),
												detailObj
														.getInt("tower_damage"),
												detailObj
														.getInt("hero_healing"),
												detailObj.getInt("level"),
												abilitys, new PlayerInfoBean()));
									}
									msg.arg1 = type;
									msg.obj = playerDetailBeans;
									if (playerDetailBeans.size() == 10) {
										msg.arg1 = type;
									} else {
										msg.arg1 = FETCH_FAILED;
									}
								} else {
									msg.arg1 = FETCH_FAILED;
								}
							} catch (JSONException e) {
								e.printStackTrace();
								msg.arg1 = FETCH_FAILED;
							}
							myHandler.sendMessage(msg);
							break;
						case FETCH_PLAYER_DETAIL:
							ArrayList<PlayerInfoBean> infoBeans = new ArrayList<PlayerInfoBean>();
							try {
								JSONArray array = new JSONObject(receiveInfo
										.getJsonStr())
										.getJSONObject("response")
										.getJSONArray("players");
								if (array.length() > 0) {
									for (int i = 0; i < array.length(); i++) {
										JSONObject obj = array.getJSONObject(i);
										PlayerInfoBean bean = new PlayerInfoBean();
										bean.setSteamid(obj
												.getString("steamid"));
										bean.setMediumIcon(obj
												.getString("avatarmedium"));
										bean.setName(obj
												.getString("personaname"));
										// 如果是电脑
										if (bean.getSteamid().equals(
												"76561197960265728")) {
											bean.setMediumIcon("http://media.steampowered.com/steamcommunity/public/images/avatars/fe/fef49e7fa7e1997310d705b2a6158ff8dc1cdfeb_medium.jpg");
											bean.setName("电脑");
										}
										infoBeans.add(bean);
									}
									for (int j = 0; j < 10 - array.length(); j++) {
										PlayerInfoBean bean = new PlayerInfoBean();
										bean.setSteamid("76561202255233023");
										bean.setMediumIcon("http://media.steampowered.com/steamcommunity/public/images/avatars/fe/fef49e7fa7e1997310d705b2a6158ff8dc1cdfeb_medium.jpg");
										bean.setName("匿名用户");
										infoBeans.add(bean);
									}
								}
								msg.arg1 = type;
								msg.obj = infoBeans;
							} catch (JSONException e) {
								e.printStackTrace();
								msg.arg1 = FETCH_FAILED;
							}
							myHandler.sendMessage(msg);
							break;
						default:
							break;
						}
					}

				});
		request.setActivity(this);
		switch (type) {
		case FETCH_DETAIL:
			request.getMatchDetails(matchId);
			break;
		case FETCH_PLAYER_DETAIL:
			if (ids == null) {
				ids = idsFromWeb;
			}
			for (int i = 0; i < ids.size(); i++) {
				steamIDs = steamIDs + Common.getSteamID(ids.get(i)) + ",";
				if (ids.get(i).equals("4294967295")) {
				}
			}
			request.getPlayerDetail(steamIDs, false);
			break;
		default:
			break;
		}
	}

	class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			case FETCH_DETAIL:
				loadPlayerDetailSuccess = true;
				playerDetailBeans = (ArrayList<PlayerDetailBean>) msg.obj;
				refreshView();
				fetchData(FETCH_PLAYER_DETAIL);
				break;
			case FETCH_PLAYER_DETAIL:
				loadPlayerInfoSuccess = true;
				playerInfoBeans = (ArrayList<PlayerInfoBean>) msg.obj;
				refreshView();
				dialog.dismiss();
				break;
			case FETCH_FAILED:
				dialog.dismiss();
				TipsToast.showToast(ActMatchDetail.this, "steam被墙了，你懂得",
						Toast.LENGTH_SHORT, DialogType.LOAD_FAILURE);
				break;
			default:
				break;
			}
		}
	}

	void refreshView() {
		if (loadPlayerDetailSuccess && loadPlayerInfoSuccess) {
			int num[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
			for (int i = 0; i < playerDetailBeans.size(); i++) {
				PlayerDetailBean bean = playerDetailBeans.get(i);
				for (int j = 0; j < playerInfoBeans.size(); j++) {
					if (playerInfoBeans.get(j).getSteamid()
							.equals(Common.getSteamID(bean.getAccount_id()))) {
						bean.getPlayerInfoBeans().setName(
								playerInfoBeans.get(j).getName());
						bean.getPlayerInfoBeans().setMediumIcon(
								playerInfoBeans.get(j).getMediumIcon());
						break;
					}
				}
				if (i < playerInfoBeans.size()) {
					if (i <= 4) {
						num[0] += bean.getKills();
						num[1] += bean.getDeaths();
						num[2] += bean.getAssists();
						num[3] += bean.getGold() + bean.getGold_spent();
					} else {
						num[4] += bean.getKills();
						num[5] += bean.getDeaths();
						num[6] += bean.getAssists();
						num[7] += bean.getGold() + bean.getGold_spent();
					}
				}
			}
			if (radiant_win.equals("false")) {
				ArrayList<PlayerDetailBean> temp = new ArrayList<PlayerDetailBean>();
				for (int i = 5; i < 10; i++) {
					temp.add(playerDetailBeans.get(i));
				}
				for (int i = 0; i < 5; i++) {
					temp.add(playerDetailBeans.get(i));
				}
				playerDetailBeans = temp;
				int t = num[0];
				num[0] = num[4];
				num[4] = t;
				t = num[1];
				num[1] = num[5];
				num[5] = t;
				t = num[2];
				num[2] = num[6];
				num[6] = t;
				t = num[3];
				num[3] = num[7];
				num[7] = t;
			}
			startTimeView.setText("开始："
					+ TimeHelper.TimeStamp2Date(start_time, "HH:mm"));
			durationView
					.setText("时长：" + Integer.parseInt(duration) / 60 + "分钟");
			matchTypeView.setText("类型：" + Common.getGameMode(lobby_type));
			list.setAdapter(new ActMatchDetailItemAdapter(this,
					playerDetailBeans, num));
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
