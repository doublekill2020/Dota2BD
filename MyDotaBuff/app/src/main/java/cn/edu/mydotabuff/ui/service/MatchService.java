package cn.edu.mydotabuff.ui.service;

import java.util.List;

import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.common.DotaDefaultConfig;
import cn.edu.mydotabuff.model.Match;
import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by tinker on 2017/7/24.
 */

public class MatchService {

    public static void getMatches(final String accountId, int offset) {
        OpenDotaApi.getService().getMatches(accountId, DotaDefaultConfig.DEFAULT_PAGE_LIMIT, offset)
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
