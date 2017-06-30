package cn.edu.mydotabuff.base;

import android.view.View;

/**
 * Created by nevermore on 2017/6/30 0030.
 */

public class BaseListClickEvent<T> {

    public View view;
    public int position;
    public int tag;
    public T data;
}
