package cn.edu.mydotabuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.bean.PlayerInfoBean;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.common.CommonTitleBar;
import cn.edu.mydotabuff.hero.FragHeroList;
import cn.edu.mydotabuff.http.IInfoReceive;
import cn.edu.mydotabuff.mydetail.FragMyDetail;
import cn.edu.mydotabuff.recently.FragRecently;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.view.CircleImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends Activity implements OnClickListener {

	/*
	 * jsoup 测试
	 */

	private FragRecently recentlyFragment;

	private FragHeroList contactsFragment;

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
	private TextView titleView, rightView;
	private View openMenuView;
	private SlidingMenu menu;
	private TextView checkUpdateBtn, feedBackBtn, shareBtn;

	private String steamID;
	private static final int FETCH_DETAIL = 1, FETCH_FAILED = 2;
	private MyHandler myHandler = new MyHandler();
	private CircleImageView userIcon;
	private TextView userName;
	private ImageLoader loader;

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
		rightView = (TextView) findViewById(CommonTitleBar.rightId);
		openMenuView = findViewById(R.id.layout_left);
		loader = ImageLoader.getInstance();

		initViews();
		initEvents();
		PlayerInfoBean bean = DotaApplication.getApplication().getPlayerInfo();
		if (getIntent().getStringExtra("userID") != null) {
			steamID = Common.getSteamID(getIntent().getStringExtra("userID"));
			if (bean == null) {
				fetchData(FETCH_DETAIL);
			} else {
				if (bean.getSteamid() != null) {
					if (!bean.getSteamid().equals(steamID)) {
						DotaApplication.getApplication().destoryPlayerInfo();
						fetchData(FETCH_DETAIL);
					} else {
						loader.displayImage(bean.getMediumIcon(), userIcon);
						userName.setText(bean.getName());
					}
				}
			}
		}
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
										bean.setSteamid(obj
												.getString("steamid"));
									}
									Message msg = myHandler.obtainMessage();
									msg.arg1 = type;
									msg.obj = bean;
									myHandler.sendMessage(msg);
								} else {
									Message msg = myHandler.obtainMessage();
									msg.arg1 = FETCH_FAILED;
									myHandler.sendMessage(msg);
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
		request.setActivity(this);
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
					DotaApplication.getApplication().setPlayerInfo(bean);
					loader.displayImage(bean.getMediumIcon(), userIcon);
					userName.setText(bean.getName());
				}
				break;
			case FETCH_FAILED:

				break;
			default:
				break;
			}
		}
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
		userIcon = (CircleImageView) findViewById(R.id.user_icon);
		userName = (TextView) findViewById(R.id.user_name);
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
			setTabSelection(0);
			titleView.setText("最近比赛");
			rightView.setVisibility(View.GONE);
			break;
		case R.id.contacts_layout:
			setTabSelection(1);
			titleView.setText("英雄使用");
			rightView.setVisibility(View.GONE);
			break;
		case R.id.board_layout:
			setTabSelection(2);
			titleView.setText("天梯排行榜");
			rightView.setVisibility(View.GONE);
			break;
		case R.id.setting_layout:
			setTabSelection(3);
			titleView.setText("个人资料");
			rightView.setText("统计");
			rightView.setVisibility(View.VISIBLE);
			break;
		case R.id.check_update:
			menu.toggle();
			Toast.makeText(this, "检测更新中，请稍后...", 1000).show();
			UmengUpdateAgent.forceUpdate(this);
			break;
		case R.id.share:
			menu.toggle();
			// startActivity(new Intent(this, ActInvokerGame.class));
			break;
		case R.id.feedback:
			menu.toggle();
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
				contactsFragment = new FragHeroList();
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
}
