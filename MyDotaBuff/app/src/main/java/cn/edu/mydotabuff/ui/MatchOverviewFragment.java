package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.ui.presenter.IMatchOverviewPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.MatchOverviewPresenterImpl;
import cn.edu.mydotabuff.ui.view.IMatchOverviewView;
import cn.edu.mydotabuff.view.SwipeRefreshRecycleView;

/**
 * Created by sadhu on 2017/7/10.
 * 描述: 比赛详情_概述
 */
public class MatchOverviewFragment extends BaseFragment<IMatchOverviewPresenter> implements IMatchOverviewView {
    @BindView(R.id.rv_list)
    SwipeRefreshRecycleView mRvList;
    @BindView(R.id.fl_success)
    FrameLayout mFlSuccess;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_match_over_view, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        setSuccessView(mFlSuccess);
        mPresenter = new MatchOverviewPresenterImpl(this);
    }

    public MatchOverviewFragment newInstance(MatchDetail matchDetail) {
        MatchOverviewFragment matchOverviewFragment = new MatchOverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", matchDetail);
        matchOverviewFragment.setArguments(bundle);
        return matchOverviewFragment;
    }
}
