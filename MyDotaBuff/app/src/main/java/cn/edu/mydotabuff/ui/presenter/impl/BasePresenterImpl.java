package cn.edu.mydotabuff.ui.presenter.impl;

import android.support.annotation.CallSuper;

import cn.edu.mydotabuff.ui.presenter.IBasePresenter;
import cn.edu.mydotabuff.ui.view.activity.IBaseView;

/**
 * Created by sadhu on 2017/7/5.
 * 描述:
 */
public class BasePresenterImpl<V extends IBaseView> implements IBasePresenter {
    protected V mView;

    public BasePresenterImpl(V view) {
        mView = view;
    }

    @CallSuper
    @Override
    public void onDestory() {
        mView = null;
    }
}
