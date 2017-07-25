package cn.edu.mydotabuff.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IPlayerDetailPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.PlayerDetailPresenterImpl;
import cn.edu.mydotabuff.ui.view.IPlayerDetailView;

/**
 * Created by nevermore on 2017/7/10 0010.
 */

public class PlayerDetailActivity extends BaseActivity<IPlayerDetailPresenter> implements IPlayerDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cd)
    CoordinatorLayout cd;
    public static final String PLAYER_ID = "PLAYER_ID";
    public static final String PLAYER_INFO = "PLAYER_INFO";
    @BindView(R.id.sdv_user_icon)
    SimpleDraweeView sdvUserIcon;
    @BindView(R.id.tv_player_name)
    TextView tvPlayerName;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private PlayerDetailPageAdapter mPageAdapter;
    private String mPlayerId;
    private PlayerInfo mPlayerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail_base);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mPlayerId = getIntent().getStringExtra(PLAYER_ID);
        mPresenter = new PlayerDetailPresenterImpl(this);
        mPlayerInfo = mPresenter.queryPlayerInfo(mPlayerId);
        sdvUserIcon.setImageURI(Uri.parse(mPlayerInfo.profile.avatar));
        tvPlayerName.setText(mPlayerInfo.profile.personaname);
        tvStatus.setText("mmr:" + (TextUtils.isEmpty(mPlayerInfo.solo_competitive_rank) ?
                R.string.rank_level_unknow_ : mPlayerInfo.solo_competitive_rank) + "|" +
                mPlayerInfo.playerWL.winRate + "%");

        setSupportActionBar(toolbar);
        mToolbar.setTitle(R.string.player_detail_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPageAdapter = new PlayerDetailPageAdapter(getSupportFragmentManager());
        vp.setAdapter(mPageAdapter);
        tabLayout.setupWithViewPager(vp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, String accountId) {
        Intent starter = new Intent(context, PlayerDetailActivity.class);
        starter.putExtra(PLAYER_ID, accountId);
        context.startActivity(starter);
    }

    class PlayerDetailPageAdapter extends FragmentPagerAdapter {

        private String[] titles = new String[]{"战绩", "英雄", "好友", "统计"};

        public PlayerDetailPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return RecentMatchFragment.newInstance(mPlayerInfo);
                case 1:
                    return HeroFragment.newInstance(mPlayerInfo);
                case 2:
                    return FriendFragment.newInstance(mPlayerInfo);
                case 3:
                    return StatisticsFragment.newInstance(mPlayerInfo);
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
}
