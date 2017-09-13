package cn.edu.mydotabuff.ui;

import android.os.Bundle;

import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.model.PlayerInfo;

/**
 * Created by nevermore on 2017/7/11 0011.
 */

public class FriendFragment extends BaseFragment {
    public static FriendFragment newInstance(PlayerInfo playerInfo) {

        Bundle args = new Bundle();
        args.putSerializable(PlayerDetailActivity.PLAYER_INFO, playerInfo);
        FriendFragment fragment = new FriendFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
