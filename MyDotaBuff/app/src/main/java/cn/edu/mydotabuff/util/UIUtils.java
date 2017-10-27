package cn.edu.mydotabuff.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by sadhu on 2017/7/6.
 * 描述:
 */
public class UIUtils {
    public static int dp2px(Context c, float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int getScreenWidht(Context c ) {
        return c.getResources().getDisplayMetrics().widthPixels;
    }
}
