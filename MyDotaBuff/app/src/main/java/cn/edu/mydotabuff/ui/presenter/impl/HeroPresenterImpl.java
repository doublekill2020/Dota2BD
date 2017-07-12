package cn.edu.mydotabuff.ui.presenter.impl;

import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.ui.presenter.IHeroPresenter;
import cn.edu.mydotabuff.ui.view.IHeroView;

/**
 * Created by nevermore on 2017/7/12 0012.
 */

public class HeroPresenterImpl extends BasePresenterImpl<IHeroView> implements IHeroPresenter{
    public HeroPresenterImpl(IHeroView view, boolean initRealm) {
        super(view, initRealm);
    }
}
