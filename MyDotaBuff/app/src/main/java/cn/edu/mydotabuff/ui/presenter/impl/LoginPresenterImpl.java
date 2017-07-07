package cn.edu.mydotabuff.ui.presenter.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.model.Profile;
import cn.edu.mydotabuff.model.SearchPlayerResult;
import cn.edu.mydotabuff.ui.presenter.ILoginPresenter;
import cn.edu.mydotabuff.ui.view.activity.ILoginView;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sadhu on 2017/6/28.
 * 描述:
 */
public class LoginPresenterImpl extends BasePresenterImpl<ILoginView> implements ILoginPresenter {
    private static final String TAG = "LoginPresenterImpl";

    private List<PlayerInfo> mPlayerInfos = new ArrayList<>();

    public LoginPresenterImpl(ILoginView view) {
        super(view, true);
    }

    @Override
    public boolean hasFocusPlayer() {
        RealmResults<PlayerInfo> players = mRealm.where(PlayerInfo.class).findAll();
        return players != null && players.size() > 0;
    }

    @Override
    public void searchPlayer(String key, boolean isExactSearch) {
        Log.i(TAG, "searchPlayer: " + key + "isExactSearch:" + isExactSearch);
        mView.showLoadingDialog();
        // 搜索
        if (!isExactSearch) {
            OpenDotaApi.getService().searchAccountId(key, 0.4f)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(new Func1<List<SearchPlayerResult>, List<PlayerInfo>>() {
                        @Override
                        public List<PlayerInfo> call(List<SearchPlayerResult> searchPlayerResults) {
                            com.orhanobut.logger.Logger.d(Thread.currentThread());
                            List<PlayerInfo> mList = new ArrayList<>();
                            if (searchPlayerResults != null) {
                                for (SearchPlayerResult result : searchPlayerResults) {
                                    PlayerInfo info = new PlayerInfo();
                                    info.account_id = String.valueOf(result.accountId);
                                    info.profile = new Profile();
                                    info.profile.account_id = info.account_id;
                                    info.profile.avatarfull = result.avatarfull;
                                    info.profile.avatar = result.avatarfull;
                                    info.profile.personaname = result.personaName;
                                    mList.add(info);
                                }
                            }
                            return mList;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<PlayerInfo>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.dismissLoadingDialog();
                        }

                        @Override
                        public void onNext(List<PlayerInfo> playerInfos) {
                            mPlayerInfos.clear();
                            mPlayerInfos.addAll(playerInfos);
                            mView.dismissLoadingDialog();
                            mView.showResult(mPlayerInfos);
                        }
                    });
        } else {
            // 直接获取玩家信息
            OpenDotaApi.getService().getPlayerInfo(key)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<PlayerInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.dismissLoadingDialog();
                        }

                        @Override
                        public void onNext(PlayerInfo info) {
                            mPlayerInfos.clear();
                            if (info != null && info.profile != null) {
                                info.account_id = info.profile.account_id;
                                mPlayerInfos.add(info);
                            }
                            mView.dismissLoadingDialog();
                            mView.showResult(mPlayerInfos);
                        }
                    });
        }
    }

    @Override
    public void bindPlayer(PlayerInfo info) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(info);
        mRealm.commitTransaction();
    }


}
