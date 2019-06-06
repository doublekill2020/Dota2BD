package cn.edu.mydotabuff.base;

import android.os.Bundle;
import android.widget.Toast;

/**
 * 作者：sadhu on 2015/10/21 15:49
 * 邮箱：static.sadhu@gmail.com
 * 描述: 通过setUserVisibleHint方法控制的懒加载(viewpager中)
 */
public class PageFragment<T extends IBasePresenter> extends BaseFragment<T> {


    private static final String TAG = PageFragment.class.getSimpleName();
    protected boolean isPrepared;
    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    protected boolean isVisibleToUser;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    public synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public void onFirstUserVisible() {
    }

    /**
     * fragment可见（切换回来或者onResume）
     */
    public void onUserVisible() {
    }

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstUserInvisible() {
    }

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public void onUserInvisible() {
    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            showToast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }


}
