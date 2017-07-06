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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.view.LoadingDialog;
import cn.edu.mydotabuff.view.TipsToast;
import cn.edu.mydotabuff.view.TipsToast.DialogType;

/**
 * @date 2015-1-22 下午6:05:10
 */
public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements
        BGASwipeBackHelper
                .Delegate, IBaseView {

    // 网络请求状态码
    public static final int OK = 1; // 成功
    public static final int FAILED = 0;// 失败 超时 等
    public static final int JSON_ERROR = -1;// json解析出错
    public ActionMenuView mActionMenuView;
    public Toolbar mToolbar;
    protected BGASwipeBackHelper mSwipeBackHelper;
    protected T mPresenter;
    private LoadingDialog dialog;

    protected ViewGroup mContainerView;
    protected View mEmptyView;
    protected View mSuccessView;
    private boolean mHasShowSuccessView = false;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initActionBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
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
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
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

    public ActionMenuView getActionMenuView() {
        return mActionMenuView;
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int stringResId) {
        Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show();
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


    @Override
    public <C extends AppCompatActivity> void toOtherActivity(Class<C> c) {
        startActivity(new Intent(this, c));
    }

    @Override
    public void toOtherActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void toOtherActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showSuccessLayout() {
        showSuccessView();
    }

    @Override
    public void showEmptyLayout(int drawableResId, int stringResId) {
        showEmptyView(drawableResId, stringResId);
    }

    @Override
    public void showEmptyLayout() {
        showEmptyView();
    }

    @Override
    public void showErrorLayout(int errorCode) {

    }

    @Override
    public void showErrorLayout() {

    }


    protected void setSuccessView(View v) {
        mSuccessView = v;
        mContainerView = (ViewGroup) v.getParent();
    }

    private void showSuccessView() {
        if (mContainerView != null && !mHasShowSuccessView) {
            mContainerView.removeAllViews();
            if (mSuccessView != null) {
                mContainerView.addView(mSuccessView);
                mHasShowSuccessView = true;
            } else {
                throw new RuntimeException("please invoke setSuccessView!");
            }
        }
    }

    private void showEmptyView(int drawableResId, int stringResId) {
        if (mContainerView != null) {
            mContainerView.removeAllViews();
            if (mEmptyView == null) {
                mEmptyView = getEmptyView(drawableResId, stringResId);
            }
            mContainerView.addView(mEmptyView);
            mHasShowSuccessView = false;
        } else {
            throw new RuntimeException("please invoke setSuccessView!");
        }
    }

    private void showEmptyView() {
        showEmptyView(0, 0);
    }

    protected View getEmptyView(@DrawableRes int resourceId, @StringRes int stringResId) {
        mEmptyView = LayoutInflater.from(BaseActivity.this).inflate(R.layout.layout_empty,
                mContainerView, false);
        ImageView imageView = (ImageView) mEmptyView.findViewById(R.id.iv_empty);
        TextView textView = (TextView) mEmptyView.findViewById(R.id.blank_text);
        swipeRefreshLayout = (SwipeRefreshLayout) mEmptyView.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onEmptyViewRefresh();
            }
        });
        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        }
        if (stringResId != 0) {
            textView.setText(getString(stringResId));
        }
        return mEmptyView;
    }

    //如子类activity需要处理空页面状态下的刷新，则重写改方法实现刷新逻辑
    protected void onEmptyViewRefresh() {
        refreshComplete();
    }

    public void refreshComplete() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
