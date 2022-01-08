package cn.edu.mydotabuff.view.linechar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.realm.RealmInt;
import cn.edu.mydotabuff.util.UIUtils;

/**
 * Created by sadhu on 2017/7/14.
 * 描述 折线图
 */
public class LineCharView extends View {
    private List<LineCharView.LineInfo> lineInfoList;

    // 画笔_轴or文字
    private Paint mAxisPaint;
    // 画笔_点or曲线
    private Paint mLinePaint;
    // 横纵起始刻度距离原点的间距
    private float mAxisSpace;
    // 刻度线的高度or宽度
    private float mAxisIndexWidthOrHeight;
    // 文字的高度
    private float mTextHeight;
    // 横轴文字最大宽度
    private float mAxisXTextWidth;
    // 纵轴文字最大宽度
    private float mAxisYTextWidth;
    // (0,0) 在屏幕中的位置
    private PointF mCenterPoint = new PointF();
    // 坐标轴原点在屏幕中的位置
    private PointF mStartPoint = new PointF();
    // 横轴每个刻度间的间距
    private int mMarginX;
    // 纵轴每个刻度间的间距
    private int mMarginY;
    // 横坐标默认刻度
    private int mScaleX = 3;
    // 纵坐标刻度
    private int mScaleY;
    // 坐标轴的宽度
    private float mAxistWitdh;
    // 坐标轴的高度
    private float mAxisHeight;
    // 所有数据中的最小值
    private int mMinValue;
    // 所有数据中的最大值
    private int mMaxValue;
    // 显示popupWindow面板需要的数据
    private HitInfo mHitInfo;
    // hit的index
    private int mHitIndex = -1;
    float lastX;
    float lastY;
    float distanceX;
    float distanceY;
    private IDrawMethod mIDrawMethod;
    private String mDescriptionX;
    private String mDescriptionY;

    public LineCharView(Context context) {
        this(context, null);
    }

