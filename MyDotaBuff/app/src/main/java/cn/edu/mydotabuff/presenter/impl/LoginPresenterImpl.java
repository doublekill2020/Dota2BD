package cn.edu.mydotabuff.presenter.impl;

import android.util.Log;

import java.util.List;

import cn.edu.mydotabuff.base.OpenDotaApi;
import cn.edu.mydotabuff.model.SearchPlayerResult;
import cn.edu.mydotabuff.presenter.ILoginPresenter;
import cn.edu.mydotabuff.tmpUI.ILoginView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sadhu on 2017/6/28.
 * 描述:
 */
public class LoginPresenterImpl implements ILoginPresenter {
    private static final String TAG = "LoginPresenterImpl";
    private ILoginView mView;

    public LoginPresenterImpl(ILoginView view) {
        this.mView = view;
    }


    @Override
    public void searchPlayer(String key, boolean isExactSearch) {
        Log.i(TAG, "searchPlayer: " + key + "isExactSearch:" + isExactSearch);
        mView.showLoadingDialog();
        OpenDotaApi.getInstance().getService().searchAccountId(key, isExactSearch ? 1f : 0.6f)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchPlayerResult>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void onNext(List<SearchPlayerResult> searchPlayerResults) {
                        mView.dismissLoadingDialog();
                        mView.showResult(searchPlayerResults);
                    }
                });
    }

    @Override
    public void bindPlayer(long accountId) {

    }

    @Override
    public void onDestory() {

    }
}
