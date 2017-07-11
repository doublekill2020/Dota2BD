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

import butterknife.BindView;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.ui.MatchOverviewFragment;
import cn.edu.mydotabuff.ui.presenter.IMatchDetaiPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.MatchDetailPresenterImpl;
import cn.edu.mydotabuff.ui.view.activity.IMatchDetailView;

/**
 * Created by sadhu on 2017/7/5.
 * 描述: 比赛详情
 */
public class MatchDetailActivity extends BaseActivity<IMatchDetaiPresenter> implements IMatchDetailView {

    public static final String EXTRA_MATCH_ID = "match_id";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
        //setUpAdapter();
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
        setUpAdapter();
    }

    private void setUpAdapter() {
        FragmentPagerAdapter mPageAdapter = new MatchDetailPageAdapter(getSupportFragmentManager());
        mVp.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mVp);
    }

    class MatchDetailPageAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{"TAB1", "TAB2", "TAB3"};

        MatchDetailPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MatchOverviewFragment.newInstance(mMatchDetail);
                case 1:
                    return MatchOverviewFragment.newInstance(mMatchDetail);
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
