/**
 * @Title: BaseActivity.java
 * @ProjectName MyDotaBuff
 * @Package cn.edu.mydotabuff.base
 * @author 袁浩 1006401052yh@gmail.com
 * @date 2015-1-22 下午6:05:10 
 * @version V1.4
 * Copyright 2013-2015 深圳市点滴互联科技有限公司  版权所有
 */
package cn.edu.mydotabuff.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.edu.mydotabuff.AppManager;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.ui.ActMain;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.SwipeBackActivityBase;
import cn.edu.mydotabuff.view.SwipeBackActivityHelper;
import cn.edu.mydotabuff.view.SwipeBackLayout;
import cn.edu.mydotabuff.view.TipsToast;
import cn.edu.mydotabuff.view.TipsToast.DialogType;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

/**
 * @author 袁浩 1006401052yh@gmail.com
 * @ClassName: BaseActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2015-1-22 下午6:05:10
 */
public abstract class BaseActivity extends ActionBarActivity implements SwipeBackActivityBase {

    // 网络请求状态码
    public static final int OK = 1; // 成功
    public static final int FAILED = 0;// 失败 超时 等
    public static final int JSON_ERROR = -1;// json解析出错
    private SwipeBackActivityHelper mHelper;
    public ActionMenuView mActionMenuView;
    public Toolbar mToolbar;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initActionBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initActionBar();
    }
    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionMenuView = (ActionMenuView) findViewById(R.id.actionMenuView);
        if (mActionMenuView != null) {
            mActionMenuView.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        AppManager.getAppManager().addActivity(this);

        final SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.actionbar_bg);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintResource(R.color.statusbar_bg);

        initViewAndData();
        initEvent();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        cn.edu.mydotabuff.view.Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    protected abstract void initViewAndData();

    protected abstract void initEvent();

    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int resId) {
        return (T) findViewById(resId);
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 显示提示（屏幕中央）
     *
     * @param content 内容
     * @param type    对话框类型
     */
    public void showTip(String content, DialogType type) {
        TipsToast.showToast(this, content, Toast.LENGTH_SHORT, type);
    }

    /**
     * 显示Toast
     *
     * @param content 内容
     */
    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
    public ActionMenuView getActionMenuView() {
        return mActionMenuView;
    }
}
