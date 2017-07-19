package cn.edu.mydotabuff.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.view.LineCharView;

/**
 * Created by sadhu on 2017/7/14.
 * 描述
 */
public class MatchGrphsFragment extends BaseFragment {
    @BindView(R.id.lineChar)
    LineCharView mLineChar;
    private MatchDetail matchDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_match_graph, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        Bundle arguments = getArguments();
        matchDetail = arguments.getParcelable("key");
        Map<String, Integer[]> listMap = new HashMap<>();
        Integer[] integers1 = new Integer[matchDetail.radiant_gold_adv.size()];
        for (int i = 0; i < matchDetail.radiant_gold_adv.size(); i++) {
            integers1[i] = matchDetail.radiant_gold_adv.get(i).val;
        }
        Integer[] integers2 = new Integer[matchDetail.radiant_xp_adv.size()];
        for (int i = 0; i < matchDetail.radiant_xp_adv.size(); i++) {
            integers2[i] = matchDetail.radiant_xp_adv.get(i).val;
        }

        listMap.put("radiant_gold_adv", integers1);
        listMap.put("radiant_xp_adv", integers2);
        mLineChar.putLinesData(listMap);
    }

    public static MatchGrphsFragment newInstance(MatchDetail matchDetail) {
        MatchGrphsFragment matchGrphsFragment = new MatchGrphsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", matchDetail);
        matchGrphsFragment.setArguments(bundle);
        return matchGrphsFragment;
    }

}
