package cn.edu.mydotabuff.ui.presenter.impl;

import android.os.SystemClock;

import com.orhanobut.logger.Logger;

import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.ui.presenter.IMatchDetaiPresenter;
import cn.edu.mydotabuff.ui.view.activity.IMatchDetailView;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sadhu on 2017/7/5.
 * 描述: 比赛详情presenter
 */
public class MatchDetailPresenterImpl extends BasePresenterImpl<IMatchDetailView> implements IMatchDetaiPresenter {
    public MatchDetailPresenterImpl(IMatchDetailView view) {
        super(view, true);
    }

    private long lastTime;
    private MatchDetail mMatchDetail;

    @Override
    public void fetchMatchDetailInfo(final String matchId) {
        mView.showLoadingDialog();
        lastTime = SystemClock.elapsedRealtime();
        RealmResults<MatchDetail> matchDetailRealmResults = mRealm
                .where(MatchDetail.class)
                .equalTo("match_id", matchId)
                .findAllAsync();
        matchDetailRealmResults.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<MatchDetail>>() {
            @Override
            public void onChange(RealmResults<MatchDetail> matchDetails, OrderedCollectionChangeSet changeSet) {
                if (changeSet == null) {
                    // The first time async returns with an null changeSet.
                    if (matchDetails.size() == 0) {
                        getMatchDetailFromNet(matchId);
                    } else {
                        mView.dismissLoadingDialog();
                        mMatchDetail = matchDetails.get(0);
                        Logger.d("db" + (SystemClock.elapsedRealtime() - lastTime));
                    }
                } else {
                    // Called on every update.
                }
            }
        });

    }

    @Override
    public MatchDetail getMatchDetail() {
        return mMatchDetail;
    }

    private void getMatchDetailFromNet(String matchId) {

        OpenDotaApi.getService().getMatchDetail(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MatchDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void onNext(MatchDetail detail) {
                        mView.dismissLoadingDialog();
                        save2DB(detail);
                        Logger.d("net:" + (SystemClock.elapsedRealtime() - lastTime));
                        mMatchDetail = detail;
                    }
                });
    }

    private void save2DB(final MatchDetail detail) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(detail);
            }
        });
    }
}

