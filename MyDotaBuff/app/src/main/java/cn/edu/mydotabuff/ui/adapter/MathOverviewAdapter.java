package cn.edu.mydotabuff.ui.adapter;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.model.MatchPlayInfo;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.drawable.DirectionDrawale;

/**
 * Created by sadhu on 2017/7/14.
 * 描述
 */
public class MathOverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_SECTION_RADIANT = 1;
    private static final int TYPE_SECTION_DIRE = 2;
    private static final int TYPE_NORMAL_RADIANT = 3;
    private static final int TYPE_NORMAL_DIRE = 4;
    private MatchDetail mDetail;
    private float mRadiantDamage;
    private int mRadianGPM;
    private int mRadianXPM;
    private float mDireDamage;
    private int mDireGPM;
    private int mDireXPM;

    public MathOverviewAdapter(MatchDetail detail) {
        this.mDetail = detail;
        for (MatchPlayInfo player : mDetail.players) {
            if (Integer.valueOf(player.player_slot) > 4) {
                mDireDamage += player.hero_damage;
                mRadianGPM += player.gold_per_min;
                mRadianXPM += player.xp_per_min;
            } else {
                mRadiantDamage += player.hero_damage;
                mDireGPM += player.gold_per_min;
                mDireXPM += player.xp_per_min;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_SECTION_RADIANT) {
            holder = new SectionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_overview_section_radiant, parent, false));
        } else if (viewType == TYPE_SECTION_DIRE) {
            holder = new SectionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_overview_section_dire, parent, false));
        } else if (viewType == TYPE_NORMAL_RADIANT || viewType == TYPE_NORMAL_DIRE) {
            holder = new MatchItemVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_overview_normal, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_SECTION_RADIANT) {
            ((SectionVH) holder).mImgColorTag.setImageDrawable(getDirectionDrawable(holder, R.color.radiantColor));
            // FIXME: 2017/8/4  放String里
            ((SectionVH) holder).mTvSumKill.setText("杀敌 " + String.valueOf(mDetail.radiant_score));
            ((SectionVH) holder).mTvGPM.setText("经验 " + mRadianGPM);
            ((SectionVH) holder).mTvXPM.setText("金钱 " + mRadianXPM);


            ((SectionVH) holder).mTvMatchResult.setText(String.valueOf(mDetail.radiant_win ? "胜利" : "失败"));
        } else if (viewType == TYPE_SECTION_DIRE) {
            //"别紧张，你这样没事",
            //"英雄不见了！",
            //"英雄回来了！",
            // "相当精彩的比赛",
            //"技不如人，甘拜下风",
            //"走好, 不送",
            //"玩不了啦!",
            //"破两路更好打, 是吧?"
            //"Ай-ай-ай-ай-ай, что сейчас произошло!"
            ((SectionVH) holder).mTvRandom.setText("Ай-ай-ай-ай-ай, что сейчас произошло!");

            // FIXME: 2017/8/4  放String里
            ((SectionVH) holder).mImgColorTag.setImageDrawable(getDirectionDrawable(holder, R.color.direColor));
            ((SectionVH) holder).mTvSumKill.setText("杀敌 " + String.valueOf(mDetail.dire_score));
            ((SectionVH) holder).mTvGPM.setText("经验 " + mDireGPM);
            ((SectionVH) holder).mTvXPM.setText("金钱 " + mDireXPM);
            ((SectionVH) holder).mTvMatchResult.setText(String.valueOf(mDetail.radiant_win ? "失败" : "胜利"));
        } else if (viewType == TYPE_NORMAL_RADIANT) {
            ((MatchItemVH) holder).bindData(mDetail.players.get(position - 1));
        } else if (viewType == TYPE_NORMAL_DIRE) {
            ((MatchItemVH) holder).bindData(mDetail.players.get(position - 2));
        }
    }

    @NonNull
    private DirectionDrawale getDirectionDrawable(RecyclerView.ViewHolder holder, @ColorRes int colorId) {
        return new DirectionDrawale(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, holder.itemView.getContext().getResources().getDisplayMetrics()),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, holder.itemView.getContext().getResources().getDisplayMetrics()),
                ContextCompat.getColor(holder.itemView.getContext(), colorId));
    }

    @Override
    public int getItemCount() {
        return mDetail == null || mDetail.players == null || mDetail.players.size() == 0
                ? 0
                : mDetail.players.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        // 0(section) 1 2 3 4 5 6(section) 7 8 9 10 11
        if (position == 0) {
            return TYPE_SECTION_RADIANT;
        } else if (position == 6) {
            return TYPE_SECTION_DIRE;
        } else if (position < 6) {
            return TYPE_NORMAL_RADIANT;
        } else {
            return TYPE_NORMAL_DIRE;
        }
    }

    class SectionVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_random_txt)
        TextView mTvRandom;
        @BindView(R.id.iv_match_color_tag)
        ImageView mImgColorTag;
        @BindView(R.id.tv_match_result)
        TextView mTvMatchResult;
        @BindView(R.id.tv_match_sumkill)
        TextView mTvSumKill;
        @BindView(R.id.tv_match_xmp)
        TextView mTvXPM;
        @BindView(R.id.tv_match_gpm)
        TextView mTvGPM;

        public SectionVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class MatchItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.sdv_hero_icon)
        SimpleDraweeView mHeroIcon;
        @BindView(R.id.tv_level)
        TextView mHeroLevel;
        @BindView(R.id.tv_person_name)
        TextView mPersonName;
        @BindView(R.id.tv_kda)
        TextView mTvKDA;
        @BindView(R.id.tv_KDA)
        TextView mTvKDADetail;
        @BindView(R.id.tv_fighting_participation)
        TextView mTvFighting;
        @BindView(R.id.tv_damaging)
        TextView mTvDamaging;
        @BindView(R.id.item0)
        SimpleDraweeView mItem0;
        @BindView(R.id.item1)
        SimpleDraweeView mItem1;
        @BindView(R.id.item2)
        SimpleDraweeView mItem2;
        @BindView(R.id.item3)
        SimpleDraweeView mItem3;
        @BindView(R.id.item4)
        SimpleDraweeView mItem4;
        @BindView(R.id.item5)
        SimpleDraweeView mItem5;


        public MatchItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bindData(MatchPlayInfo bean) {
            mHeroIcon.setImageURI(Utils.getHeroImageUriForFresco(Common.getHeroName(bean.hero_id)));
            mHeroLevel.setText(String.valueOf(bean.level));
            mPersonName.setText(TextUtils.isEmpty(bean.personaname) ? itemView.getContext().getString(R.string.anonymous_player) : bean.personaname);
            mTvKDA.setText(String.format(Locale.CHINA, "KDA: %.2f", bean.kda));
            mTvFighting.setText(String.format(Locale.CHINA, "参战率: %.1f%%", bean.teamfight_participation * 100));
            mTvDamaging.setText(String.format(Locale.CHINA, "伤害: %.1f%%",
                    bean.hero_damage * 100 / (Integer.valueOf(bean.player_slot) > 4 ? mDireDamage : mRadiantDamage)));
            mTvKDADetail.setText(String.format(Locale.CHINA, "%d/%d/%d", bean.kills, bean.deaths, bean.assists));
            mItem0.setImageURI(Utils.getItemsImageUri(Common.getItemName(bean.item_0)));
            mItem1.setImageURI(Utils.getItemsImageUri(Common.getItemName(bean.item_1)));
            mItem2.setImageURI(Utils.getItemsImageUri(Common.getItemName(bean.item_2)));
            mItem3.setImageURI(Utils.getItemsImageUri(Common.getItemName(bean.item_3)));
            mItem4.setImageURI(Utils.getItemsImageUri(Common.getItemName(bean.item_4)));
            mItem5.setImageURI(Utils.getItemsImageUri(Common.getItemName(bean.item_5)));
        }
    }

}
