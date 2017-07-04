package cn.edu.mydotabuff.ui.presenter.impl;

import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.base.BaseListClickEvent;
import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.common.ClickTag;
import cn.edu.mydotabuff.model.Match;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IFollowFragmentPresenter;
import cn.edu.mydotabuff.ui.service.PlayerInfoService;
import cn.edu.mydotabuff.ui.view.IFollowFragmentView;
import cn.edu.mydotabuff.util.ThreadUtils;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public class FollowFragmentPresenterImpl implements IFollowFragmentPresenter {

    private IFollowFragmentView mView;
    private RealmResults<PlayerInfo> mPlayerInfos;
    private Realm mRealm;
    private boolean mHasLoaded = false;
    private List<String> mFolloers = new ArrayList<>();
    private RealmResults<Match> matches;

    public FollowFragmentPresenterImpl(IFollowFragmentView view) {
        mView = view;
        mRealm = ((BaseFragment) mView).getRealm();
        mPlayerInfos = mRealm.where(PlayerInfo.class).equalTo("follow", true).findAllAsync();
        mPlayerInfos.addChangeListener(new RealmChangeListener<RealmResults<PlayerInfo>>() {
            @Override
            public void onChange(RealmResults<PlayerInfo> playerInfos) {
                if (playerInfos.size() > 0 && !mHasLoaded) {
                    mFolloers.clear();
                    for (PlayerInfo playerInfo : playerInfos) {
                        mFolloers.add(playerInfo.profile.account_id);
                        syncPlayerData(playerInfo.profile.account_id);
                    }
                    getDataFromDb(mFolloers);
                    doSync(mFolloers);

                    mHasLoaded = true;
                }
            }
        });
        RxBus.get().register(this);
    }

    @Override
    public List<String> getAllFollowers() {
        if (!mHasLoaded) {
            mView.showToast(R.string.data_in_loading);
        }
        return mFolloers;
    }

    private void syncPlayerData(String accountId) {
        PlayerInfoService.getPlayerInfo(accountId);
    }

    @Override
    public void onDestroy() {
        RxBus.get().unregister(this);
        mView = null;
    }
    @Subscribe
    public void onItemClicked(BaseListClickEvent event){
        if(event.tag == ClickTag.CLICK_TO_DETAIL){

        }
    }

    @Override
    public void getDataFromDb(final List<String> followers) {
        String[] params = followers.toArray(new String[0]);
        matches = mRealm.where(Match.class).in("account_id", params)
                .findAllAsync().sort("start_time", Sort.DESCENDING);
        matches.addChangeListener(new RealmChangeListener<RealmResults<Match>>() {
            @Override
            public void onChange(RealmResults<Match> matches) {
                if (matches.size() > 0) {
                    mView.showSuccessLayout();
                    mView.setDataToRecycleView(matches);
                }
            }
        });
    }

    @Override
    public void doSync(final List<String> followers) {
        for (final String accountId : followers) {
            OpenDotaApi.getService().getRecentMatch(accountId)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(Schedulers.io())
                    .map(new Func1<List<Match>, List<Match>>() {
                        @Override
                        public List<Match> call(List<Match> matches) {
                            Realm realm = Realm.getDefaultInstance();
                            try {
                                realm.beginTransaction();
                                for (Match match : matches) {
                                    match.account_id = accountId;
                                    match.id = accountId + match.match_id;
                                }
                                realm.copyToRealmOrUpdate(matches);
                                realm.commitTransaction();
                            } catch (Exception e) {
                                return null;
                            } finally {
                                realm.close();
                            }
                            return matches;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Match>>() {
                        @Override
                        public void call(List<Match> matches) {
                            if(mView != null){
                                mView.setRefreshCompleted();
                            }
                        }
                    });
        }
    }
}
