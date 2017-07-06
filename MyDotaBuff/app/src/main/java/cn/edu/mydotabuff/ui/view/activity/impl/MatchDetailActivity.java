package cn.edu.mydotabuff.ui.view.activity.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_match_detail_new);
        mPresenter = new MatchDetailPresenterImpl(this);
        String stringExtra = getIntent().getStringExtra(EXTRA_MATCH_ID);
        mPresenter.getMatchDetail(stringExtra);
    }

    public static void start(Context context, String matchId) {
        Intent intent = new Intent(context, MatchDetailActivity.class);
        intent.putExtra(EXTRA_MATCH_ID, matchId);
        context.startActivity(intent);
    }
}
