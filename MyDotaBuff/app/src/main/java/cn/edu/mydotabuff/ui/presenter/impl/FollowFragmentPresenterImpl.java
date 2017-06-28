package cn.edu.mydotabuff.ui.presenter.impl;

import cn.edu.mydotabuff.ui.presenter.IFollowFragmentPresenter;
import cn.edu.mydotabuff.ui.view.IFollowFragmentView;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public class FollowFragmentPresenterImpl implements IFollowFragmentPresenter {
    private IFollowFragmentView mView;

    public FollowFragmentPresenterImpl(IFollowFragmentView view) {
        mView = view;
    }

    @Override
    public void onDestroy() {

    }
}
