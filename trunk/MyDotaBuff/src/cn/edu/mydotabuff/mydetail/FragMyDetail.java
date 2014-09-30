package cn.edu.mydotabuff.mydetail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.bean.MatchBean;
import cn.edu.mydotabuff.bean.PlayerBean;
import cn.edu.mydotabuff.bean.PlayerInfoBean;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.common.CommonTitleBar;
import cn.edu.mydotabuff.custom.TipsToast;
import cn.edu.mydotabuff.custom.TipsToast.DialogType;
import cn.edu.mydotabuff.http.IInfoReceive;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.view.CircleImageView;

public class FragMyDetail extends Fragment {
	private Activity activity;
	private static final int FETCH_DETAIL = 1;
	private String steamID;
	private MyHandler myHandler;
	private CircleImageView iconView;
	private TextView nameView, statusView, timeView, loginView;
	private ImageLoader loader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_my_detail, container, false);
		nameView = (TextView) view.findViewById(R.id.name);
		statusView = (TextView) view.findViewById(R.id.status);
		timeView = (TextView) view.findViewById(R.id.regist_time);
		loginView = (TextView) view.findViewById(R.id.last_login);
		iconView = (CircleImageView) view.findViewById(R.id.myinfrom_up_img);
		activity = getActivity();
		loader = ImageLoader.getInstance();
		myHandler = new MyHandler();
		if (activity.getIntent().getStringExtra("userID") != null) {
			steamID = Common.getSteamID(activity.getIntent().getStringExtra(
					"userID"));
			fetchData(FETCH_DETAIL);
		}
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
	}

	void fetchData(final int type) {
		PersonalRequestImpl request = new PersonalRequestImpl(
				new IInfoReceive() {

					@Override
					public void onMsgReceiver(ResponseObj receiveInfo) {
						// TODO Auto-generated method stub
						switch (type) {
						case FETCH_DETAIL:
							PlayerInfoBean bean = new PlayerInfoBean();
							try {
								if (new JSONObject(receiveInfo.getJsonStr())
										.has("response")) {
									JSONArray array = new JSONObject(
											receiveInfo.getJsonStr())
											.getJSONObject("response")
											.getJSONArray("players");
									if (array.length() > 0) {
										JSONObject obj = array.getJSONObject(0);
										bean.setCommunityState(obj
												.getInt("communityvisibilitystate"));
										bean.setLastlogooff(obj
												.getString("lastlogoff"));
										bean.setMediumIcon(obj
												.getString("avatarmedium"));
										bean.setName(obj
												.getString("personaname"));
										bean.setState(obj
												.getInt("personastate"));
										bean.setTimecreated(obj
												.getString("timecreated"));
									}
									Message msg = myHandler.obtainMessage();
									msg.arg1 = type;
									msg.obj = bean;
									myHandler.sendMessage(msg);
								} else {
									activity.runOnUiThread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											TipsToast.showToast(activity,
													"steam被墙了，你懂得",
													Toast.LENGTH_SHORT,
													DialogType.LOAD_FAILURE);
										}
									});
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

							break;
						default:
							break;
						}
					}

				});
		request.setActivity(activity);
		request.setDialogTitle("获取中");
		switch (type) {
		case FETCH_DETAIL:
			request.getPlayerDetail(steamID);
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
				PlayerInfoBean bean = (PlayerInfoBean) msg.obj;
				if (bean != null) {
					nameView.setText(bean.getName());
					statusView.setText(Common.getPersonState(bean.getState()));
					timeView.setText(TimeHelper.TimeStamp2Date(
							bean.getTimecreated(), "yyyy-MM-dd HH:mm"));
					loginView.setText(TimeHelper.TimeStamp2Date(
							bean.getLastlogooff(), "yyyy-MM-dd HH:mm"));
					loader.displayImage(bean.getMediumIcon(), iconView);
				}
				break;
			default:
				break;
			}
		}
	}
}
