package cn.edu.mydotabuff.ui.service;

import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.model.PlayerInfo;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by tinker on 2017/7/3.
 */

public class PlayerInfoService {

    public static void getPlayerInfo(final String accountId) {
        OpenDotaApi.getService().getPlayerInfo(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<PlayerInfo, Boolean>() {
                    @Override
                    public Boolean call(PlayerInfo playerInfo) {
                        Realm realm = Realm.getDefaultInstance();
                        try {
                            realm.beginTransaction();
                            playerInfo.account_id = playerInfo.profile.account_id;
                            realm.copyToRealmOrUpdate(playerInfo);
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

    public static PlayerInfo queryPlayerInfo(Realm realm, String accountId) {
        return realm.where(PlayerInfo.class).equalTo("account_id", accountId).findFirst();
    }

    public static RealmResults<PlayerInfo> queryPlayerInfoResults(Realm realm, String accountId) {
        return realm.where(PlayerInfo.class).equalTo("account_id", accountId).findAllAsync();
    }
}
