package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.base.BaseListAdapter;
import cn.edu.mydotabuff.base.BaseListHolder;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.model.MatchPlayInfo;
import cn.edu.mydotabuff.ui.presenter.IMatchOverviewPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.MatchOverviewPresenterImpl;
import cn.edu.mydotabuff.ui.view.IMatchOverviewView;
import cn.edu.mydotabuff.util.Utils;
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
    private MatchDetail matchDetail;
    private BaseListAdapter<MatchPlayInfo> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_match_over_view, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private static final String TAG = "MatchOverviewFragment";

    private void init() {
        setSuccessView(mFlSuccess);
        Bundle arguments = getArguments();
        matchDetail = arguments.getParcelable("key");
        mPresenter = new MatchOverviewPresenterImpl(this);
        mRvList.setAdapter(mAdapter = new BaseListAdapter<MatchPlayInfo>(matchDetail.players, R.layout.item_match_detail_over_view) {
            @Override
            public void getView(BaseListHolder holder, MatchPlayInfo bean, int pos) {
                holder.setImageURI(R.id.sdv_hero_icon, Utils.getHeroImageUriForFresco(Common.getHeroName(bean.hero_id)));
                holder.setText(R.id.tv_level, String.valueOf(bean.level));
                holder.setText(R.id.tv_person_name, TextUtils.isEmpty(bean.personaname) ? getString(R.string.anonymous_player) : bean.personaname);
                holder.setText(R.id.tv_kda, String.format(Locale.CHINA, "KDA: %d", bean.kda));
                holder.setText(R.id.tv_KDA, String.format(Locale.CHINA, "%d/%d/%d", bean.kills, bean.deaths, bean.assists));

                holder.setImageURI(R.id.item0, Utils.getItemsImageUri(Common.getItemName(bean.item_0)));
                holder.setImageURI(R.id.item1, Utils.getItemsImageUri(Common.getItemName(bean.item_1)));
                holder.setImageURI(R.id.item2, Utils.getItemsImageUri(Common.getItemName(bean.item_2)));
                holder.setImageURI(R.id.item3, Utils.getItemsImageUri(Common.getItemName(bean.item_3)));
                holder.setImageURI(R.id.item4, Utils.getItemsImageUri(Common.getItemName(bean.item_4)));
                holder.setImageURI(R.id.item5, Utils.getItemsImageUri(Common.getItemName(bean.item_5)));
            }
        });
    }

    public static MatchOverviewFragment newInstance(MatchDetail matchDetail) {
        MatchOverviewFragment matchOverviewFragment = new MatchOverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", matchDetail);
        matchOverviewFragment.setArguments(bundle);
        return matchOverviewFragment;
    }
}
