package cn.edu.mydotabuff.base;


import io.realm.Realm;

public interface IBasePresenter {

    Realm getRealm();

    void initRealm();

    void onDestroy();
}
