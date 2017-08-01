package cn.edu.mydotabuff.ui.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.PageFragment;
import cn.edu.mydotabuff.base.realm.RealmInt;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.view.GrahInfoWindow;
import cn.edu.mydotabuff.view.LineCharView;

/**
 * Created by sadhu on 2017/7/14.
 * 描述
 */
public class MatchGrphsFragment extends PageFragment {
    @BindView(R.id.lineChar)
    LineCharView mLineChar;
    private MatchDetail matchDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_match_graph, container, false);
        ButterKnife.bind(this, view);
        mLineChar.setOnPointHitListener(new LineCharView.OnPointHitListener() {
            @Override
            public void onHit(LineCharView.HitInfo info) {

            }

            @Override
            public void onDismiss() {

            }
        });
        return view;
    }

    @Override
    public void onFirstUserVisible() {
        Bundle arguments = getArguments();
        matchDetail = arguments.getParcelable("key");

        LineCharView.LineInfo goldLineInfo = new LineCharView.LineInfo();
        RealmInt[] goldArray = matchDetail.radiant_gold_adv.toArray(new RealmInt[0]);
        goldLineInfo.color = Color.parseColor("#E97815");
        goldLineInfo.values = goldArray;
        goldLineInfo.name = "GOLD";


        LineCharView.LineInfo xpLineInfo = new LineCharView.LineInfo();
        RealmInt[] xpArray = matchDetail.radiant_xp_adv.toArray(new RealmInt[0]);
        xpLineInfo.color = Color.parseColor("#206FA7");
        xpLineInfo.values = xpArray;
        xpLineInfo.name = "XP";

        List<LineCharView.LineInfo> lineInfoList = new ArrayList<>();
        lineInfoList.add(goldLineInfo);
        lineInfoList.add(xpLineInfo);
        mLineChar.putLinesData(lineInfoList);
    }

    public static MatchGrphsFragment newInstance(MatchDetail matchDetail) {
        MatchGrphsFragment matchGrphsFragment = new MatchGrphsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", matchDetail);
        matchGrphsFragment.setArguments(bundle);
        return matchGrphsFragment;
    }


    private void showGrahPopupWindow() {
        GrahInfoWindow grahInfoWindow = new GrahInfoWindow(getContext());
        grahInfoWindow.showAtLocation(mLineChar, Gravity.LEFT | Gravity.TOP, 2, 2);
    }

}
