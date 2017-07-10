package cn.edu.mydotabuff.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail_base);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mPresenter = new PlayerDetailPresenterImpl(this);
    }

    public static void start(Context context, String accountId) {
        Intent starter = new Intent(context, PlayerDetailActivity.class);
        starter.putExtra(PLAYER_ID, accountId);
        context.startActivity(starter);
    }
}
