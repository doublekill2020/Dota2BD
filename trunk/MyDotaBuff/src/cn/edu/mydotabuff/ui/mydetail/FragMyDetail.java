package cn.edu.mydotabuff.ui.mydetail;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.DotaApplication.LocalDataType;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.common.bean.PlayerInfoBean;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.common.http.OnWebDataGetListener;
import cn.edu.mydotabuff.common.http.WebDataHelper;
import cn.edu.mydotabuff.common.http.WebDataHelper.DataType;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.view.CircleImageView;
import cn.edu.mydotabuff.view.LoadingDialog;
import cn.edu.mydotabuff.view.TipsToast;
import cn.edu.mydotabuff.view.TipsToast.DialogType;

import com.nostra13.universalimageloader.core.ImageLoader;

public class FragMyDetail extends Fragment implements OnWebDataGetListener {
	private CircleImageView iconView;
	private TextView nameView, statusView, timeView, loginView;
	private ImageLoader loader;
	private PlayerInfoBean bean;
	private WebDataHelper helper;
	private Activity activity;
	private LoadingDialog dialog;
	private TextView winNum, loseNum;
	private SharedPreferences myPreferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_my_detail, container, false);
		setHasOptionsMenu(true);
		nameView = (TextView) view.findViewById(R.id.name);
		statusView = (TextView) view.findViewById(R.id.status);
		timeView = (TextView) view.findViewById(R.id.regist_time);
		loginView = (TextView) view.findViewById(R.id.last_login);
		winNum = (TextView) view.findViewById(R.id.most_win_num);
		loseNum = (TextView) view.findViewById(R.id.most_lose_num);
		iconView = (CircleImageView) view.findViewById(R.id.myinfrom_up_img);
		loader = ImageLoader.getInstance();
		activity = getActivity();
		dialog = new LoadingDialog(activity);
		myPreferences = activity.getSharedPreferences("user_info",
				Activity.MODE_PRIVATE);
		String isNeedUpdate = myPreferences.getString("isNeedUpdate", "");
		if (isNeedUpdate.equals("true")) {
			helper = new WebDataHelper(activity);
			helper.setDataGetListener(this);
			helper.getWebData(DataType.DETAIL,
					myPreferences.getString("userID", ""));
		} else {
			bean = DotaApplication.getApplication().getData(
					LocalDataType.PLAYER_DETAIL_INFO);
			if (bean == null) {
				helper = new WebDataHelper(activity);
				helper.setDataGetListener(this);
				helper.getWebData(DataType.DETAIL,
						myPreferences.getString("userID", ""));
			} else {
				initView();
				bindDataFromWeb();
			}
		}
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		if (bean != null && bean.getTimecreated() != null) {
			nameView.setText(bean.getName());
			statusView.setText(Common.getPersonState(bean.getState()));
			timeView.setText(TimeHelper.TimeStamp2Date(bean.getTimecreated(),
					"yyyy-MM-dd HH:mm"));
			loginView.setText(TimeHelper.TimeStamp2Date(bean.getLastlogooff(),
					"yyyy-MM-dd HH:mm"));
			loader.displayImage(bean.getMediumIcon(), iconView);
			if (bean.isLoadWebData()) {
				bindDataFromWeb();
			} else {
				helper = new WebDataHelper(activity);
				helper.setDataGetListener(this);
				helper.getWebData(DataType.DETAIL,
						myPreferences.getString("userID", ""));
			}
		}
	}

	private void bindDataFromWeb() {
		// TODO Auto-generated method stub
		winNum.setText(bean.getWinStreak() + "场");
		loseNum.setText(bean.getLoseStreak() + "场");
	}

	@Override
	public void onStartGetData() {
		// TODO Auto-generated method stub
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	public <T> void onGetFinished(T data) {
		// TODO Auto-generated method stub
		bean = DotaApplication.getApplication().getData(
				LocalDataType.PLAYER_DETAIL_INFO);
		initView();
		bindDataFromWeb();
		dialog.dismiss();
	}

	@Override
	public void onGetFailed(String failMsg) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		TipsToast.showToast(activity, "获取超时", Toast.LENGTH_SHORT,
				DialogType.LOAD_FAILURE);
	}

	OnMenuItemClickListener itemListener = new OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub

			return false;
		}
	};

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.frag_my_detail, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.more_detail:
			if (bean == null) {
				TipsToast.showToast(activity, "暂无数据", Toast.LENGTH_SHORT,
						DialogType.LOAD_FAILURE);
			} else {
				if (bean.isLoadMap() && bean.isLoadWebData()
						&& bean.getBeans() != null) {
					Intent intent = new Intent(activity,
							ActUserStatistics.class);
					intent.putExtra("type", "current");
					startActivity(intent);
				} else {
					TipsToast.showToast(activity, "暂无数据", Toast.LENGTH_SHORT,
							DialogType.LOAD_FAILURE);
				}
			}
			break;
		case R.id.give_mark:
			try {
				Uri uri = Uri.parse("market://details?id="
						+ getActivity().getPackageName());
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} catch (Exception e) {
				TipsToast.showToast(activity, "抱歉，您还未安装相应的应用市场",
						Toast.LENGTH_SHORT, DialogType.LOAD_FAILURE);
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
