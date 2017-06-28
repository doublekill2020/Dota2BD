package cn.edu.mydotabuff.ui.presenter.impl;

import cn.edu.mydotabuff.ui.presenter.FollowFragmentPresenter;
import cn.edu.mydotabuff.ui.view.fragment.FollowFragmentView;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public class FollowFragmentPresenterImpl implements FollowFragmentPresenter {
    private FollowFragmentView mView;

    public FollowFragmentPresenterImpl(FollowFragmentView view) {
        mView = view;
    }

    @Override
    public void onDestroy() {

    }
}
