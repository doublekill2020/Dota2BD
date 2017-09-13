package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.base.BaseListAdapter;
import cn.edu.mydotabuff.base.BaseListHolder;
import cn.edu.mydotabuff.common.EventTag;
import cn.edu.mydotabuff.model.Hero;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IHeroPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.HeroPresenterImpl;
import cn.edu.mydotabuff.ui.view.IHeroView;
import cn.edu.mydotabuff.view.SwipeRefreshRecycleView;

/**
 * Created by nevermore on 2017/7/11 0011.
 */

public class HeroFragment extends BaseFragment<IHeroPresenter> implements IHeroView {
    @BindView(R.id.rv_list)
    SwipeRefreshRecycleView rvList;
    @BindView(R.id.fl_success)
    FrameLayout flSuccess;
    private BaseListAdapter<Hero> mAdapter;
    private List<Hero> heroes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hero_base, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        setSuccessView(flSuccess);
        rvList.setRefreshLoadMoreListener(new SwipeRefreshRecycleView.RefreshLoadMoreListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {

            }
        });
        rvList.setAdapter(mAdapter = new BaseListAdapter<Hero>(heroes, R.layout
                .frag_hero_used_list_item, EventTag.CLICK_TO_HERO_DETAIL) {
            @Override
            public void getView(BaseListHolder holder, final Hero hero, int pos) {
            }
        });
        mPresenter = new HeroPresenterImpl(this, true);
    }

    public static HeroFragment newInstance(PlayerInfo playerInfo) {

        Bundle args = new Bundle();
        args.putSerializable(PlayerDetailActivity.PLAYER_INFO, playerInfo);
        HeroFragment fragment = new HeroFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
