package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.PageFragment;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.ui.adapter.MathOverviewAdapter;
import cn.edu.mydotabuff.ui.presenter.IMatchOverviewPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.MatchOverviewPresenterImpl;
import cn.edu.mydotabuff.ui.view.IMatchOverviewView;

/**
 * Created by sadhu on 2017/7/10.
 * 描述: 比赛详情_概述
 */
public class MatchOverviewFragment extends PageFragment<IMatchOverviewPresenter> implements IMatchOverviewView {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    private MatchDetail matchDetail;
    private MathOverviewAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_match_over_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();
        Bundle arguments = getArguments();
        matchDetail = arguments.getParcelable("key");
        mPresenter = new MatchOverviewPresenterImpl(this);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvList.setAdapter(mAdapter = new MathOverviewAdapter(matchDetail));
    }

    public static MatchOverviewFragment newInstance(MatchDetail matchDetail) {
        MatchOverviewFragment matchOverviewFragment = new MatchOverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", matchDetail);
        matchOverviewFragment.setArguments(bundle);
        return matchOverviewFragment;
    }
}
