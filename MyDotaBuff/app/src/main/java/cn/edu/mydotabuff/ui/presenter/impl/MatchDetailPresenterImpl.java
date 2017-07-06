package cn.edu.mydotabuff.ui.presenter.impl;

import android.os.SystemClock;

import com.orhanobut.logger.Logger;

import cn.edu.mydotabuff.base.BasePresenterImpl;
import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.ui.presenter.IMatchDetaiPresenter;
import cn.edu.mydotabuff.ui.view.activity.IMatchDetailView;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sadhu on 2017/7/5.
 * 描述: 比赛详情presenter
 */
public class MatchDetailPresenterImpl extends BasePresenterImpl<IMatchDetailView> implements IMatchDetaiPresenter {
    public MatchDetailPresenterImpl(IMatchDetailView view) {
        super(view);
    }

    private long lastTime;

    @Override
    public void getMatchDetail(String matchId) {
        mView.showLoadingDialog();
        lastTime = SystemClock.elapsedRealtime();
        OpenDotaApi.getService().getMatchDetail(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        mView.dismissLoadingDialog();
                        Logger.d(SystemClock.elapsedRealtime() - lastTime);
                    }
                });
    }
}
