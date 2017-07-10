package cn.edu.mydotabuff.ui.presenter.impl;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;

import cn.edu.mydotabuff.base.BaseListClickEvent;
import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.ui.presenter.IPlayerDetailPresenter;
import cn.edu.mydotabuff.ui.view.IPlayerDetailView;
import io.realm.Realm;

/**
 * Created by nevermore on 2017/7/10 0010.
 */

public class PlayerDetailPresenterImpl extends BasePresenterImpl<IPlayerDetailView> implements IPlayerDetailPresenter {
    public PlayerDetailPresenterImpl(IPlayerDetailView view) {
        super(view);
        RxBus.get().register(this);
    }

    @Override
    public void follow() {

    }
    @Subscribe
    public void onItemClicked(BaseListClickEvent event){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }
}
