package cn.edu.mydotabuff.ui.presenter.impl;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;

import cn.edu.mydotabuff.base.BaseListClickEvent;
import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IPlayerDetailPresenter;
import cn.edu.mydotabuff.ui.service.PlayerInfoService;
import cn.edu.mydotabuff.ui.view.IPlayerDetailView;

/**
 * Created by nevermore on 2017/7/10 0010.
 */

public class PlayerDetailPresenterImpl extends BasePresenterImpl<IPlayerDetailView> implements IPlayerDetailPresenter {
    public PlayerDetailPresenterImpl(IPlayerDetailView view) {
        super(view,true);
        RxBus.get().register(this);
    }

    @Override
    public void follow() {

    }

    @Override
    public PlayerInfo queryPlayerInfo(String accountId) {
        return PlayerInfoService.querySinglePlayerInfo(getRealm(), accountId);
    }

    @Subscribe
    public void onItemClicked(BaseListClickEvent event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }
}
