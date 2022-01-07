package cn.edu.mydotabuff.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import cn.edu.mydotabuff.model.PlayedWithWrapper;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.model.Rating;
import cn.edu.mydotabuff.ui.adapter.SimplePaddingDecoration;
import cn.edu.mydotabuff.ui.presenter.IPlayedWithFragmentPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.PlayedWithFragmentPresenterImpl;
import cn.edu.mydotabuff.ui.view.IPlayedWithFragmentView;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.SwipeRefreshRecycleView;

public class PlayedWithFragment extends BaseFragment<IPlayedWithFragmentPresenter> implements IPlayedWithFragmentView {

    @BindView(R.id.rv_list)
    SwipeRefreshRecycleView mRvList;
    @BindView(R.id.fl_success)
    FrameLayout mFlSuccess;
    private BaseListAdapter<PlayedWithWrapper> mAdapter;
    private List<PlayedWithWrapper> infos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_played_with_base, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        final String accountID = getActivity().getSharedPreferences("user_info", Activity.MODE_PRIVATE)
                .getString("userID", "172750452");
        setSuccessView(mFlSuccess);
        mRvList.getRecycleView().addItemDecoration(new SimplePaddingDecoration(getContext()));
        mRvList.setRefreshLoadMoreListener(new SwipeRefreshRecycleView.RefreshLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData(accountID);
            }

            @Override
            public void onLoadMore() {

            }
        });
        mRvList.setPullLoadMoreEnable(false);
        NumberFormat nt = NumberFormat.getPercentInstance();
        mRvList.setAdapter(mAdapter = new BaseListAdapter<PlayedWithWrapper>(infos, R.layout
                .fragment_played_with_item, -1) {
            @SuppressLint("StringFormatMatches")
            @Override
            public void getView(BaseListHolder holder, final PlayedWithWrapper info, int pos) {
                if (!StringUtils.isEmpty(info.avatar)) {
                    holder.setImageURI(R.id.sdv_user_icon, info.avatar);
                }
                if (!StringUtils.isEmpty(info.personaname)) {
                    holder.setText(R.id.tv_player_name, info.personaname);
                }
                holder.setText(R.id.tv_time, TimeHelper.convertTimeToFormat(info.last_played));
                holder.setText(R.id.tvSteamID,"ID:"+info.account_id);

                int win1 = info.with_win;
                int lose1 = info.with_games - info.with_win;
                double rate1 = Double.parseDouble(win1 + "") / Double.parseDouble(info.with_games + "");
                holder.setText(R.id.tv1, String.format(getString(R.string.played_with_tips),
                        win1, lose1, nt.format(rate1)));

                int win2 = info.against_win;
                int lose2 = info.against_games - info.against_win;
                double rate2 = Double.parseDouble(win2 + "") / Double.parseDouble(info.against_games + "");
                holder.setText(R.id.tv2, String.format(getString(R.string.played_with_tips2),
                        win2, lose2, nt.format(rate2)));
                if (rate1 > rate2) {
                    holder.setText(R.id.tvNick, getString(R.string.played_with_god));
                    holder.setTextColor(R.id.tvNick, R.color.my_green);
                } else {
                    holder.setText(R.id.tvNick, getString(R.string.played_with_sb));
                    holder.setTextColor(R.id.tvNick, R.color.my_orange);
                }

                int mmr = (win1+win2-lose1-lose2) * 30;
                if (mmr > 0){
                    holder.setText(R.id.tvMMR,getString(R.string.mmr)+"+"+mmr);
                    holder.setTextColor(R.id.tvMMR, R.color.my_green);
                    holder.setText(R.id.tvNick, getString(R.string.played_with_god));
                    holder.setTextColor(R.id.tvNick, R.color.my_green);
                    holder.setVisibility(R.id.tvMMR,View.VISIBLE);
                    holder.setText(R.id.tvTips,getString(R.string.tips_add_friend));
                    holder.setTextColor(R.id.tvTips, R.color.my_green);
                }else if(mmr == 0){
                    holder.setText(R.id.tvMMR,"");
                    holder.setTextColor(R.id.tvMMR, R.color.my_green);
                    holder.setText(R.id.tvNick, getString(R.string.played_with_none));
                    holder.setTextColor(R.id.tvNick, R.color.my_green);
                    holder.setVisibility(R.id.tvMMR,View.GONE);
                    holder.setText(R.id.tvTips,getString(R.string.tips_add_friend));
                    holder.setTextColor(R.id.tvTips, R.color.my_green);
                } else {
                    holder.setText(R.id.tvMMR,getString(R.string.mmr)+mmr);
                    holder.setTextColor(R.id.tvMMR, R.color.my_orange);
                    holder.setText(R.id.tvNick, getString(R.string.played_with_sb));
                    holder.setTextColor(R.id.tvNick, R.color.my_orange);
                    holder.setVisibility(R.id.tvMMR,View.VISIBLE);
                    holder.setText(R.id.tvTips,getString(R.string.tips_delete));
                    holder.setTextColor(R.id.tvTips, R.color.my_orange);
                }
                holder.setRootOnClickerListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //PlayerDetailActivity.start(mActivity, info.account_id+"");
                    }
                });
            }
        });
        mPresenter = new PlayedWithFragmentPresenterImpl(this);
        mRvList.refresh();
    }

    @Override
    public void notifyDataUpdate(List<PlayedWithWrapper> data) {
        infos.clear();
        infos.addAll(data);
        Collections.sort(infos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshCompleted() {
        mRvList.setRefreshCompleted();
    }

}
