package cn.edu.mydotabuff.ui.service;

import com.hwangjr.rxbus.RxBus;

import java.util.List;

import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.base.RxCallBackEvent;
import cn.edu.mydotabuff.common.EventTag;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.model.PlayerWL;
import cn.edu.mydotabuff.model.Rating;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by tinker on 2017/7/3.
 */

public class PlayerInfoService {

    public static void getPlayerRating(final String accountId) {
        final RxCallBackEvent event = new RxCallBackEvent();
        event.tag = EventTag.GET_PLAYER_RATING;
        OpenDotaApi.getService().getPlayerRating(accountId)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Func1<List<Rating>, Boolean>() {
                    @Override
                    public Boolean call(List<Rating> ratings) {
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
        OpenDotaApi.getService().getPlayerInfo(accountId)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<PlayerInfo, Observable<PlayerWL>>() {
                    @Override
                    public Observable<PlayerWL> call(PlayerInfo playerInfo) {
                        Realm realm = Realm.getDefaultInstance();
                        try {
                            PlayerInfo old = realm.where(PlayerInfo.class).equalTo("account_id", accountId).findFirst();
                            realm.beginTransaction();
                            playerInfo.account_id = playerInfo.profile.account_id;
                            if (old.follow) {
                                playerInfo.follow = true;
                            }
                            realm.copyToRealmOrUpdate(playerInfo);
                            realm.commitTransaction();
                        } catch (Exception e) {
                            return null;
                        } finally {
                            realm.close();
                        }
                        return OpenDotaApi.getService().getPlayerWL(accountId);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Func1<PlayerWL, Boolean>() {
                    @Override
                    public Boolean call(PlayerWL playerWL) {
                        Realm realm = Realm.getDefaultInstance();
                        try {
                            realm.beginTransaction();
                            playerWL.accountId = accountId;
                            playerWL.winRate = (playerWL.win * 1.0f / (playerWL.win + playerWL.lose)) * 100;
                            realm.copyToRealmOrUpdate(playerWL);
                            realm.commitTransaction();

                            PlayerInfo playerInfo = realm.where(PlayerInfo.class).equalTo("account_id", accountId).findFirst();
                            PlayerWL playerWLInDb = realm.where(PlayerWL.class).equalTo("accountId",accountId).findFirst();
                            realm.beginTransaction();
                            playerInfo.playerWL = playerWLInDb;
                            realm.copyToRealmOrUpdate(playerInfo);
                            realm.commitTransaction();
                            return true;
                        } catch (Exception e) {
                            return false;
                        } finally {
                            realm.close();
                        }
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
