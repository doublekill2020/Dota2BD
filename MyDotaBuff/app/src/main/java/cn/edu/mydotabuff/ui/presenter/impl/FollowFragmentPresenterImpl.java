package cn.edu.mydotabuff.ui.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.model.Match;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IFollowFragmentPresenter;
import cn.edu.mydotabuff.ui.view.IFollowFragmentView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
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
                        mFolloers.add(playerInfo.account_id);
                    }
                    getDataFromDb(mFolloers);
                    doSync(mFolloers);

                    mHasLoaded = true;
                }
            }
        });
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getDataFromDb(final List<String> followers) {
        String[] params = followers.toArray(new String[0]);
        RealmResults<Match> matches = mRealm.where(Match.class).in("account_id", params).findAllAsync();
        matches.addChangeListener(new RealmChangeListener<RealmResults<Match>>() {
            @Override
            public void onChange(RealmResults<Match> matches) {
                if (matches.size() > 0) {

                }
            }
        });
    }

    @Override
    public void doSync(final List<String> followers) {
        for (final String accountId : followers) {
            OpenDotaApi.getService().getRecentMatch(accountId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<List<Match>, Boolean>() {
                        @Override
                        public Boolean call(List<Match> matches) {
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
                                return false;
                            } finally {
                                realm.close();
                            }
                            return true;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                        }
                    });
        }
    }
}
