package cn.edu.mydotabuff.base;

import android.content.Intent;


public interface IBaseView {

    void toOtherActivity(Intent intent);

    void showSuccessLayout();

    void showEmptyLayout(int drawableResId, int stringResId);

    void showEmptyLayout();

    void showErrorLayout(int errorCode);

    void showErrorLayout();

    void showToast(String msg);

    void showToast(int stringResId);

    void toOtherActivityForResult(Intent intent, int requestCode);

}
