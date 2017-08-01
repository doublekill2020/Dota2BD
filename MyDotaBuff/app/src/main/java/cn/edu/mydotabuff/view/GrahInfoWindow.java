package cn.edu.mydotabuff.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.util.UIUtils;

/**
 * Created by sadhu on 2017/8/1.
 * 描述 曲线图详情展示的popupWindow
 */
public class GrahInfoWindow extends PopupWindow {
    public GrahInfoWindow(Context context) {
        setWidth(UIUtils.dp2px(context, 100));
        setHeight(UIUtils.dp2px(context, 100));
        View view = LayoutInflater.from(context).inflate(R.layout.pop_grah_info, null);
        setContentView(view);
    }
}
