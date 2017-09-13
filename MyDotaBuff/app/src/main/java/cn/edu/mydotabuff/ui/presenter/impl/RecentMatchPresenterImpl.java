package cn.edu.mydotabuff.ui.presenter.impl;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseListClickEvent;
import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.base.RxCallBackEvent;
import cn.edu.mydotabuff.common.EventTag;
import cn.edu.mydotabuff.model.Match;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.presenter.IFollowFragmentPresenter;
import cn.edu.mydotabuff.ui.presenter.IRecentMatchPresenter;
import cn.edu.mydotabuff.ui.service.PlayerInfoService;
import cn.edu.mydotabuff.ui.view.IFollowFragmentView;
import cn.edu.mydotabuff.ui.view.IRecentMatchView;
import cn.edu.mydotabuff.ui.view.activity.impl.MatchDetailActivity;
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

public class RecentMatchPresenterImpl extends BasePresenterImpl<IRecentMatchView> implements IRecentMatchPresenter {

    private RealmResults<PlayerInfo> mPlayerInfos;
    private boolean mHasLoaded = false;
    private List<String> mPlayerIds = new ArrayList<>();
    private RealmResults<Match> matches;
    private Map<String, PlayerInfo> mPlayerInfoMap;

    public RecentMatchPresenterImpl(IRecentMatchView view) {
        super(view, true);
        mPlayerInfos = mRealm.where(PlayerInfo.class).equalTo("follow", true).findAllAsync();
        mPlayerInfos.addChangeListener(new RealmChangeListener<RealmResults<PlayerInfo>>() {
            @Override
            public void onChange(RealmResults<PlayerInfo> playerInfos) {
                if (playerInfos.size() > 0 && !mHasLoaded) {
                    generatePlayerInfoMap();
                    mPlayerIds.clear();
                    for (PlayerInfo playerInfo : playerInfos) {
                        mPlayerIds.add(playerInfo.profile.account_id);
                        syncPlayerData(playerInfo.profile.account_id);
                    }
                    getDataFromDb(mPlayerIds);
                    doSync(mPlayerIds);
                    mHasLoaded = true;
                }
            }
        });
        RxBus.get().register(this);
    }

    @Override
    public Map<String, PlayerInfo> getPlayerInfoMap() {
        return mPlayerInfoMap;
    }

    @Override
    public void doSyncPlayersRating(List<String> playersAccountIds) {
        for (String accountId : playersAccountIds) {
            PlayerInfoService.getPlayerRating(accountId);
        }
    }

    private void generatePlayerInfoMap() {
        mPlayerInfoMap = new HashMap<>();
        for (PlayerInfo playerInfo : mRealm.copyFromRealm(mPlayerInfos)) {
            mPlayerInfoMap.put(playerInfo.account_id, playerInfo);
        }
    }

    @Subscribe
    public void onEvent(RxCallBackEvent event) {
        if (event.tag == EventTag.GET_PLAYER_RATING) {
            if (event.success) {
                mView.notifyDataUpdate();
            }
        }
    }

    @Override
    public List<String> getAllFollowers() {
        if (!mHasLoaded) {
            mView.showToast(R.string.data_in_loading);
        }
        return mPlayerIds;
    }

    private void syncPlayerData(String accountId) {
        PlayerInfoService.getPlayerInfo(accountId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Subscribe
    public void onItemClicked(BaseListClickEvent event) {
        if (event.tag == EventTag.PLAYER_DETAIL_CLICK_TO_MATCH_DETAIL) {
            MatchDetailActivity.start(mView.getContext(), matches.get(event.position).match_id);
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
                if (mView == null) {
                    return;
                }
                if (matches.size() > 0) {
                    mView.showSuccessLayout();
                    mView.setDataToRecycleView(matches);
                }
            }
        });
    }

    @Override
    public void doSync(final List<String> followers) {
        doSyncPlayersRating(mPlayerIds);
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
                        }
                    });
        }
    }
}
