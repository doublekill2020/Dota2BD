package cn.edu.mydotabuff.mydetail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.bean.MatchBean;
import cn.edu.mydotabuff.bean.PlayerBean;
import cn.edu.mydotabuff.bean.PlayerInfoBean;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.common.CommonTitleBar;
import cn.edu.mydotabuff.custom.LoadingDialog;
import cn.edu.mydotabuff.custom.TipsToast;
import cn.edu.mydotabuff.custom.TipsToast.DialogType;
import cn.edu.mydotabuff.http.IInfoReceive;
import cn.edu.mydotabuff.http.OnWebDataGetListener;
import cn.edu.mydotabuff.http.WebDataHelper;
import cn.edu.mydotabuff.http.WebDataHelper.DataType;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.view.CircleImageView;

public class FragMyDetail extends Fragment implements OnWebDataGetListener {
	private CircleImageView iconView;
	private TextView nameView, statusView, timeView, loginView;
	private ImageLoader loader;
	private PlayerInfoBean bean = DotaApplication.getApplication()
			.getPlayerInfo();
	private WebDataHelper helper;
	private Activity activity;
	private LoadingDialog dialog;
	private TextView rightView, winNum, loseNum;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_my_detail, container, false);
		nameView = (TextView) view.findViewById(R.id.name);
		statusView = (TextView) view.findViewById(R.id.status);
		timeView = (TextView) view.findViewById(R.id.regist_time);
		loginView = (TextView) view.findViewById(R.id.last_login);
		winNum = (TextView) view.findViewById(R.id.most_win_num);
		loseNum = (TextView) view.findViewById(R.id.most_lose_num);
		iconView = (CircleImageView) view.findViewById(R.id.myinfrom_up_img);
		loader = ImageLoader.getInstance();
		activity = getActivity();
		rightView = (TextView) activity.findViewById(CommonTitleBar.rightId);
		dialog = new LoadingDialog(activity);
		initView();
		initEvent();
		return view;
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, ActUserStatistics.class);
				startActivity(intent);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		if (bean != null &&bean.getTimecreated() != null) {
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
				helper.getWebData(DataType.DETAIL, activity.getIntent()
						.getStringExtra("userID"));
			}
		}
	}

	private void bindDataFromWeb() {
		// TODO Auto-generated method stub
		winNum.setText(bean.getWinStreak()+"场");
		loseNum.setText(bean.getLoseStreak()+"场");
	}

	@Override
	public void onStartGetData() {
		// TODO Auto-generated method stub
		dialog.show();
	}

	@Override
	public <T> void onGetFinished(List<T> data) {
		// TODO Auto-generated method stub
		bean = DotaApplication.getApplication().getPlayerInfo();
		bindDataFromWeb();
		dialog.dismiss();
	}

	@Override
	public void onGetFailed() {
		// TODO Auto-generated method stub
		dialog.dismiss();
		TipsToast.showToast(activity, "获取超时", Toast.LENGTH_SHORT,
				DialogType.LOAD_FAILURE);
	}

}