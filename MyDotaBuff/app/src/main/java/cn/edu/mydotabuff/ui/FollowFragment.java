package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
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
import cn.edu.mydotabuff.common.ClickTag;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.model.Match;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IFollowFragmentPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.FollowFragmentPresenterImpl;
import cn.edu.mydotabuff.ui.service.PlayerInfoService;
import cn.edu.mydotabuff.ui.view.fragment.IFollowFragmentView;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.SwipeRefreshRecycleView;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public class FollowFragment extends BaseFragment<IFollowFragmentPresenter> implements
        IFollowFragmentView {

    @BindView(R.id.rv_list)
    SwipeRefreshRecycleView mRvList;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.fl_success)
    FrameLayout mFlSuccess;
    private BaseListAdapter<Match> mAdapter;
    private List<Match> mMatches = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_base, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        setSuccessView(mFlSuccess);
        mRvList.setRefreshLoadMoreListener(new SwipeRefreshRecycleView.RefreshLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.doSync(mPresenter.getAllFollowers());
            }

            @Override
            public void onLoadMore() {

            }
        });
        mRvList.setAdapter(mAdapter = new BaseListAdapter<Match>(mMatches, R.layout
                .fragment_follow_item, ClickTag.CLICK_TO_DETAIL) {
            @Override
            public void getView(BaseListHolder holder, Match match, int pos) {
                holder.setImageURI(R.id.sdv_hero_icon, Utils.getHeroImageUriForFresco(Common.getHeroName
                        (match.hero_id)));
                holder.setText(R.id.tv_kda, match.kills + "/" + match.deaths + "/" + match.assists);
                holder.setText(R.id.tv_time, TimeHelper.convertTimeToFormat(match.start_time));
                PlayerInfo playerInfo = PlayerInfoService.queryPlayerInfo(mRealm, match.account_id);
                if (playerInfo != null) {
                    holder.setImageURI(R.id.sdv_user_icon, playerInfo.profile.avatar);
                    holder.setText(R.id.tv_player_name, playerInfo.profile.personaname);
                    if (TextUtils.isEmpty(playerInfo.solo_competitive_rank)) {
                        holder.setText(R.id.tv_mmr, R.string.rank_level_unknow);
                    } else {
                        holder.setText(R.id.tv_mmr, playerInfo.solo_competitive_rank + Common
                                .getMmrLevel(playerInfo.solo_competitive_rank));
                    }
                }
            }
        });
        mPresenter = new FollowFragmentPresenterImpl(this);
    }

    @Override
    public void setDataToRecycleView(List<Match> matches) {
        mMatches.clear();
        mMatches.addAll(matches);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshCompleted() {
        mRvList.setRefreshCompleted();
    }
}
