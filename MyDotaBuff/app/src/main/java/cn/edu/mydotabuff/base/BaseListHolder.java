package cn.edu.mydotabuff.base;

import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hwangjr.rxbus.RxBus;

/**
 * //                              _oo0oo_
 * //                             o8888888o
 * //                             88" . "88
 * //                             (| -_- |)
 * //                             0\  =  /0
 * //                           ___/`___'\___
 * //                         .' \\|     |// '.
 * //                        / \\|||  :  |||// \
 * //                       / _||||| -:- |||||_ \
 * //                      |   | \\\  _  /// |   |
 * //                      | \_|  ''\___/''  |_/ |
 * //                      \  .-\__  '_'  __/-.  /
 * //                    ___'. .'  /--.--\  '. .'___
 * //                  ."" '<  .___\_<|>_/___. '>' "".
 * //               | | :  `_ \`.;` \ _ / `;.`/ - ` : | |
 * //               \ \  `_.   \_ ___\ /___ _/   ._`  / /
 * //            ====`-.____` .__ \_______/ __. -` ___.`====
 * //                             `=-----='
 * //         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * //                    佛祖保佑           永无BUG
 * //
 * //         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class BaseListHolder extends RecyclerView.ViewHolder {

    private View itemView;

    public BaseListHolder(View itemView, final int clickTag, final int longClickTag) {
        super(itemView);
        this.itemView = itemView;
        if (clickTag != -1) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseListClickEvent event = new BaseListClickEvent();
                    event.position = getAdapterPosition();
                    event.view = v;
                    event.tag = clickTag;
                    RxBus.get().post(event);
                }
            });
        }
        if (longClickTag != -1) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    BaseListClickEvent event = new BaseListClickEvent();
                    event.position = getAdapterPosition();
                    event.view = v;
                    event.tag = longClickTag;
                    RxBus.get().post(event);
                    return true;
                }
            });
        }
    }

    public <T extends View> T findViewById(int viewId) {
        return (T) itemView.findViewById(viewId);
    }

    public void setText(int viewId, CharSequence text) {
        TextView view = findViewById(viewId);
        view.setText(text);
    }

    public void setTextColor(int viewId, int color) {
        TextView view = findViewById(viewId);
        view.setTextColor(color);
    }

    public void setTextColor(int viewId, ColorStateList color) {
        TextView view = findViewById(viewId);
        view.setTextColor(color);
    }

    public void setTextSize(int viewId, int typeValue, int size) {
        TextView view = findViewById(viewId);
        view.setTextSize(typeValue, size);
    }

    public void setImageResource(int viewId, int resId) {
        ImageView view = findViewById(viewId);
        view.setImageResource(resId);
    }

    public void setBackgroundColor(int viewId, int color) {
        View view = findViewById(viewId);
        view.setBackgroundColor(color);
    }

    public void setBackgroundResource(int viewId, int resid) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resid);
    }

    public void setVisibility(int viewId, int status) {
        View view = findViewById(viewId);
        view.setVisibility(status);
    }

    public void setImageURI(int viewId, String url) {
        SimpleDraweeView view = findViewById(viewId);
        view.setImageURI(Uri.parse(url));
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
    }

    /**
     * 设置进度条进度
     *
     * @param viewId
     * @param rate
     */
    public void setProgress(int viewId, int rate) {
        ProgressBar pb = findViewById(viewId);
        pb.setProgress(rate);
    }

    public void setViewVisibility(int viewId, int visibility) {
        View v = findViewById(viewId);
        v.setVisibility(visibility);
    }

    public void setTag(int viewId, Object bean) {
        View v = findViewById(viewId);
        v.setTag(bean);
    }

    public void setChecked(int viewId, boolean checked) {
        CheckBox c = findViewById(viewId);
        c.setChecked(checked);
    }

    public void setClickable(int viewId, boolean clickable) {
        View v = findViewById(viewId);
        v.setClickable(false);
    }
}
