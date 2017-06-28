package cn.edu.mydotabuff.ui.view.activity;

import android.support.annotation.StringRes;

/**
 * Created by sadhu on 2017/6/28.
 * 描述:
 */
public interface IBaseView {
    void showToast(String content);

    void showToast(@StringRes int stringRes);

    void showLoadingDialog();

    void dismissLoadingDialog();
}
