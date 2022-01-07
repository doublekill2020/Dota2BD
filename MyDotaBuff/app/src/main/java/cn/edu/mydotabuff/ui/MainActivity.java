package cn.edu.mydotabuff.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.nostra13.universalimageloader.core.ImageLoader;
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

import java.util.Map;

import butterknife.BindView;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.base.BaseWebViewActivity;
import cn.edu.mydotabuff.base.RxCallBackEvent;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.common.EventTag;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IMainPresenter;
import cn.edu.mydotabuff.ui.view.IMainView;
import cn.edu.mydotabuff.ui.view.activity.impl.LoginActivity;
import cn.edu.mydotabuff.view.CircleImageView;
import io.realm.Realm;

/**
 * Created by tinker on 2017/6/27.
 */

public class MainActivity extends BaseActivity<IMainPresenter> implements IMainView,NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.cd)
    CoordinatorLayout cd;
    private MainPageAdapter mPageAdapter;

    private String steamID;
    private String userID;
    private SharedPreferences myPreferences;
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");
    private ActionBarDrawerToggle mDrawerToggle;
    SimpleDraweeView sdvUserIcon;
    TextView tvPlayerName;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {

        RxBus.get().register(this);
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        mVp.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mVp);
        initUMShare();

        configureToolbar();
        configureDrawer();


        myPreferences = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        userID = myPreferences.getString("userID", "");
        if (!userID.equals("")) {
            steamID = Common.getSteamID(userID);
        }
        Snackbar snackbar = Snackbar.make(cd, R.string.match_data_will_be_sync_in_background,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(getString(R.string.cancel_do_sync), new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snackbar.show();
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
        getSupportActionBar().setTitle(getString(R.string.app_name));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);

                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
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

        mNavigationView.setNavigationItemSelectedListener(this);
        sdvUserIcon = mNavigationView.findViewById(R.id.sdv_user_icon);
        tvPlayerName = mNavigationView.findViewById(R.id.tv_player_name);
        tvStatus = mNavigationView.findViewById(R.id.tv_status);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.reLogin: {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();

                myPreferences.edit().clear().commit();
                Intent reLoginIntent = new Intent(this, LoginActivity.class);
                reLoginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(reLoginIntent);
                finish();
                break;
            }
            case R.id.about:{
                Intent i = new Intent();
                i.setClass(this, BaseWebViewActivity.class);
                i.putExtra("url", "file:///android_asset/about.html");
                startActivity(i);
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    class MainPageAdapter extends FragmentPagerAdapter {

        //private String[] titles = new String[]{"谁最菜", "关注", "统计", "发现"};
        private String[] titles = new String[]{"谁最菜", "关注"};
        private FollowFragment followFragment;
        //private FragHeroList fragHeroList;
        private StatisticsFragment statisticsFragment;
        private FragFound fragFound;
        private PlayedWithFragment playedWith;

        public MainPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if(playedWith == null){
                        playedWith = new PlayedWithFragment();
                    }
                    return playedWith;
                case 1:
                    if(followFragment == null){
                        followFragment = new FollowFragment();
                    }
                    return followFragment;
//                case 2:
//                    if(statisticsFragment == null){
//                        statisticsFragment = new StatisticsFragment();
//                    }
//                    return statisticsFragment;
//                case 3:
//                    if (fragFound == null){
//                        fragFound = new FragFound();
//                    }
//                    return fragFound;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
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

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    private void refreshDrawerLayoutData(PlayerInfo playerInfo) {
        if (sdvUserIcon == null)
            sdvUserIcon = mNavigationView.findViewById(R.id.sdv_user_icon);
        if (tvPlayerName == null)
            tvPlayerName = mNavigationView.findViewById(R.id.tv_player_name);
        if (tvStatus == null)
            tvStatus = mNavigationView.findViewById(R.id.tv_status);
        sdvUserIcon.setImageURI(Uri.parse(playerInfo.profile.avatar));
        tvPlayerName.setText(playerInfo.profile.personaname);
        tvStatus.setText("MMR:" + (TextUtils.isEmpty(playerInfo.solo_competitive_rank) ?
                R.string.rank_level_unknow_ : playerInfo.solo_competitive_rank) + " | " + "胜率" +
                playerInfo.playerWL.winRate + "%");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Subscribe
    public void onUserInfoReceived(RxCallBackEvent<Map<String, PlayerInfo>> event){
        if(event.tag == EventTag.GET_PLAYER_INFO_SUCCESS && event.data != null && event.data.containsKey(userID)){
            refreshDrawerLayoutData(event.data.get(userID));
        }
    }
}
