package cn.edu.mydotabuff.view;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseListAdapter;
import cn.edu.mydotabuff.base.BaseListHolder;
import cn.edu.mydotabuff.util.UIUtils;
import cn.edu.mydotabuff.view.linechar.LineCharView;

/**
 * Created by sadhu on 2017/8/1.
 * 描述 曲线图详情展示的popupWindow
 */
public class GrahInfoWindow extends PopupWindow {
    private LineCharView.HitInfo mHitInfo;
    private TextView mTvTitle;
    private RecyclerView mRvList;
    private Context ctx;
    private int mBaseOffset;
    private int mPopupWindowWidth;
    private int mScreenWidth;
    private final BaseListAdapter<LineCharView.GrahItemInfo> mAdapter;

    private int handMode;

    public GrahInfoWindow(Context context) {
        this.ctx = context;
        this.mHitInfo = new LineCharView.HitInfo();
        this.mHitInfo.datas = new ArrayList<>();
        mBaseOffset = UIUtils.dp2px(context, 40);
        mPopupWindowWidth = UIUtils.dp2px(context, 110);
        mScreenWidth = UIUtils.getScreenWidht(context);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_solid_33_trans_corners_4));
        View view = LayoutInflater.from(context).inflate(R.layout.pop_grah_info, null);
        mTvTitle = view.findViewById(R.id.tv_title);
        mRvList = view.findViewById(R.id.rv_list);
        mAdapter = new BaseListAdapter<LineCharView.GrahItemInfo>(mHitInfo.datas, R.layout.item_pop_grah_info) {
            @Override
            public void getView(BaseListHolder holder, LineCharView.GrahItemInfo bean, int pos) {
                holder.setText(R.id.tv_name, bean.name);
                holder.setText(R.id.tv_value, bean.value);
                holder.setBackgroundColor(R.id.v_color, bean.color);
            }
        };
        mRvList.setAdapter(mAdapter);
        setContentView(view);
    }

    public void update() {
        mTvTitle.setText(String.format(Locale.CHINA, "%d:00", mHitInfo.time));
        mAdapter.notifyDataSetChanged();
        float offsetX = mHitInfo.hitX - mPopupWindowWidth - mBaseOffset;
//        if (mHitInfo.hitX + mBaseOffset + mPopupWindowWidth > mScreenWidth) {
//            offsetX = mHitInfo.hitX + mBaseOffset;
//        }
        if (isShowing()) {
            update((int) offsetX,
                    (int) mHitInfo.touchY,
                    -1,
                    -1);
        } else {
            showAtLocation(mTvTitle, Gravity.LEFT | Gravity.TOP, (int) offsetX, (int) mHitInfo.touchY);
        }
    }

    public LineCharView.HitInfo getData() {
        return mHitInfo;
    }

    public void setData(LineCharView.HitInfo info) {
        mHitInfo.datas.clear();
        mHitInfo.datas.addAll(info.datas);
        mHitInfo.hitX = info.hitX;
        mHitInfo.touchY = info.touchY;
        mHitInfo.time = info.time;
    }
}
