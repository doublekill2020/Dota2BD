package cn.edu.mydotabuff.ui.service;

import android.util.Log;

import com.hwangjr.rxbus.RxBus;

import java.math.BigDecimal;
import java.util.List;

import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.base.RxCallBackEvent;
import cn.edu.mydotabuff.common.EventTag;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.model.PlayerWL;
import cn.edu.mydotabuff.model.Rating;
import cn.edu.mydotabuff.util.ThreadUtils;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by tinker on 2017/7/3.
 */

public class PlayerInfoService {

    public static void getPlayerRating(final String accountId) {
        final RxCallBackEvent event = new RxCallBackEvent();
        event.tag = EventTag.GET_PLAYER_RATING;
        OpenDotaApi.getService().getPlayerRating(accountId)
                .map(new Func1<List<Rating>, Boolean>() {
                    @Override
                    public Boolean call(List<Rating> ratings) {
                        Log.i("getPlayerRating", ThreadUtils.isMainThread()+"");
                        Realm realm = Realm.getDefaultInstance();
                        try {
                            realm.beginTransaction();
                            for (Rating rating : ratings) {
                                rating.id = rating.account_id + rating.match_id;
                            }
                            realm.copyToRealmOrUpdate(ratings);
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
                        event.success = aBoolean;
                        event.data = accountId;
                        RxBus.get().post(event);
                    }
                });
    }


    public static void getPlayerInfo(final String accountId) {

        Observable<PlayerWL> playerWLObservable = OpenDotaApi.getService().getPlayerWL(accountId);
        Observable<PlayerInfo> playerInfoObservable = OpenDotaApi.getService().getPlayerInfo(accountId);
        playerWLObservable.zipWith(playerInfoObservable, new Func2<PlayerWL, PlayerInfo, Boolean>() {
            @Override
            public Boolean call(PlayerWL playerWL, PlayerInfo playerInfo) {
                Realm realm = Realm.getDefaultInstance();
                try {
                    PlayerInfo playerInfoInDb = realm.where(PlayerInfo.class).equalTo
                            ("account_id", accountId).findFirst();
                    realm.beginTransaction();
                    playerWL.accountId = accountId;
                    playerWL.winRate = (playerWL.win * 1.0f / (playerWL.win + playerWL
                            .lose)) * 100;
                    BigDecimal bd = new BigDecimal(playerWL.winRate);
                    playerWL.winRate = bd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                    PlayerWL playerWLManager = realm.copyToRealmOrUpdate(playerWL);

                    playerInfo.account_id = accountId;
                    if (playerInfoInDb != null) {
                        playerInfo.follow = playerInfoInDb.follow;
                    }
                    playerInfo.playerWL = playerWLManager;
                    realm.copyToRealmOrUpdate(playerInfo);
                    realm.commitTransaction();
                    return true;
                } catch (Exception e) {
                    return false;
                } finally {
                    realm.close();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                    }
                });
    }

    public static PlayerInfo queryPlayerInfo(Realm realm, String accountId) {
        return realm.where(PlayerInfo.class).equalTo("account_id", accountId).findFirst();
    }

    public static RealmResults<PlayerInfo> queryPlayerInfoResults(Realm realm, String accountId) {
        return realm.where(PlayerInfo.class).equalTo("account_id", accountId).findAllAsync();
    }

    public static PlayerInfo querySinglePlayerInfo(Realm realm, String accountId) {
        return realm.where(PlayerInfo.class).equalTo("account_id", accountId).findFirst();
    }
}
