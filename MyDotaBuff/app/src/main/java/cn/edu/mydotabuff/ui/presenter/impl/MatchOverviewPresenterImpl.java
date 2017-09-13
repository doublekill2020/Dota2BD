package cn.edu.mydotabuff.ui.presenter.impl;

import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.ui.presenter.IMatchOverviewPresenter;
import cn.edu.mydotabuff.ui.view.IMatchOverviewView;

/**
 * Created by sadhu on 2017/7/10.
 * 描述:
 */
public class MatchOverviewPresenterImpl extends BasePresenterImpl<IMatchOverviewView> implements IMatchOverviewPresenter {
    public MatchOverviewPresenterImpl(IMatchOverviewView view) {
        super(view);
    }
}
