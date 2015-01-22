package cn.edu.mydotabuff;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.ConnectCallback.ErrorCode;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.DotaApplication.LocalDataType;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.bean.PlayerInfoBean;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.game.ActInvokerGame;
import cn.edu.mydotabuff.hero.FragHeroList;
import cn.edu.mydotabuff.http.IInfoReceive;
import cn.edu.mydotabuff.mydetail.FragMyDetail;
import cn.edu.mydotabuff.recently.FragRecently;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.view.CircleImageView;
import cn.edu.mydotabuff.view.TipsToast;
import cn.edu.mydotabuff.view.TipsToast.DialogType;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.a.b.m;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends BaseActivity implements OnClickListener {

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
	// private SlidingMenu menu;
	private TextView checkUpdateBtn, feedBackBtn, shareBtn, logoutBtn, chatBtn;

	private String steamID;
	private static final int FETCH_DETAIL = 1, FETCH_FAILED = 2,
			LOGIN_SUCCESS = 3;
	private MyHandler myHandler = new MyHandler();
	private CircleImageView userIcon;
	private TextView userName;
	private ImageLoader loader;
	private String userID;
	private SharedPreferences myPreferences;
	private OnMainEventListener listener;
	// 首先在您的Activity中添加如下成员变量
	final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		setContentView(R.layout.main);
		loader = ImageLoader.getInstance();

		initUMShare();
		initViews();
		initEvents();
		myPreferences = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
		userID = myPreferences.getString("userID", "");
		if (!userID.equals("")) {
			steamID = Common.getSteamID(userID);
			fetchData(FETCH_DETAIL);
		}
		configureToolbar();
		configureDrawer();
	}

	private void configureToolbar() {
		Toolbar mainToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mainToolbar);
		getSupportActionBar().setTitle("最近比赛");

		mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
					mDrawerLayout.closeDrawer(Gravity.START);

				} else {
					mDrawerLayout.openDrawer(Gravity.START);
				}
			}
		});
	}

	private void configureDrawer() {
		// Configure drawer
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.dan, R.string.dan) {

			public void onDrawerClosed(View view) {
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				supportInvalidateOptionsMenu(); // creates call to
												// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
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
										if (bean.getLastlogooff() == null) {
											bean.setLastlogooff("1417140906");
										}
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
										msg.arg1 = type;
										msg.obj = bean;
									} else {
										msg.arg1 = FETCH_FAILED;
									}
									myHandler.sendMessage(msg);
								} else {
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
		switch (type) {
		case FETCH_DETAIL:
			if (myPreferences.getString("isNeedUpdate", "").equals("")) {
				request.getPlayerDetail(steamID, false);
			} else {
				request.getPlayerDetail(steamID, true);
			}
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
					DotaApplication.getApplication().saveData(bean,
							LocalDataType.PLAYER_INFO);
					loader.displayImage(bean.getMediumIcon(), userIcon);
					userName.setText(bean.getName());

					long time = Long.parseLong(bean.getLastlogooff());
					long lastUpdateTime = myPreferences.getLong(
							"lastUpdateTime", 0);
					Editor editor = myPreferences.edit();
					if (time > lastUpdateTime) {
						editor.putLong("lastUpdateTime", time);
						editor.putString("isNeedUpdate", "true");
						editor.commit();
					} else {
						editor.putString("isNeedUpdate", "false");
						editor.commit();
					}
					if (listener != null)
						listener.onFinishGetPlayerInfo();
				}
				break;
			case FETCH_FAILED:
				TipsToast.showToast(MainActivity.this, "steam被墙了，你懂得",
						Toast.LENGTH_SHORT, DialogType.LOAD_FAILURE);
				break;
			case LOGIN_SUCCESS:
				String token = (String) msg.obj;
				// 连接融云服务器。
				RongIM.connect(token, new RongIMClient.ConnectCallback() {

					@Override
					public void onSuccess(String s) {
						// 此处处理连接成功。
						Toast.makeText(MainActivity.this, "登录成功！",
								Toast.LENGTH_SHORT).show();
						RongIM.getInstance().startChatroom(MainActivity.this,
								"chatroom002", "聊天室");
					}

					@Override
					public void onError(ErrorCode errorCode) {
						// 此处处理连接错误。
						Toast.makeText(MainActivity.this,
								errorCode.getMessage(), Toast.LENGTH_SHORT)
								.show();
					}
				});
				// // 设置用户信息提供者。
				// RongIM.setGetUserInfoProvider(new
				// RongIM.GetUserInfoProvider() {
				// // App 返回指定的用户信息给 IMKit 界面组件。
				// // 原则上 App
				// //
				// 应该将用户信息和头像在移动设备上进行缓存，每次获取用户信息的时候，就不用再通过网络获取，提高加载速度，提升用户体验。我们后续将提供用户信息缓存功能，方便您开发。
				// @Override
				// public RongIMClient.UserInfo getUserInfo(String userId) {
				// if (userId.equals("188929113")) {
				// RongIMClient.UserInfo user = new RongIMClient.UserInfo(
				// "188929113", "zhangsan",
				// "http://www.baidu.com/img/bdlogo.png");
				//
				// return user;
				// } else if (userId.equals("202055420")) {
				// RongIMClient.UserInfo user = new RongIMClient.UserInfo(
				// "202055420", "lisi",
				// "http://2.su.bdimg.com/star_skin/1001_t.png");
				//
				// return user;
				// }
				//
				// return null;
				// }
				// }, false);

				// // 设置好友信息提供者。
				// RongIM.setGetFriendsProvider(new RongIM.GetFriendsProvider()
				// {
				// @Override
				// public List<RongIMClient.UserInfo> getFriends() {
				// // 返回 App 的好友列表给 IMKit 界面组件，供会话列表页中选择好友时使用。
				// List<RongIMClient.UserInfo> list = new
				// ArrayList<RongIMClient.UserInfo>();
				//
				// RongIMClient.UserInfo user1 = new RongIMClient.UserInfo(
				// "188929113", "zhangsan",
				// "http://www.baidu.com/img/bdlogo.png");
				//
				// list.add(user1);
				//
				// RongIMClient.UserInfo user2 = new RongIMClient.UserInfo(
				// "202055420", "lisi",
				// "http://2.su.bdimg.com/star_skin/1001_t.png");
				//
				// list.add(user2);
				//
				// return list;
				// }
				// });
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

		// menu = new SlidingMenu(this);// 直接new，而不是getSlidingMenu
		// menu.setMode(SlidingMenu.LEFT);
		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// menu.setShadowDrawable(R.drawable.drawer_shadow);
		// menu.setShadowWidthRes(R.dimen.shadow_width);
		// menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// menu.setBehindWidth(400);// 设置SlidingMenu菜单的宽度
		// menu.setFadeDegree(0.35f);
		// menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);// 必须调用
		// menu.setMenu(R.layout.frame_left_menu);// 就是普通的layout布局

		checkUpdateBtn = (TextView) findViewById(R.id.check_update);
		feedBackBtn = (TextView) findViewById(R.id.feedback);
		shareBtn = (TextView) findViewById(R.id.share);
		logoutBtn = (TextView) findViewById(R.id.logout);
		chatBtn = (TextView) findViewById(R.id.chat_room);
		userIcon = (CircleImageView) findViewById(R.id.user_icon);
		userName = (TextView) findViewById(R.id.user_name);
	}

	private void initEvents() {
		recentlyLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		newsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);

		checkUpdateBtn.setOnClickListener(this);
		feedBackBtn.setOnClickListener(this);
		shareBtn.setOnClickListener(this);
		logoutBtn.setOnClickListener(this);
		chatBtn.setOnClickListener(this);
		// menu.setBehindCanvasTransformer(new CanvasTransformer() {
		// @Override
		// public void transformCanvas(Canvas canvas, float percentOpen) {
		// float scale = (float) (percentOpen * 0.25 + 0.75);
		// canvas.scale(scale, scale, canvas.getWidth() / 2,
		// canvas.getHeight() / 2);
		// }
		// });
		// openMenuView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// menu.toggle();
		// }
		// });
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_layout:
			setTabSelection(0);
			getSupportActionBar().setTitle("最近比赛");
			break;
		case R.id.contacts_layout:
			setTabSelection(1);
			getSupportActionBar().setTitle("英雄使用");
			break;
		case R.id.board_layout:
			setTabSelection(2);
			getSupportActionBar().setTitle("天梯排行榜");
			break;
		case R.id.chat_room:
			mDrawerLayout.closeDrawer(Gravity.LEFT);
			PersonalRequestImpl request = new PersonalRequestImpl(
					new IInfoReceive() {

						@Override
						public void onMsgReceiver(ResponseObj receiveInfo) {
							// TODO Auto-generated method stub
							try {
								JSONObject obj = new JSONObject(
										receiveInfo.getJsonStr());
								int code = obj.getInt("code");
								if (code == 200) {
									Message msg = myHandler.obtainMessage();
									msg.obj = obj.getString("token");
									msg.arg1 = LOGIN_SUCCESS;
									myHandler.sendMessage(msg);
								} else {

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});
			request.setActivity(this);
			request.setDialogTitle("登录中，请稍候...");
			PlayerInfoBean bean = DotaApplication.getApplication().getData(
					LocalDataType.PLAYER_INFO);
			request.getUserToken(userID, bean.getName(), bean.getMediumIcon());
			break;
		case R.id.setting_layout:
			setTabSelection(3);
			getSupportActionBar().setTitle("个人资料");
			break;
		case R.id.check_update:
			mDrawerLayout.closeDrawer(Gravity.LEFT);
			Toast.makeText(this, "检测更新中，请稍后...", 1000).show();
			UmengUpdateAgent.forceUpdate(this);
			break;
		case R.id.share:
			mDrawerLayout.closeDrawer(Gravity.LEFT);
			mController.openShare(this, null);
			break;
		case R.id.feedback:
			mDrawerLayout.closeDrawer(Gravity.LEFT);
			FeedbackAgent agent = new FeedbackAgent(this);
			agent.startFeedbackActivity();
			break;
		case R.id.logout:
			SharedPreferences mySharedPreferences = getSharedPreferences(
					"user_info", Activity.MODE_PRIVATE);
			Editor editor = mySharedPreferences.edit();
			editor.putString("userID", "");
			editor.putString("isLogin", "false");
			editor.putString("isNeedUpdate", "true");
			editor.putLong("lastUpdateTime", 0);
			editor.commit();
			DotaApplication.getApplication().destoryData(
					LocalDataType.PLAYER_INFO);
			DotaApplication.getApplication().destoryData(
					LocalDataType.PLAYER_DETAIL_INFO);
			DotaApplication.getApplication().destoryData(LocalDataType.MATCHES);
			DotaApplication.getApplication().destoryData(
					LocalDataType.HERO_USED_LIST);
			startActivity(new Intent(this, ActLogin.class));
			finish();
			break;
		default:
			break;
		}
	}

	private void initUMShare() {

		mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
		mController.getConfig().removePlatform(SHARE_MEDIA.SINA);
		String targetUrl = "http://4evercai.aliapp.com/";
		mController.setShareContent(getString(R.string.share_content));
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(this, R.drawable.ic_launcher));
		String appID = "wx1aa7275fa99e880f";
		String appSecret = "09811403cd21959cc384dea048c01aba";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
		wxHandler.setTargetUrl(targetUrl);
		wxHandler.addToSocialSDK();

		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.setTargetUrl(targetUrl);
		wxCircleHandler.addToSocialSDK();

		String QQappID = "1103458121";
		String QQappSecret = "PtcezFKwEHmAF0t9";
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, QQappID,
				QQappSecret);
		qqSsoHandler.setTargetUrl(targetUrl);
		QQShareContent qqShareContent = new QQShareContent();
		// 设置分享文字
		qqShareContent.setShareContent(getString(R.string.share_content));
		// 设置分享title
		qqShareContent.setTitle(getString(R.string.app_name));
		// 设置点击分享内容的跳转链接
		qqShareContent.setTargetUrl(targetUrl);
		qqShareContent.setShareImage(new UMImage(this, R.drawable.ic_launcher));
		mController.setShareMedia(qqShareContent);
		qqSsoHandler.addToSocialSDK();

		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, QQappID,
				QQappSecret);
		qZoneSsoHandler.setTargetUrl(targetUrl);
		QZoneShareContent qzone = new QZoneShareContent();
		// 设置分享文字
		qzone.setShareContent(getString(R.string.share_content));
		// 设置点击消息的跳转URL
		qzone.setTargetUrl(targetUrl);
		qzone.setShareImage(new UMImage(this, R.drawable.ic_launcher));
		// 设置分享内容的标题
		qzone.setTitle(getString(R.string.app_name));
		mController.setShareMedia(qzone);
		qZoneSsoHandler.addToSocialSDK();
		SinaSsoHandler sinaHandler = new SinaSsoHandler();
		sinaHandler.setTargetUrl(targetUrl);
		mController.getConfig().setSsoHandler(sinaHandler);
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
		transaction.commitAllowingStateLoss();
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
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
		try {
			listener = (OnMainEventListener) fragment;
		} catch (Exception e) {
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	private boolean flags = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (flags) {
				finish();
			} else {
				flags = true;
				Toast.makeText(this, "再按一次退出程序!", 500).show();
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						flags = false;
					}
				}, 1000);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
