package cn.edu.mydotabuff.ui.view.activity;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sadhu on 2017/6/28.
 * 描述:
 */
public interface IBaseView {
    void showToast(String content);

    void showToast(@StringRes int stringRes);

    void showLoadingDialog();

    void dismissLoadingDialog();

    <C extends AppCompatActivity> void toActivity(Class<C> c);

    Context getContext();
}
