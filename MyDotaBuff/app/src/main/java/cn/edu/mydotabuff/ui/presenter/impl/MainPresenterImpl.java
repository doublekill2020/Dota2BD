package cn.edu.mydotabuff.ui.presenter.impl;

import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.ui.presenter.IMainPresenter;
import cn.edu.mydotabuff.ui.view.IMainView;

/**
 * Created by nevermore on 2017/7/6 0006.
 */

public class MainPresenterImpl extends BasePresenterImpl<IMainView> implements IMainPresenter {
    public MainPresenterImpl(IMainView view) {
        super(view);
    }
    @Override
    public void doSyncMatch() {

    }
}
