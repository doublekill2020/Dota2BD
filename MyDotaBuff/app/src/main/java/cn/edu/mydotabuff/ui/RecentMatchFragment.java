package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.common.EventTag;
import cn.edu.mydotabuff.model.LobbyType;
import cn.edu.mydotabuff.model.Match;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.model.Rating;
import cn.edu.mydotabuff.ui.presenter.IRecentMatchPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.RecentMatchPresenterImpl;
import cn.edu.mydotabuff.ui.view.IRecentMatchView;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.util.Utils;

/**
 * Created by nevermore on 2017/7/11 0011.
 */

public class RecentMatchFragment extends BaseFragment<IRecentMatchPresenter> implements
        IRecentMatchView {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.fl_success)
    FrameLayout mFlSuccess;
    private BaseListAdapter<Match> mAdapter;
    private List<Match> mMatches = new ArrayList<>();
    private PlayerInfo mPlayerInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_match_base, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        mPlayerInfo = (PlayerInfo) getArguments().getSerializable(PlayerDetailActivity.PLAYER_INFO);
        setSuccessView(mFlSuccess);
        showLoadingLayout();
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mAdapter = new BaseListAdapter<Match>(mMatches, R.layout
                .fragment_follow_item, EventTag.PLAYER_DETAIL_CLICK_TO_MATCH_DETAIL) {
            @Override
            public void getView(BaseListHolder holder, final Match match, int pos) {
                holder.setImageURI(R.id.sdv_hero_icon, Utils.getHeroImageUriForFresco(Common
                        .getHeroName
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
                holder.setImageURI(R.id.sdv_user_icon, mPlayerInfo.profile.avatar);
                holder.setText(R.id.tv_player_name, mPlayerInfo.profile.personaname);
                if (match.lobby_type == LobbyType.LOBBY_TYPE_RANKED) {
                    Rating rating = mPresenter.getRealm().where(Rating.class).equalTo("id", match
                            .account_id + match.match_id).findFirst();
                    if (rating != null && !TextUtils.isEmpty(rating.solo_competitive_rank)) {
                        String mmr = rating.solo_competitive_rank;
                        holder.setText(R.id.tv1, mmr + Common.getMmrLevel(mmr));
                    } else {
                        holder.setText(R.id.tv1, R.string.rank_level_unknow);
                    }
                }
            }
        });
        mPresenter = new RecentMatchPresenterImpl(this);
        mPresenter.doSync(mPresenter.getAllFollowers());
    }

    @Override
    public void setDataToRecycleView(List<Match> matches) {
        mMatches.clear();
        mMatches.addAll(matches);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyDataUpdate() {
        mAdapter.notifyDataSetChanged();
    }

    public static RecentMatchFragment newInstance(PlayerInfo playerInfo) {
        Bundle args = new Bundle();
        args.putSerializable(PlayerDetailActivity.PLAYER_INFO, playerInfo);
        RecentMatchFragment fragment = new RecentMatchFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
