package cn.edu.mydotabuff.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.model.MatchPlayInfo;
import cn.edu.mydotabuff.ui.presenter.IMatchOverviewPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.MatchOverviewPresenterImpl;
import cn.edu.mydotabuff.ui.view.IMatchOverviewView;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.drawable.DirectionDrawale;

/**
 * Created by sadhu on 2017/7/10.
 * 描述: 比赛详情_概述
 */
public class MatchOverviewFragment extends BaseFragment<IMatchOverviewPresenter> implements IMatchOverviewView {

    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.iv_radiant_tag)
    ImageView mImgRadiantTag;
    @BindView(R.id.iv_dire_tag)
    ImageView mImgRireTag;

    private MatchDetail matchDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_match_over_view, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void setImageURI(View parent, int id, String url) {
        SimpleDraweeView view = parent.findViewById(id);
        view.setImageURI(Uri.parse(url));
    }

    public void setText(View parent, int id, String text) {
        TextView view = parent.findViewById(id);
        view.setText(text);
    }

    private void init() {
        Bundle arguments = getArguments();
        matchDetail = arguments.getParcelable("key");
        mPresenter = new MatchOverviewPresenterImpl(this);
        mImgRadiantTag.setImageDrawable(new DirectionDrawale(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, getResources().getDisplayMetrics()),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, getResources().getDisplayMetrics()),
                ContextCompat.getColor(getContext(), R.color.radiantColor)));

        mImgRireTag.setImageDrawable(new DirectionDrawale(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, getResources().getDisplayMetrics()),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, getResources().getDisplayMetrics()),
                ContextCompat.getColor(getContext(), R.color.direColor)));


        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_match_detail_over_view, mLlContainer, false);
            MatchPlayInfo bean = matchDetail.players.get(i);
            setImageURI(view, R.id.sdv_hero_icon, Utils.getHeroImageUriForFresco(Common.getHeroName(bean.hero_id)));
            setText(view, R.id.tv_level, String.valueOf(bean.level));
            setText(view, R.id.tv_person_name, TextUtils.isEmpty(bean.personaname) ? getString(R.string.anonymous_player) : bean.personaname);
            setText(view, R.id.tv_kda, String.format(Locale.CHINA, "KDA: %.2f", bean.kda));
            setText(view, R.id.tv_KDA, String.format(Locale.CHINA, "%d/%d/%d", bean.kills, bean.deaths, bean.assists));

            setImageURI(view, R.id.item0, Utils.getItemsImageUri(Common.getItemName(bean.item_0)));
            setImageURI(view, R.id.item1, Utils.getItemsImageUri(Common.getItemName(bean.item_1)));
            setImageURI(view, R.id.item2, Utils.getItemsImageUri(Common.getItemName(bean.item_2)));
            setImageURI(view, R.id.item3, Utils.getItemsImageUri(Common.getItemName(bean.item_3)));
            setImageURI(view, R.id.item4, Utils.getItemsImageUri(Common.getItemName(bean.item_4)));
            setImageURI(view, R.id.item5, Utils.getItemsImageUri(Common.getItemName(bean.item_5)));
            if (i > 4) {
                mLlContainer.addView(view, 2 + i);
            } else {
                mLlContainer.addView(view, 1 + i);
            }

        }

//        mRvList.setAdapter(mAdapter = new BaseListAdapter<MatchPlayInfo>(matchDetail.players, R.layout.item_match_detail_over_view) {
//            @Override
//            public void getView(BaseListHolder holder, MatchPlayInfo bean, int pos) {
//                holder.setImageURI(R.id.sdv_hero_icon, Utils.getHeroImageUriForFresco(Common.getHeroName(bean.hero_id)));
//                holder.setText(R.id.tv_level, String.valueOf(bean.level));
//                holder.setText(R.id.tv_person_name, TextUtils.isEmpty(bean.personaname) ? getString(R.string.anonymous_player) : bean.personaname);
//                holder.setText(R.id.tv_kda, String.format(Locale.CHINA, "KDA: %.2f", bean.kda));
//                holder.setText(R.id.tv_KDA, String.format(Locale.CHINA, "%d/%d/%d", bean.kills, bean.deaths, bean.assists));
//
//                holder.setImageURI(R.id.item0, Utils.getItemsImageUri(Common.getItemName(bean.item_0)));
//                holder.setImageURI(R.id.item1, Utils.getItemsImageUri(Common.getItemName(bean.item_1)));
//                holder.setImageURI(R.id.item2, Utils.getItemsImageUri(Common.getItemName(bean.item_2)));
//                holder.setImageURI(R.id.item3, Utils.getItemsImageUri(Common.getItemName(bean.item_3)));
//                holder.setImageURI(R.id.item4, Utils.getItemsImageUri(Common.getItemName(bean.item_4)));
//                holder.setImageURI(R.id.item5, Utils.getItemsImageUri(Common.getItemName(bean.item_5)));
//            }
//        });
    }

    public static MatchOverviewFragment newInstance(MatchDetail matchDetail) {
        MatchOverviewFragment matchOverviewFragment = new MatchOverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", matchDetail);
        matchOverviewFragment.setArguments(bundle);
        return matchOverviewFragment;
    }
}
