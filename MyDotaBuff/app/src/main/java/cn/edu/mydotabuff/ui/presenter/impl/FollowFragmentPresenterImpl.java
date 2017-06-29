package cn.edu.mydotabuff.ui.presenter.impl;

import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IFollowFragmentPresenter;
import cn.edu.mydotabuff.ui.view.IFollowFragmentView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public class FollowFragmentPresenterImpl implements IFollowFragmentPresenter {

    private IFollowFragmentView mView;
    private RealmResults<PlayerInfo> mPlayerInfos;
    private Realm mRealm;

    public FollowFragmentPresenterImpl(IFollowFragmentView view) {
        mView = view;
        mRealm = ((BaseFragment) mView).getRealm();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getDataFromDb() {
        mPlayerInfos = mRealm.where(PlayerInfo.class).equalTo("follow", true).findAllAsync();
        mPlayerInfos.addChangeListener(new RealmChangeListener<RealmResults<PlayerInfo>>() {
            @Override
            public void onChange(RealmResults<PlayerInfo> playerInfos) {

            }
        });
    }

    @Override
    public void doSync() {

    }
}
