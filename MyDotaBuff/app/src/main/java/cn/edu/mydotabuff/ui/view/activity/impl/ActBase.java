package cn.edu.mydotabuff.ui.view.activity.impl;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import cn.edu.mydotabuff.ui.view.activity.IBaseView;
import cn.edu.mydotabuff.view.LoadingDialog;

/**
 * Created by sadhu on 2017/6/28.
 * 描述:
 */
public class ActBase extends AppCompatActivity implements IBaseView {
    private LoadingDialog dialog;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int stringRes) {
        Toast.makeText(this, getString(stringRes), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog() {
        if (dialog == null)
            dialog = new LoadingDialog(this);
        if (!dialog.isShowing())
            dialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
