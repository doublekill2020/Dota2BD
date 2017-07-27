package cn.edu.mydotabuff.base;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;


public interface IBaseView {


    void showSuccessLayout();

    void showEmptyLayout(int drawableResId, int stringResId);

    void showEmptyLayout();

    void showErrorLayout(int errorCode);

    void showErrorLayout();

    void showLoadingLayout();

    void showToast(String msg);

    void showToast(@StringRes int stringRes);

    void showLoadingDialog();

    void dismissLoadingDialog();

    <C extends AppCompatActivity> void toOtherActivity(Class<C> c);

    void toOtherActivity(Intent intent);

    void toOtherActivityForResult(Intent intent, int requestCode);

    Context getContext();


}
