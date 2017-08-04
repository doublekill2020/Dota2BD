package cn.edu.mydotabuff.ui.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.PageFragment;
import cn.edu.mydotabuff.base.realm.RealmInt;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.model.MatchPlayInfo;
import cn.edu.mydotabuff.view.GrahInfoWindow;
import cn.edu.mydotabuff.view.linechar.LineCharView;

/**
 * Created by sadhu on 2017/7/14.
 * 描述
 */
public class MatchGrphsFragment extends PageFragment implements LineCharView.OnPointHitListener {
    @BindView(R.id.lineChar_team)
    LineCharView mLineCharTeam;
    @BindView(R.id.lineChar_gold)
    LineCharView lineCharGold;
    @BindView(R.id.lineChar_xp)
    LineCharView lineCharXP;
    private MatchDetail matchDetail;
    private GrahInfoWindow mGrahInfoWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_match_graph, container, false);
        ButterKnife.bind(this, view);
        mLineCharTeam.setOnPointHitListener(this);
        lineCharGold.setOnPointHitListener(this);
        lineCharXP.setOnPointHitListener(this);
        return view;
    }

    @Override
    public void onFirstUserVisible() {
        Bundle arguments = getArguments();
        matchDetail = arguments.getParcelable("key");

        LineCharView.LineInfo goldLineInfo = new LineCharView.LineInfo();
        goldLineInfo.color = Color.parseColor("#E97815");
        goldLineInfo.values = matchDetail.radiant_gold_adv.toArray(new RealmInt[0]);
        goldLineInfo.name = "GOLD";


        LineCharView.LineInfo xpLineInfo = new LineCharView.LineInfo();
        xpLineInfo.color = Color.parseColor("#206FA7");
        xpLineInfo.values = matchDetail.radiant_xp_adv.toArray(new RealmInt[0]);
        xpLineInfo.name = "XP";

        List<LineCharView.LineInfo> lineInfoList = new ArrayList<>();
        lineInfoList.add(goldLineInfo);
        lineInfoList.add(xpLineInfo);
        mLineCharTeam.putLinesData(lineInfoList);

        if (matchDetail.players != null) {
            List<LineCharView.LineInfo> playerGoldLineInfoList = new ArrayList<>();
            List<LineCharView.LineInfo> playerXPLineInfoList = new ArrayList<>();
            for (MatchPlayInfo player : matchDetail.players) {
                LineCharView.LineInfo goldInfo = new LineCharView.LineInfo();
                LineCharView.LineInfo XPInfo = new LineCharView.LineInfo();
                goldInfo.color = getContext().getResources().getColor(getResources().getIdentifier("player_color_" + player.player_slot, "color", getContext().getPackageName()));
                goldInfo.name = Common.getHeroCHSNameById(player.hero_id);
                goldInfo.values = player.gold_t.toArray(new RealmInt[0]);

                XPInfo.color = goldInfo.color;
                XPInfo.name = goldInfo.name;
                XPInfo.values = player.xp_t.toArray(new RealmInt[0]);
                playerGoldLineInfoList.add(goldInfo);
                playerXPLineInfoList.add(XPInfo);
            }
            lineCharGold.putLinesData(playerGoldLineInfoList);
            lineCharXP.putLinesData(playerXPLineInfoList);
        }

    }

    public static MatchGrphsFragment newInstance(MatchDetail matchDetail) {
        MatchGrphsFragment matchGrphsFragment = new MatchGrphsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", matchDetail);
        matchGrphsFragment.setArguments(bundle);
        return matchGrphsFragment;
    }


    private void showGrahPopupWindow(LineCharView.HitInfo info) {
        if (mGrahInfoWindow == null) {
            mGrahInfoWindow = new GrahInfoWindow(getContext());
        }
        mGrahInfoWindow.setData(info);
        mGrahInfoWindow.update();
    }

    @Override
    public void onHit(LineCharView.HitInfo info) {
        showGrahPopupWindow(info);
    }

    @Override
    public void onDismiss() {
        if (mGrahInfoWindow != null && mGrahInfoWindow.isShowing()) {
            mGrahInfoWindow.dismiss();
        }
    }
}
