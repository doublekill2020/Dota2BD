package cn.edu.mydotabuff.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rom4ek.arcnavigationview.ArcNavigationView;
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

import org.json2.JSONArray;
import org.json2.JSONException;
import org.json2.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.model.PlayerInfoBean;
import cn.edu.mydotabuff.common.http.IInfoReceive;
import cn.edu.mydotabuff.ui.hero.FragHeroList;
import cn.edu.mydotabuff.ui.mydetail.FragMyDetail;
import cn.edu.mydotabuff.ui.recently.FragRecently;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.view.CircleImageView;
import cn.edu.mydotabuff.view.TipsToast;

/**
 * Created by tinker on 2017/6/27.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.navigation_view)
    ArcNavigationView mNavigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    private MainPageAdapter mPageAdapter;

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
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    public void init() {
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        mVp.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mVp);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        initUMShare();

        configureToolbar();
        configureDrawer();


        myPreferences = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        userID = myPreferences.getString("userID", "");
        if (!userID.equals("")) {
            steamID = Common.getSteamID(userID);
            fetchData(FETCH_DETAIL);
        }
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
                                            if (obj.has("timecreated")) {
                                                bean.setTimecreated(obj
                                                        .getString("timecreated"));
                                            } else {
                                                bean.setTimecreated("1417140906");
                                            }
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
                    request.setIsCancelAble(false);
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
                                DotaApplication.LocalDataType.PLAYER_INFO);

                        long time = Long.parseLong(bean.getLastlogooff());
                        long lastUpdateTime = myPreferences.getLong(
                                "lastUpdateTime", 0);
                        SharedPreferences.Editor editor = myPreferences.edit();
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
                    showTip("steam被墙了，你懂得", TipsToast.DialogType.LOAD_FAILURE);
                    break;
                default:
                    break;
            }
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
        sinaHandler.setTargetUrl("http://www.baidu.com");
        mController.getConfig().setSsoHandler(sinaHandler);
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("最近比赛");

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, (Toolbar) findViewById(R
                .id.toolbar), 0, 0) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    class MainPageAdapter extends FragmentStatePagerAdapter {

        private String[] titles = new String[]{"关注", "英雄", "发现", "我"};

        public MainPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragRecently();
                case 1:
                    return new FragHeroList();
                case 2:
                    return new FragFound();
                case 3:
                    return new FragMyDetail();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public interface OnMainEventListener {

        void onFinishGetPlayerInfo();
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
    public boolean isSupportSwipeBack() {
        return false;
    }
}
