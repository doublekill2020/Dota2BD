package cn.edu.mydotabuff.util;

import android.os.Looper;

/**
 * Created by nevermore on 2017/7/3 0003.
 */

public class ThreadUtils {
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
