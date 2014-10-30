package cn.edu.mydotabuff.mydetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.bean.BestRecord;
import cn.edu.mydotabuff.bean.PlayerInfoBean;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.common.CommonTitleBar;
import cn.edu.mydotabuff.view.XListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

public class ActUserStatistics extends Activity implements OnClickListener {

	private List<View> views;
	private TextView leftBtn, rightBtn;
	private ViewPager pager;
	private ViewpagerAdapter adapter;
	private CommAdapter<BestRecord> commAdapter;
	private PlayerInfoBean bean;
	private ArrayList<BestRecord> beans;
	private XListView leftList;
	private HashMap<String, String> allDataMap,rankDataMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		bean = DotaApplication.getApplication().getPlayerInfo();
		beans = bean.getBeans();
		allDataMap = bean.getMaps().get(0);
		Log.i("hao",allDataMap.toString());
		rankDataMap = bean.getMaps().get(1);
		Log.i("hao",rankDataMap.toString());
		if (beans != null) {
			initView();
			initEvents();
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.act_user_statistics_left_layout);
		CommonTitleBar.addLeftBackAndMidTitle(this, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		}, "统计");
		views = new ArrayList<View>();
		leftBtn = (TextView) findViewById(R.id.tvMRBServing);
		rightBtn = (TextView) findViewById(R.id.tvMRBFinished);
		setTabChange(0);
		pager = (ViewPager) findViewById(R.id.viewPager);
		View view1 = View
				.inflate(this, R.layout.act_user_statistics_left, null);
		leftList = (XListView) view1.findViewById(R.id.left_list);
		leftList.setPullLoadEnable(false);
		leftList.setPullRefreshEnable(false);
		leftList.setVerticalScrollBarEnabled(false);
		leftList.setAdapter(commAdapter = new CommAdapter<BestRecord>(this,
				beans, R.layout.act_user_statistics_left_item) {

			@Override
			public void convert(CommViewHolder helper, BestRecord item) {
				// TODO Auto-generated method stub
				helper.setImageFromWeb(R.id.icon, item.getImageUri());
				helper.setText(R.id.name, item.getHeroName());
				helper.setText(R.id.tag1,
						item.getRecordType() + ":" + item.getRecordNum());
				helper.setText(R.id.tag2, "比赛编号：" + item.getMmatchID());
				helper.setText(R.id.status, item.getResult());
				String result = item.getResult();
				helper.setText(R.id.status, result);
				if (result.equals("胜利")) {
					helper.setBackgroundColor(R.id.status, getResources()
							.getColor(R.color.my_green));
				} else {
					helper.setBackgroundColor(R.id.status, getResources()
							.getColor(R.color.my_orange));
				}
			}
		});
		views.add(view1);
		View view2 = View.inflate(this, R.layout.act_user_statistics_right,
				null);
		views.add(view2);
		if (adapter == null) {
			adapter = new ViewpagerAdapter(views);
			pager.setAdapter(adapter);
		} else {
			adapter.setNewList(views);
		}
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setTabChange(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void setTabChange(final int index) {
		if (index == 0) {
			// leftBtn.setTextColor(getResources().getColor(R.color.white));
			// rightBtn.setTextColor(getResources().getColor(R.color.my_blue));
			leftBtn.setSelected(true);
			rightBtn.setSelected(false);
		} else {
			// rightBtn.setTextColor(getResources().getColor(R.color.white));
			// leftBtn.setTextColor(getResources().getColor(R.color.my_blue));
			rightBtn.setSelected(true);
			leftBtn.setSelected(false);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		if (viewId == R.id.tvMRBServing) {
			pager.setCurrentItem(0);
		} else if (viewId == R.id.tvMRBFinished) {
			pager.setCurrentItem(1);
		}
	}
}
