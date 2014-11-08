package cn.edu.mydotabuff;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.DotaApplication.LocalDataType;
import cn.edu.mydotabuff.bean.BoardBean;
import cn.edu.mydotabuff.bean.MatchBean;
import cn.edu.mydotabuff.bean.PlayerBean;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.custom.TipsToast;
import cn.edu.mydotabuff.custom.TipsToast.DialogType;
import cn.edu.mydotabuff.http.IInfoReceive;
import cn.edu.mydotabuff.http.IInfoReceive.ResponseObj;
import cn.edu.mydotabuff.recently.FragItemAdapter;
import cn.edu.mydotabuff.recently.FragRecently;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.util.TimeHelper;

public class FragBoard extends Fragment {

	private static final int FETCH_BOARD = 1;
	private static final int FETCH_FAILED = 2;
	private ArrayList<BoardBean> beans;
	private ListView lv;
	private CommAdapter<BoardBean> adapter;
	private Activity activity;
	private TextView tx;
	private MyHandler myHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.frag_board, container,
				false);
		lv = (ListView) newsLayout.findViewById(R.id.frag_board_list);
		tx = (TextView) newsLayout.findViewById(R.id.update_time);
		beans = DotaApplication.getApplication().getData(LocalDataType.BOARDS);
		myHandler = new MyHandler();
		activity = getActivity();
		if (beans == null) {
			fetchData(FETCH_BOARD);
		} else {
			lv.setAdapter(adapter = new CommAdapter<BoardBean>(activity, beans,
					R.layout.frag_board_item) {

				@Override
				public void convert(CommViewHolder helper, BoardBean item) {
					// TODO Auto-generated method stub
					helper.setText(R.id.name, item.getName());
					helper.setText(R.id.rank, item.getRank() + "");
					helper.setText(R.id.solo_mmr, item.getSolo_mmr() + "");
				}
			});
			if (beans.size() > 0) {
				tx.setText(TimeHelper.TimeStamp2Date(beans.get(0)
						.getUpdateTime(), "MM-dd HH:mm"));
			}
		}
		return newsLayout;
	}

	void fetchData(final int type) {
		PersonalRequestImpl request = new PersonalRequestImpl(
				new IInfoReceive() {

					@Override
					public void onMsgReceiver(ResponseObj receiveInfo) {
						// TODO Auto-generated method stub
						switch (type) {
						case FETCH_BOARD:
							ArrayList<BoardBean> beans = new ArrayList<BoardBean>();
							try {
								JSONObject jsonObj = new JSONObject(receiveInfo
										.getJsonStr());
								String time = jsonObj.getString("time_posted");
								JSONArray array = jsonObj
										.getJSONArray("leaderboard");
								for (int i = 0; i < array.length(); i++) {
									JSONObject obj = array.getJSONObject(i);
									int rank = obj.getInt("rank");
									String name = "";
									if (obj.has("team_tag")) {
										name = obj.getString("team_tag") + ".";
									}
									name += obj.getString("name");
									int solo_mmr = obj.getInt("solo_mmr");
									beans.add(new BoardBean(time, rank, name,
											solo_mmr));
								}
								Message msg = myHandler.obtainMessage();
								msg.arg1 = type;
								msg.obj = beans;
								myHandler.sendMessage(msg);
							} catch (JSONException e) {
								e.printStackTrace();
								Message msg = myHandler.obtainMessage();
								msg.arg1 = FETCH_FAILED;
								myHandler.sendMessage(msg);
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
		case FETCH_BOARD:
			request.getBoard("china");
			break;
		default:
			break;
		}
	}

	class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			case FETCH_BOARD:
				ArrayList<BoardBean> beans = (ArrayList<BoardBean>) msg.obj;
				if (beans != null) {
					DotaApplication.getApplication().saveData(beans, LocalDataType.BOARDS);
					lv.setAdapter(adapter = new CommAdapter<BoardBean>(
							activity, beans, R.layout.frag_board_item) {

						@Override
						public void convert(CommViewHolder helper,
								BoardBean item) {
							// TODO Auto-generated method stub
							helper.setText(R.id.name, item.getName());
							helper.setText(R.id.rank, item.getRank() + "");
							helper.setText(R.id.solo_mmr, item.getSolo_mmr()
									+ "");
						}
					});
					if (beans.size() > 0) {
						tx.setText("上次更新时间："
								+ TimeHelper.TimeStamp2Date(beans.get(0)
										.getUpdateTime(), "MM-dd HH:mm"));
					}
				}
				break;
			case FETCH_FAILED:
				TipsToast.showToast(activity, "steam被墙了，你懂得",
						Toast.LENGTH_SHORT, DialogType.LOAD_FAILURE);
				break;
			default:
				break;
			}
		}
	}

}
