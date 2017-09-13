/**
 * @Title: BaseFragment.java
 * @ProjectName MyDotaBuff
 * @Package cn.edu.mydotabuff.base
 * @author 袁浩 1006401052yh@gmail.com
 * @date 2015-2-2 下午12:03:38
 * @version V1.4
 * Copyright 2013-2015 深圳市点滴互联科技有限公司  版权所有
 */
package cn.edu.mydotabuff.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.view.LoadingDialog;

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {

    private View view;
    protected ViewGroup mContainerView;
    protected View mEmptyView;
    protected View mSuccessView;
    protected View loadingView;
    protected Activity mActivity;
    protected LayoutInflater mInflater;
    private boolean mHasShowSuccessView = false;
    protected T mPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadingDialog dialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private final void showSuccessView() {
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

    protected void setSuccessView(View v) {
        mSuccessView = v;
        mContainerView = (ViewGroup) mSuccessView.getParent();
    }

    //如子类fragment需要处理空页面状态下的刷新，则重写改方法实现刷新逻辑
    protected void onEmptyViewRefresh() {
        refreshComplete();
    }

    protected View getEmptyView(int resourceId, int stringResId) {
        mEmptyView = LayoutInflater.from(mActivity).inflate(R.layout.layout_empty,
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
        if (isAdded() && stringResId != 0) {
            textView.setText(getString(stringResId));
        }
        return mEmptyView;
    }

    public void refreshComplete() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private final void showEmptyView(int drawableResId, int stringResId) {
        if (mContainerView != null) {
            mContainerView.removeAllViews();
            if (mEmptyView == null) {
                getEmptyView(drawableResId, stringResId);
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


    @Override
    public void showLoadingDialog() {
        if (dialog == null)
            dialog = new LoadingDialog(getActivity());
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
        startActivity(new Intent(getContext(), c));
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
    public void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int stringResId) {
        Toast.makeText(mActivity, stringResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorLayout(int errorCode) {
    }

    @Override
    public void showErrorLayout() {

    }

    @Override
    public void showLoadingLayout() {
        showLoadingView();
    }

    protected void showLoadingView() {
        if (mContainerView != null) {
            mContainerView.removeAllViews();
            if (loadingView == null) {
                loadingView = LayoutInflater.from(mActivity).inflate(R.layout.layout_loading,
                        mContainerView, false);
            }
            mContainerView.addView(loadingView);
            mHasShowSuccessView = false;
        } else {
            throw new RuntimeException("please invoke setSuccessView!");
        }
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
