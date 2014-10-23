package cn.edu.mydotabuff;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import u.aly.be;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.bean.HerosSatistics;
import cn.edu.mydotabuff.common.CommonTitleBar;
import cn.edu.mydotabuff.custom.LoadingDialog;
import cn.edu.mydotabuff.game.ActInvokerGame;
import cn.edu.mydotabuff.http.OnWebDataGetListener;
import cn.edu.mydotabuff.http.WebDataHelper;
import cn.edu.mydotabuff.http.WebDataHelper.DataType;
import cn.edu.mydotabuff.mydetail.FragMyDetail;
import cn.edu.mydotabuff.recently.FragRecently;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends Activity implements OnClickListener,
		OnWebDataGetListener {

	/*
	 * jsoup 测试
	 */
	private List<HerosSatistics> heroSatisticsList = new ArrayList<HerosSatistics>();

	private FragRecently recentlyFragment;

	private ContactsFragment contactsFragment;

	private FragBoard newsFragment;

	private FragMyDetail settingFragment;

	private View recentlyLayout;

	private View contactsLayout;

	private View newsLayout;

	private View settingLayout;

	private ImageView messageImage;

	private ImageView contactsImage;

	private ImageView newsImage;

	private ImageView settingImage;

	private TextView messageText;

	private TextView contactsText;

	private TextView newsText;

	private TextView settingText;

	private FragmentManager fragmentManager;
	private TextView titleView;
	private View openMenuView;
	private SlidingMenu menu;
	private TextView checkUpdateBtn, feedBackBtn, shareBtn;
	private LoadingDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		setContentView(R.layout.main);
		CommonTitleBar.addCurrencyTitleBar(this, null,
				R.drawable.biz_main_back_normal, "", null, "最近比赛", null, "");
		titleView = (TextView) findViewById(CommonTitleBar.titleId);
		openMenuView = findViewById(R.id.layout_left);
		dialog = new LoadingDialog(this);
		initViews();
		initEvents();
	}

	private void initViews() {

		recentlyLayout = findViewById(R.id.message_layout);
		contactsLayout = findViewById(R.id.contacts_layout);
		newsLayout = findViewById(R.id.board_layout);
		settingLayout = findViewById(R.id.setting_layout);
		messageImage = (ImageView) findViewById(R.id.message_image);
		contactsImage = (ImageView) findViewById(R.id.contacts_image);
		newsImage = (ImageView) findViewById(R.id.news_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
		messageText = (TextView) findViewById(R.id.message_text);
		contactsText = (TextView) findViewById(R.id.contacts_text);
		newsText = (TextView) findViewById(R.id.news_text);
		settingText = (TextView) findViewById(R.id.setting_text);

		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);

		menu = new SlidingMenu(this);// 直接new，而不是getSlidingMenu
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowDrawable(R.drawable.drawer_shadow);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setBehindWidth(400);// 设置SlidingMenu菜单的宽度
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);// 必须调用
		menu.setMenu(R.layout.frame_left_menu);// 就是普通的layout布局

		checkUpdateBtn = (TextView) findViewById(R.id.check_update);
		feedBackBtn = (TextView) findViewById(R.id.feedback);
		shareBtn = (TextView) findViewById(R.id.share);
	}

	private void initEvents() {
		recentlyLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		newsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);

		openMenuView.setOnClickListener(this);
		checkUpdateBtn.setOnClickListener(this);
		feedBackBtn.setOnClickListener(this);
		shareBtn.setOnClickListener(this);
		menu.setBehindCanvasTransformer(new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}
		});
		openMenuView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			titleView.setText("最近比赛");
			break;
		case R.id.contacts_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			WebDataHelper helper = new WebDataHelper(this);
			helper.setDataGetListener(this);
			helper.getWebData(DataType.HERO, "188929113");
			titleView.setText("职业联赛");
			break;
		case R.id.board_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			titleView.setText("天梯排行榜");
			break;
		case R.id.setting_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
			// printDate();
			titleView.setText("个人资料");
			break;
		case R.id.check_update:
			menu.toggle();
			Toast.makeText(this, "检测更新中，请稍后...", 1000).show();
			UmengUpdateAgent.forceUpdate(this);
			break;
		case R.id.share:
			menu.toggle();
			// Toast.makeText(this, "正在开发~~~", 1000).show();
			startActivity(new Intent(this, ActInvokerGame.class));
			break;
		case R.id.feedback:
			menu.toggle();
			// Toast.makeText(this, "正在开发~~~", 1000).show();
			FeedbackAgent agent = new FeedbackAgent(this);
			agent.startFeedbackActivity();
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			messageImage.setImageResource(R.drawable.recently_selected);
			messageText.setTextColor(Color.WHITE);
			if (recentlyFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				recentlyFragment = new FragRecently();
				transaction.add(R.id.content, recentlyFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(recentlyFragment);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			contactsImage.setImageResource(R.drawable.live_selected);
			contactsText.setTextColor(Color.WHITE);
			if (contactsFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				contactsFragment = new ContactsFragment();
				transaction.add(R.id.content, contactsFragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(contactsFragment);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			newsImage.setImageResource(R.drawable.find_seleted);
			newsText.setTextColor(Color.WHITE);
			if (newsFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				newsFragment = new FragBoard();
				transaction.add(R.id.content, newsFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(newsFragment);
			}
			break;
		case 3:
		default:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			settingImage.setImageResource(R.drawable.my_detail_selected);
			settingText.setTextColor(Color.WHITE);
			if (settingFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				settingFragment = new FragMyDetail();
				transaction.add(R.id.content, settingFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		messageImage.setImageResource(R.drawable.recently_unselected);
		messageText.setTextColor(Color.parseColor("#82858b"));
		contactsImage.setImageResource(R.drawable.live_unselected);
		contactsText.setTextColor(Color.parseColor("#82858b"));
		newsImage.setImageResource(R.drawable.find_unseleted);
		newsText.setTextColor(Color.parseColor("#82858b"));
		settingImage.setImageResource(R.drawable.my_detail_unselected);
		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (recentlyFragment != null) {
			transaction.hide(recentlyFragment);
		}
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
		if (newsFragment != null) {
			transaction.hide(newsFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
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
	public void onStartGetData() {
		// TODO Auto-generated method stub
		dialog.show();
		System.out.println("开始加载数据");
	}

	@Override
	public <T> void onGetFinished(List<T> data) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		System.out.println("加载数据成功");
		heroSatisticsList = (List<HerosSatistics>) data;
		for (HerosSatistics beans : heroSatisticsList) {
			System.out.println(beans.toString());
		}
	}

	@Override
	public void onGetFailed() {
		// TODO Auto-generated method stub
		dialog.dismiss();
		System.out.println("加载数据失败！");
	}
}
