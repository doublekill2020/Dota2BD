package cn.edu.mydotabuff.base;

import android.support.annotation.CallSuper;

import io.realm.Realm;

/**
 * Created by sadhu on 2017/7/5.
 * 描述:
 */
public class BasePresenterImpl<V extends IBaseView> implements IBasePresenter {
    protected V mView;
    protected Realm mRealm;

    public BasePresenterImpl(V view) {
        this(view, false);
    }

    public BasePresenterImpl(V view, boolean initRealm) {
        mView = view;
        if (initRealm) {
            initRealm();
        }
    }

    @Override
    public Realm getRealm() {
        return mRealm;
    }

    @Override
    public final void initRealm() {
        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }
    }

    @CallSuper
    @Override
    public void onDestroy() {
        mView = null;
        if (mRealm != null) {
            mRealm.removeAllChangeListeners();
            mRealm.close();
        }
    }
}
