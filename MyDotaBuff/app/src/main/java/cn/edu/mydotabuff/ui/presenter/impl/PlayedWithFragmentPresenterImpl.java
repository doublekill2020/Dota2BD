package cn.edu.mydotabuff.ui.presenter.impl;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.base.RxCallBackEvent;
import cn.edu.mydotabuff.common.EventTag;
import cn.edu.mydotabuff.model.Match;
import cn.edu.mydotabuff.model.PlayedWithWrapper;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IPlayedWithFragmentPresenter;
import cn.edu.mydotabuff.ui.service.PlayerInfoService;
import cn.edu.mydotabuff.ui.view.IPlayedWithFragmentView;
import io.realm.RealmResults;

public class PlayedWithFragmentPresenterImpl extends BasePresenterImpl<IPlayedWithFragmentView> implements IPlayedWithFragmentPresenter {

    private RealmResults<PlayerInfo> mPlayerInfos;
    private boolean mHasLoaded = false;
    private List<String> mPlayerIds = new ArrayList<>();
    private RealmResults<Match> matches;
    private Map<String, PlayerInfo> mPlayerInfoMap;

    public PlayedWithFragmentPresenterImpl(IPlayedWithFragmentView view) {
        super(view, true);
        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Override
    public void getData(String accountId) {
        PlayerInfoService.getPlayedWithInfo(accountId);
    }

    @Subscribe
    public void onEvent(RxCallBackEvent<List<PlayedWithWrapper>> event) {
        if (event.tag == EventTag.GET_PLAYED_WITH_INFO) {
            mView.setRefreshCompleted();
            if (event.success && event.data != null) {
                mView.notifyDataUpdate(event.data);
            } else {
                mView.showToast(DotaApplication.getApplication().getString(R.string.no_more_data));
            }
        }
    }
}
