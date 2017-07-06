package cn.edu.mydotabuff.base;

/**
 * Created by nevermore on 2017/7/6 0006.
 */

public class RxCallBackEvent<T> {
    public T data;
    public Throwable throwable;
    public boolean success;
    public int tag;
}
