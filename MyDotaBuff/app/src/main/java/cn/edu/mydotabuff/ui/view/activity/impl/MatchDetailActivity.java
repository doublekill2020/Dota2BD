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

import butterknife.BindView;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
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
    private FragmentPagerAdapter mPageAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mVp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_match_detail_new);
        setSupportActionBar(toolbar);
        mPresenter = new MatchDetailPresenterImpl(this);
        String stringExtra = getIntent().getStringExtra(EXTRA_MATCH_ID);
        mPresenter.fetchMatchDetailInfo(stringExtra);
        setUpAdapter();
    }

    private void setUpAdapter() {
        mPageAdapter = new MatchDetailPageAdapter(getSupportFragmentManager());
        mVp.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mVp);
    }

    public static void start(Context context, String matchId) {
        Intent intent = new Intent(context, MatchDetailActivity.class);
        intent.putExtra(EXTRA_MATCH_ID, matchId);
        context.startActivity(intent);
    }

    class MatchDetailPageAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{"TAB1", "TAB2", "TAB3"};

        public MatchDetailPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MatchOverviewFragment.newInstance(mPresenter.getMatchDetail());
                case 1:
                    return new MatchOverviewFragment();
                case 2:
                    return new MatchOverviewFragment();
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
