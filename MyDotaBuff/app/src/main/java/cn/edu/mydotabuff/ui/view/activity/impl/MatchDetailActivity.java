package cn.edu.mydotabuff.ui.view.activity.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.model.LobbyType;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.model.MatchPlayInfo;
import cn.edu.mydotabuff.ui.MatchOverviewFragment;
import cn.edu.mydotabuff.ui.presenter.IMatchDetaiPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.MatchDetailPresenterImpl;
import cn.edu.mydotabuff.ui.view.MatchGrphsFragment;
import cn.edu.mydotabuff.ui.view.activity.IMatchDetailView;
import cn.edu.mydotabuff.util.TimeHelper;

/**
 * Created by sadhu on 2017/7/5.
 * 描述: 比赛详情
 */
public class MatchDetailActivity extends BaseActivity<IMatchDetaiPresenter> implements IMatchDetailView {

    public static final String EXTRA_MATCH_ID = "match_id";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_match_finish_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_match_duration)
    TextView mTvDuration;
    @BindView(R.id.tv_match_level)
    TextView mTvMatchLevel;
    @BindView(R.id.tv_game_mode)
    TextView mTvGameMode;

    @BindView(R.id.tv_title)
    TextView mTvTitle;


    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mVp;

    private MatchDetail mMatchDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_match_detail_new);
        setSupportActionBar(toolbar);
        int contentInsetStartWithNavigation = toolbar.getContentInsetStartWithNavigation();
        toolbar.setContentInsetsRelative(0, contentInsetStartWithNavigation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mPresenter = new MatchDetailPresenterImpl(this);
        String matchId = getIntent().getStringExtra(EXTRA_MATCH_ID);
        mTvTitle.setText(String.format("比赛%s", matchId));
        mPresenter.fetchMatchDetailInfo(matchId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, String matchId) {
        Intent intent = new Intent(context, MatchDetailActivity.class);
        intent.putExtra(EXTRA_MATCH_ID, matchId);
        context.startActivity(intent);
    }

    @Override
    public void fetchMatchDetailInfoSuccess(MatchDetail matchDetail) {
        this.mMatchDetail = matchDetail;
        dismissLoadingDialog();
        setUpTopView();
        setUpAdapter();
    }

    private void setUpTopView() {
        mTvStartTime.setText(TimeHelper.convertTimeToFormat(mMatchDetail.start_time));
        mTvDuration.setText(TimeHelper.secondToMinWithUnit(mMatchDetail.duration));
        // FIXME: 2017/7/14 这种方式有问题,尝试修复
        if (mMatchDetail.lobby_type == LobbyType.LOBBY_TYPE_RANKED) {
            List<String> rank = new ArrayList<>();
            for (MatchPlayInfo player : mMatchDetail.players) {
                rank.add(player.solo_competitive_rank);
            }
            mTvMatchLevel.setText(Common.getAverageMmrAverageLevel(rank.toArray(new String[0])));
            mTvGameMode.setText(Common.getLobbyTypeName(mMatchDetail.lobby_type));
        } else {
            mTvMatchLevel.setText(Common.getLobbyTypeName(mMatchDetail.lobby_type));
            mTvGameMode.setText(Common.getGameMode(mMatchDetail.game_mode));
        }

    }

    private void setUpAdapter() {
        FragmentPagerAdapter mPageAdapter = new MatchDetailPageAdapter(getSupportFragmentManager());
        mVp.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mVp);
    }

    class MatchDetailPageAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{"概述", "图表", "事件"};

        MatchDetailPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MatchOverviewFragment.newInstance(mMatchDetail);
                case 1:
                    return MatchGrphsFragment.newInstance(mMatchDetail);
                case 2:
                    return MatchOverviewFragment.newInstance(mMatchDetail);
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
