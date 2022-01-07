package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.common.EventTag;
import cn.edu.mydotabuff.model.LobbyType;
import cn.edu.mydotabuff.model.Match;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.model.Rating;
import cn.edu.mydotabuff.ui.presenter.IFollowFragmentPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.FollowFragmentPresenterImpl;
import cn.edu.mydotabuff.ui.view.IFollowFragmentView;
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
    @BindView(R.id.fl_success)
    FrameLayout mFlSuccess;
    private BaseListAdapter<Match> mAdapter;
    private List<Match> mMatches = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_base, container, false);
        setHasOptionsMenu(true);
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
                .fragment_follow_item, EventTag.CLICK_TO_MATCH_DETAIL) {
            @Override
            public void getView(BaseListHolder holder, final Match match, int pos) {
                holder.setImageURI(R.id.sdv_hero_icon, Utils.getHeroImageUriForFresco(Common.getHeroName
                        (match.hero_id)));
                holder.setText(R.id.tv_kda, match.kills + "/" + match.deaths + "/" + match.assists);
                holder.setText(R.id.tv_time, TimeHelper.convertTimeToFormat(match.start_time));
                if (Common.getMatchResult(match.player_slot, match.radiant_win)) {
                    holder.setText(R.id.tv_game_status, R.string.match_result_win);
                    holder.setTextColor(R.id.tv_game_status, R.color.my_green);
                } else {
                    holder.setText(R.id.tv_game_status, R.string.match_result_lose);
                    holder.setTextColor(R.id.tv_game_status, R.color.my_orange);
                }
                holder.setText(R.id.tv1, Common.getLobbyTypeName(match.lobby_type));
                if(mPresenter.getPlayerInfoMap() == null){
                    return;
                }
                PlayerInfo playerInfo = mPresenter.getPlayerInfoMap().get(match.account_id);
                if (playerInfo != null) {
                    holder.setImageURI(R.id.sdv_user_icon, playerInfo.profile.avatar);
                    holder.setText(R.id.tv_player_name, playerInfo.profile.personaname);
                }
                if (match.lobby_type == LobbyType.LOBBY_TYPE_RANKED) {
                    Rating rating = mPresenter.getRealm().where(Rating.class).equalTo("id", match.account_id + match.match_id).findFirst();
                    if (rating != null && !TextUtils.isEmpty(rating.solo_competitive_rank)) {
                        String mmr = rating.solo_competitive_rank;
                        holder.setText(R.id.tv1, mmr + Common.getMmrLevel(mmr));
                    } else {
                        holder.setText(R.id.tv1, R.string.rank_level_unknow);
                    }
                }
                holder.setOnClickListener(R.id.sdv_user_icon, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PlayerDetailActivity.start(mActivity, match.account_id);
                    }
                });
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

    @Override
    public void notifyDataUpdate() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_follow_fragment, menu);
        final SearchView searchView = (SearchView) menu.findItem(
                R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.follow_fragment_search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String arg0) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }

    @Override
    public void refresh() {
        mRvList.refresh();
    }
}
