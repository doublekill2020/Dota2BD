package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.model.PlayerInfo;

/**
 * Created by nevermore on 2017/7/4 0004.
 */

public class StatisticsFragment extends BaseFragment {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static StatisticsFragment newInstance(PlayerInfo playerInfo) {

        Bundle args = new Bundle();
        args.putSerializable(PlayerDetailActivity.PLAYER_INFO, playerInfo);
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