    public LineCharView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineCharView);
        mDescriptionX = typedArray.getString(R.styleable.LineCharView_descriptionX);
        mDescriptionY = typedArray.getString(R.styleable.LineCharView_descriptionY);
        typedArray.recycle();
        init();
    }

    private void init() {
        mAxisPaint = new Paint();
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        mAxisPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getContext().getResources().getDisplayMetrics()));

        mTextHeight = (mAxisPaint.getFontMetrics().bottom - mAxisPaint.getFontMetrics().top);
        mAxisXTextWidth = mAxisPaint.measureText("60:00");
        mAxisYTextWidth = mAxisPaint.measureText("-30000");

        mAxisSpace = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getContext().getResources().getDisplayMetrics());
        mAxisIndexWidthOrHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getContext().getResources().getDisplayMetrics());

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint.setAntiAlias(true);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画直角坐标系
        canvas.drawLine(mAxisYTextWidth + mAxisIndexWidthOrHeight + getPaddingLeft(),
                getPaddingTop(),
                mAxisYTextWidth + mAxisIndexWidthOrHeight + getPaddingLeft(),
                getHeight() - mTextHeight - mAxisIndexWidthOrHeight - getPaddingBottom(),
                mAxisPaint);
        canvas.drawLine(mAxisYTextWidth + mAxisIndexWidthOrHeight + getPaddingLeft(),
                getHeight() - mTextHeight - mAxisIndexWidthOrHeight - getPaddingBottom(),
                getWidth() - getPaddingRight(),
                getHeight() - mTextHeight - mAxisIndexWidthOrHeight - getPaddingBottom(),
                mAxisPaint);

        if (lineInfoList != null && lineInfoList.size() > 0) {
            // 画X,Y轴上的分割线
            drawAxisX(canvas);
            drawAxisY(canvas);
            if (mHitIndex != -1) {
                mAxisPaint.setPathEffect(new DashPathEffect(new float[]{UIUtils.dp2px(getContext(), 8), UIUtils.dp2px(getContext(), 2)}, 0));
                mAxisPaint.setStyle(Paint.Style.STROKE);
                Path path = new Path();
                path.moveTo(lineInfoList.get(0).points.get(mHitIndex).x,
                        getPaddingTop());
                path.lineTo(lineInfoList.get(0).points.get(mHitIndex).x,
                        getHeight() - mTextHeight - mAxisIndexWidthOrHeight - getPaddingBottom());
                canvas.drawPath(path, mAxisPaint);
                mAxisPaint.setStyle(Paint.Style.FILL);
                mAxisPaint.setPathEffect(null);
            }
            //画曲线
            for (int i = 0; i < lineInfoList.size(); i++) {
                LineInfo lineInfo = lineInfoList.get(i);
                calculatePoints(lineInfo);
                List<PointF> points = lineInfo.points;
                if (mIDrawMethod == null) {
                    mIDrawMethod = new BezierDrawMethod();
                }
                mLinePaint.setColor(lineInfo.color);
                mLinePaint.setStrokeWidth(2);
                mIDrawMethod.preparePoints(points);
                mIDrawMethod.drawPoints(canvas, mLinePaint, points);
                mLinePaint.setStrokeWidth(10);
                drawNormalPoints(points, canvas);
            }
        }
    }


    /**
     * 画X轴
     *
     * @param canvas
     */
    private void drawAxisX(Canvas canvas) {
        mAxisPaint.setTextAlign(Paint.Align.LEFT);
        // 分钟数
        int min = calculateAxisXSize();
        int textHaftWidth = (int) (mAxisXTextWidth / 2);
        // 坐标轴X轴实际宽度
        mAxistWitdh = getWidth() - mAxisYTextWidth - mAxisSpace - mAxisXTextWidth - getPaddingLeft() - getPaddingRight();
        // 间距
        mMarginX = (int) (mAxistWitdh / (min - 1));
        // 每隔多少个需要画文字
        int divisor = Math.round(mAxisXTextWidth / mMarginX) + 1;
        if (divisor < mScaleX) {
            divisor = mScaleX;
        }
        // 原点的X坐标
        mStartPoint.x = mAxisYTextWidth + mAxisSpace + getPaddingLeft();
        mCenterPoint.x = mStartPoint.x + textHaftWidth;
        float x;
        for (int i = 0; i < min; i++) {
            x = mCenterPoint.x + mMarginX * i;
            if (i % divisor == 0) {
                canvas.drawLine(x,
                        getHeight() - mAxisIndexWidthOrHeight - mTextHeight - getPaddingBottom(),
                        x,
                        getHeight() - mTextHeight - getPaddingBottom(),
                        mAxisPaint);
                drawXText(i, x, canvas);
            } else {
                canvas.drawLine(x,
                        getHeight() - mAxisIndexWidthOrHeight - mTextHeight - getPaddingBottom(),
                        x,
                        getHeight() - mTextHeight - mAxisIndexWidthOrHeight / 2 - getPaddingBottom(),
                        mAxisPaint);
            }
        }
        String descriptionX = "Time";
        if (!TextUtils.isEmpty(mDescriptionX)) {
            descriptionX = mDescriptionX;
        }
        canvas.drawText(descriptionX,
                mCenterPoint.x + mMarginX * (min - 1) - mAxisPaint.measureText(descriptionX) / 2,
                getHeight() - mAxisIndexWidthOrHeight - mTextHeight - mAxisPaint.getFontMetrics().bottom - getPaddingBottom(), mAxisPaint);
    }

    /**
     * 画Y轴
     *
     * @param canvas
     */
    private void drawAxisY(Canvas canvas) {
        mAxisPaint.setTextAlign(Paint.Align.RIGHT);
        // 获取所有数据中的最大最小值
        getMinAndMaxValue();
        int region = (mMaxValue - mMinValue);
        // 默认刻度是2000
        mScaleY = 2000;
        if (region > 25000) {
            mScaleY = 5000;
        }
        int start;
        int end;
        if (mMinValue > 0) {
            start = (int) Math.ceil((double) mMinValue / mScaleY);
        } else {
            start = (int) Math.floor((double) mMinValue / mScaleY);
        }

        if (mMaxValue > 0) {
            end = (int) Math.ceil((double) mMaxValue / mScaleY);
        } else {
            end = (int) Math.floor((double) mMaxValue / mScaleY);
        }
        int startValue = mScaleY * start;
        int endValue = mScaleY * end;
        int size = (int) Math.ceil((double) (endValue - startValue) / mScaleY) + 1;
        // 纵坐标实际高度
        mAxisHeight = getHeight() - mTextHeight - mAxisIndexWidthOrHeight - mAxisXTextWidth - getPaddingBottom() - getPaddingTop();
        mMarginY = (int) (mAxisHeight / (size - 1));
        // 最小值y坐标的值
        mStartPoint.y = mAxisHeight + mAxisXTextWidth / 2 + getPaddingTop();
        // float startY = getHeight() - mTextHeight - mAxisIndexWidthOrHeight - mAxisXTextWidth / 2;
        float y;
        for (int i = 0; i < size; i++) {
            y = mStartPoint.y - mMarginY * i;
            // +4 文字和横线之间留点间距
            canvas.drawLine(mAxisYTextWidth + getPaddingLeft() + 4,
                    y,
                    mAxisYTextWidth + mAxisIndexWidthOrHeight + getPaddingLeft(),
                    y,
                    mAxisPaint);
            drawYText(startValue, mScaleY, i, y, canvas);
        }

        if (!TextUtils.isEmpty(mDescriptionY)) {
            float v = mAxisPaint.measureText(mDescriptionY);
            Path path = new Path();
            path.moveTo(mAxisYTextWidth + mAxisIndexWidthOrHeight + getPaddingLeft() + mTextHeight, v + getPaddingTop());
            path.lineTo(mAxisYTextWidth + mAxisIndexWidthOrHeight + getPaddingLeft() + mTextHeight, getPaddingTop());
            canvas.drawTextOnPath(mDescriptionY, path, 0, 0, mAxisPaint);
        }


    }

    /**
     * 画y轴刻度文字
     */
    private void drawYText(int startValue, int scale, int i, float y, Canvas canvas) {
        startValue += (scale * i);
        if (startValue == 0) {
            mCenterPoint.y = y;
//            // 画基线
//            mAxisPaint.setPathEffect(new DashPathEffect(new float[]{UIUtils.dp2px(getContext(), 8), UIUtils.dp2px(getContext(), 2)}, 0));
//            mAxisPaint.setStyle(Paint.Style.STROKE);
//            Path path = new Path();
//            path.moveTo(mAxisYTextWidth + mAxisIndexWidthOrHeight,
//                    mCenterPoint.y);
//            path.lineTo(getWidth(),
//                    mCenterPoint.y);
//            canvas.drawPath(path, mAxisPaint);
//            mAxisPaint.setStyle(Paint.Style.FILL);
//            mAxisPaint.setPathEffect(null);
        }
        float top = mAxisPaint.getFontMetrics().top;
        float bottom = mAxisPaint.getFontMetrics().bottom;
        canvas.drawText(String.valueOf(startValue), mAxisYTextWidth + getPaddingLeft(), y - top / 2 - bottom / 2, mAxisPaint);
    }

    /**
     * 画x轴刻度文字
     */
    private void drawXText(int i, float startX, Canvas canvas) {
        Paint.FontMetrics fontMetrics = mAxisPaint.getFontMetrics();
        float baseline = getHeight() - fontMetrics.bottom - getPaddingBottom();
        String text = i + ":00";
        canvas.drawText(text, startX - mAxisPaint.measureText(text) / 2, baseline, mAxisPaint);
    }

    private int calculateAxisXSize() {
        return lineInfoList.get(0).values.length;
    }


    private void getMinAndMaxValue() {
        mMinValue = Integer.MAX_VALUE;
        mMaxValue = Integer.MIN_VALUE;
        for (int i = 0; i < lineInfoList.size(); i++) {
            RealmInt[] values = lineInfoList.get(i).values;
            for (RealmInt value : values) {
                mMinValue = Math.min(value.val, mMinValue);
                mMaxValue = Math.max(value.val, mMaxValue);
            }
        }
    }


    private void drawNormalPoints(List<PointF> points, Canvas canvas) {
        for (int i = 0; i < points.size(); i++) {
            if (i == mHitIndex) {
                mLinePaint.setStrokeWidth(20);
                canvas.drawPoint(points.get(i).x, points.get(i).y, mLinePaint);
            } else {
                mLinePaint.setStrokeWidth(10);
                canvas.drawPoint(points.get(i).x, points.get(i).y, mLinePaint);
            }
        }

    }

    /**
     * 计算每个点的坐标
     *
     * @param lineInfo
     */
    private void calculatePoints(LineInfo lineInfo) {
        List<PointF> pointInfos;
        boolean canOverride;
        if (lineInfo.points != null && lineInfo.points.size() == lineInfo.values.length) {
            pointInfos = lineInfo.points;
            canOverride = true;
        } else {
            pointInfos = new ArrayList<>();
            canOverride = false;
        }
        for (int i = 0; i < lineInfo.values.length; i++) {
            PointF pointF;
            if (canOverride) {
                pointF = lineInfo.points.get(i);
            } else {
                pointF = new PointF();
            }
            pointF.x = mCenterPoint.x + i * mMarginX;
            pointF.y = mCenterPoint.y - (lineInfo.values[i].val / (float) mScaleY) * mMarginY;
            if (!canOverride) {
                pointInfos.add(pointF);
            }
        }
        lineInfo.points = pointInfos;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                distanceX = distanceY = 0f;
                lastX = event.getX();
                lastY = event.getY();
                if (lastX < mCenterPoint.x || lastX > mCenterPoint.x + mAxistWitdh
                        || lastY > mStartPoint.y || lastY < mStartPoint.y - mAxisHeight) {
                    return super.onTouchEvent(event);
                } else {
                    binarySearchHitPoint(event);
                    return true;
                }
            case MotionEvent.ACTION_MOVE:
                //如果超过数据边缘or 如果是上下滑
                float tmpX = event.getX();
                float tmpY = event.getY();
                if (tmpX < mCenterPoint.x || tmpX > mCenterPoint.x + mAxistWitdh
                        || tmpY > mStartPoint.y || tmpY < mStartPoint.y - mAxisHeight) {
                    mHitIndex = -1;
                    getParent().requestDisallowInterceptTouchEvent(false);
                    if (mListener != null) {
                        mListener.onDismiss();
                    }
                } else {
                    distanceX += Math.abs(tmpX - lastX);
                    distanceY += Math.abs(tmpY - lastY);
                    lastX = tmpX;
                    lastY = tmpY;
                    if (Math.abs(distanceY) > Math.abs(distanceX) && mHitIndex == -1) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        binarySearchHitPoint(event);
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mHitIndex = -1;
                invalidate();
                getParent().requestDisallowInterceptTouchEvent(false);
                if (mListener != null) {
                    mListener.onDismiss();
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 搜索当前触摸点落在哪个时间点上
     *
     * @param ev
     */
    private void binarySearchHitPoint(MotionEvent ev) {
        LineInfo lineInfo = lineInfoList.get(0);
        //查找范围的上下界
        int low = 0;
        int high = lineInfo.points.size() - 1;
        //未查找到的返回值
        int tmpHitIndex = -1;
        if(lineInfo.points.size() == 0){
            return;
        }
        while (high - low != 1 && high - low != 0) {
            //二分中点=数组左边界+(右边界-左边界)/2
            //整数类型默认取下整
            int mid = low + (high - low) / 2;
            //中间值是如果大于key
            if (lineInfo.points.get(mid).x > ev.getX()) {
                //证明key在[low,mid)这个区间
                high = mid;
            } else if (lineInfo.points.get(mid).x < ev.getX()) {
                //证明key在(mid,high]这个区间
                low = mid;
            } else {
                tmpHitIndex = mid;
                break;
            }
        }

        if (tmpHitIndex == -1) {
            tmpHitIndex = (Math.abs(lineInfo.points.get(low).x - ev.getX()) > Math.abs(lineInfo.points.get(high).x - ev.getX())) ? high : low;
        }
        // 不相等才需要重新绘制
        if (mHitIndex != tmpHitIndex) {
            mHitIndex = tmpHitIndex;
            invalidate();
        }
        if (mListener != null) {
            boolean canOverride = true;
            if (mHitInfo == null) {
                mHitInfo = new HitInfo();
                canOverride = false;
            }
            // 如果只是垂直方向移动,只改变y的值就行了
            if (mHitInfo.hitX == ev.getRawX() + (ev.getX() - lineInfo.points.get(tmpHitIndex).x)) {
                mHitInfo.touchY = ev.getRawY();
            } else {
                mHitInfo.hitX = ev.getRawX() + (ev.getX() - lineInfo.points.get(tmpHitIndex).x);
                mHitInfo.touchY = ev.getRawY();
                mHitInfo.time = mHitIndex;
                if (!canOverride) {
                    mHitInfo.datas = new ArrayList<>();
                }
                for (int i = 0; i < lineInfoList.size(); i++) {
                    GrahItemInfo grahItemInfo;
                    if (!canOverride) {
                        grahItemInfo = new GrahItemInfo();
                    } else {
                        grahItemInfo = mHitInfo.datas.get(i);
                    }
                    grahItemInfo.color = lineInfoList.get(i).color;
                    grahItemInfo.name = lineInfoList.get(i).name;
                    grahItemInfo.value = String.valueOf(lineInfoList.get(i).values[mHitIndex].val);
                    if (!canOverride) {
                        mHitInfo.datas.add(grahItemInfo);
                    }
                }
            }
            mListener.onHit(mHitInfo);
        }


    }

    public void putLinesData(List<LineCharView.LineInfo> lineInfoList) {
        this.lineInfoList = lineInfoList;
        requestLayout();
    }

    private OnPointHitListener mListener;

    public void setOnPointHitListener(OnPointHitListener listener) {
        mListener = listener;
    }

    public static interface OnPointHitListener {
        void onHit(HitInfo info);

        void onDismiss();
    }

    public static class LineInfo {
        public String name;
        public int color;
        public RealmInt[] values;
        public List<PointF> points;
    }

    public static class HitInfo {
        public float hitX;
        public float touchY;
        public int time;
        public List<GrahItemInfo> datas;

        @Override
        public String toString() {
            return "HitInfo{" +
                    "hitX=" + hitX +
                    ", touchY=" + touchY +
                    ", time='" + time + '\'' +
                    ", datas=" + datas +
                    '}';
        }
    }

    public static class GrahItemInfo {
        public int color;
        public String name;
        public String value;

        @Override
        public String toString() {
            return "GrahItemInfo{" +
                    "color=" + color +
                    ", name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GrahItemInfo grahItemInfo = (GrahItemInfo) o;

            return name != null ? name.equals(grahItemInfo.name) : grahItemInfo.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }
}
