package cn.edu.mydotabuff.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.realm.RealmInt;

/**
 * Created by sadhu on 2017/7/14.
 * 描述 折线图
 */
public class LineCharView extends View {
    private List<LineCharView.LineInfo> lineInfoList;

    private Map<String, List<PointInfo>> linesInfoMap = new HashMap<>();
    private Paint mAxisPaint;
    private Paint mTextPaint;
    private Paint mPointPaint;
    private float mAxisSpace;
    private float mAxisIndexWidthOrHeight;
    private int mDefaultValuedIndex = 3;
    private float mTextHeight;
    private float mAxisXTextWidth;
    private float mAxisYTextWidth;
    // 曲线的路径
    private final Path mCurvePath = new Path();
    // 形成曲线所组成的点
    private List<PointF> mCurveSavePoints = new ArrayList<>();

    // (0,0) 在屏幕中的位置
    private PointF mCenterPoint = new PointF();
    // 坐标轴原点在屏幕中的位置
    private PointF mStartPoint = new PointF();
    private int mMarginX;
    private int mMarginY;
    private int mScaleY;
    private float mAxistWitdh;
    private float mAxisHeight;
    private int mMinValue;
    private int mMaxValue;
    private int mHitIndex = -1;

    public LineCharView(Context context) {
        this(context, null);
    }

    public LineCharView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mAxisPaint = new Paint();
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setStyle(Paint.Style.STROKE);
        mAxisPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getContext().getResources().getDisplayMetrics()));
        mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        mTextHeight = (mTextPaint.getFontMetrics().bottom - mTextPaint.getFontMetrics().top);
        mAxisXTextWidth = mTextPaint.measureText("60:00");
        mAxisYTextWidth = mTextPaint.measureText("-30000");

        mAxisSpace = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getContext().getResources().getDisplayMetrics());
        mAxisIndexWidthOrHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getContext().getResources().getDisplayMetrics());

        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeCap(Paint.Cap.ROUND);
        mPointPaint.setAntiAlias(true);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        // 画直角坐标系
        path.moveTo(mAxisYTextWidth + mAxisIndexWidthOrHeight,
                getPaddingTop());
        path.lineTo(mAxisYTextWidth + mAxisIndexWidthOrHeight,
                getHeight() - mTextHeight - mAxisIndexWidthOrHeight + getPaddingBottom());
        path.lineTo(getWidth() - getPaddingRight(),
                getHeight() - mTextHeight - mAxisIndexWidthOrHeight + getPaddingBottom());
        canvas.drawPath(path, mAxisPaint);
        if (lineInfoList != null && lineInfoList.size() > 0) {
            // 画X,Y轴上的分割线
            drawAxisX(canvas);
            drawAxisY(canvas);
            //画曲线
            for (int i = 0; i < lineInfoList.size(); i++) {
                LineInfo lineInfo = lineInfoList.get(i);
                calculatePoints(lineInfo.name, lineInfo.values);
                List<PointInfo> pointInfos = linesInfoMap.get(lineInfo.name);
                mCurveSavePoints.clear();
                mCurvePath.reset();
                function_Catmull_Rom(pointInfos, mCurveSavePoints, mCurvePath);
                mPointPaint.setColor(lineInfo.color);
                mPointPaint.setStrokeWidth(2);
                canvas.drawPath(mCurvePath, mPointPaint);
                mPointPaint.setStrokeWidth(10);
                drawNormalPoints(pointInfos, canvas);
            }
        }
    }


    /**
     * 画X轴
     *
     * @param canvas
     */
    private void drawAxisX(Canvas canvas) {
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        // 分钟数
        int min = calculateAxisXSize();
        int textHaftWidth = (int) (mAxisXTextWidth / 2);
        // 坐标轴X轴实际宽度
        mAxistWitdh = getWidth() - mAxisYTextWidth - mAxisSpace - mAxisXTextWidth;
        // 间距
        mMarginX = (int) (mAxistWitdh / (min - 1));
        // 每隔多少个需要画文字
        int divisor = Math.round(mAxisXTextWidth / mMarginX) + 1;
        if (divisor < mDefaultValuedIndex) {
            divisor = mDefaultValuedIndex;
        }
        // 原点的X坐标
        mStartPoint.x = mAxisYTextWidth + mAxisSpace;
        mCenterPoint.x = mStartPoint.x + textHaftWidth;
        float x;
        for (int i = 0; i < min; i++) {
            x = mCenterPoint.x + mMarginX * i;
            if (i % divisor == 0) {
                canvas.drawLine(x,
                        getHeight() - mAxisIndexWidthOrHeight - mTextHeight,
                        x,
                        getHeight() - mTextHeight,
                        mAxisPaint);
                drawTime(i, x, canvas);
            } else {
                canvas.drawLine(x,
                        getHeight() - mAxisIndexWidthOrHeight - mTextHeight,
                        x,
                        getHeight() - mTextHeight - mAxisIndexWidthOrHeight / 2,
                        mAxisPaint);
            }
        }
    }

    /**
     * 画Y轴
     *
     * @param canvas
     */
    private void drawAxisY(Canvas canvas) {
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
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
        mAxisHeight = getHeight() - mTextHeight - mAxisIndexWidthOrHeight - mAxisXTextWidth;
        mMarginY = (int) (mAxisHeight / (size - 1));
        // 最小值y坐标的值
        mStartPoint.y = mAxisHeight + mAxisXTextWidth / 2;
//        float startY = getHeight() - mTextHeight - mAxisIndexWidthOrHeight - mAxisXTextWidth / 2;
        float y;
        for (int i = 0; i < size; i++) {
            y = mStartPoint.y - mMarginY * i;
            canvas.drawLine(mAxisYTextWidth,
                    y,
                    mAxisYTextWidth + mAxisIndexWidthOrHeight,
                    y,
                    mAxisPaint);
            drawValues(startValue, mScaleY, i, y, canvas);
        }
    }

    private void drawValues(int startValue, int scale, int i, float y, Canvas canvas) {
        startValue += (scale * i);
        if (startValue == 0) {
            mCenterPoint.y = y;
            // 画基线
            canvas.drawLine(mAxisYTextWidth + mAxisIndexWidthOrHeight,
                    mCenterPoint.y,
                    getWidth(),
                    mCenterPoint.y,
                    mAxisPaint);
        }
        float top = mTextPaint.getFontMetrics().top;
        float bottom = mTextPaint.getFontMetrics().bottom;
        canvas.drawText(String.valueOf(startValue), mAxisYTextWidth, y - top / 2 - bottom / 2, mTextPaint);
    }

    private void drawTime(int i, float startX, Canvas canvas) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float baseline = getHeight() - fontMetrics.bottom;
        String text = i + ":00";
        canvas.drawText(text, startX - mTextPaint.measureText(text) / 2, baseline, mTextPaint);
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


    private void drawNormalPoints(List<PointInfo> pointInfos, Canvas canvas) {
        Logger.d("hitIndex:" + mHitIndex);
        for (int i = 0; i < pointInfos.size(); i++) {
            if (i == mHitIndex) {
                mPointPaint.setStrokeWidth(20);
                canvas.drawPoint(pointInfos.get(i).point.x, pointInfos.get(i).point.y, mPointPaint);
            } else {
                mPointPaint.setStrokeWidth(10);
                canvas.drawPoint(pointInfos.get(i).point.x, pointInfos.get(i).point.y, mPointPaint);
            }
        }

    }

    private void calculatePoints(String key, RealmInt[] values) {
        ArrayList<PointInfo> pointInfos = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            PointInfo pointInfo = new PointInfo();
            PointF pointF = new PointF();
            pointF.x = mCenterPoint.x + i * mMarginX;
            pointF.y = mCenterPoint.y - (values[i].val / (float) mScaleY) * mMarginY;
            pointInfo.point = pointF;
            pointInfo.value = values[i].val;
            pointInfos.add(pointInfo);
        }
        linesInfoMap.put(key, pointInfos);
    }

    private void function_Catmull_Rom(List<PointInfo> pointInfos, List<PointF> save, Path path) {
        if (pointInfos.size() < 4) {
            return;
        }
        path.moveTo(pointInfos.get(0).point.x, pointInfos.get(0).point.y);
        save.add(pointInfos.get(0).point);
        PointF pi = new PointF(); // intermediate point
        for (int index = 1; index < pointInfos.size() - 2; index++) {
            PointF p0 = pointInfos.get(index - 1).point;
            PointF p1 = pointInfos.get(index).point;
            PointF p2 = pointInfos.get(index + 1).point;
            PointF p3 = pointInfos.get(index + 2).point;
            for (int i = 1; i <= 100; i++) {
                float t = i * (1.0f / 100);
                float tt = t * t;
                float ttt = tt * t;
                pi.x = (float) (0.5 * (2 * p1.x + (p2.x - p0.x) * t + (2 * p0.x - 5 * p1.x + 4 * p2.x - p3.x) * tt + (3 * p1.x - p0.x - 3 * p2.x + p3.x)
                        * ttt));
                pi.y = (float) (0.5 * (2 * p1.y + (p2.y - p0.y) * t + (2 * p0.y - 5 * p1.y + 4 * p2.y - p3.y) * tt + (3 * p1.y - p0.y - 3 * p2.y + p3.y)
                        * ttt));
                path.lineTo(pi.x, pi.y);
                save.add(pi);
            }
        }
        path.lineTo(pointInfos.get(pointInfos.size() - 1).point.x, pointInfos.get(pointInfos.size() - 1).point.y);
        save.add(pointInfos.get(pointInfos.size() - 1).point);
    }

    float lastX;
    float lastY;
    float distanceX;
    float distanceY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                distanceX = distanceY = 0f;
                lastX = event.getX();
                lastY = event.getY();
                binarySearchHitPoint(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                //如果超过数据边缘or 如果是上下滑
                float tmpX = event.getX();
                float tmpY = event.getY();
                if (tmpX < mCenterPoint.x || tmpX > mCenterPoint.x + mAxistWitdh
                        || tmpY > mStartPoint.y || tmpY < mStartPoint.y - mAxisHeight) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    distanceX += Math.abs(tmpX - lastX);
                    distanceY += Math.abs(tmpY - lastY);
                    lastX = tmpX;
                    lastY = tmpY;
                    if (Math.abs(distanceY) > Math.abs(distanceX)) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        com.orhanobut.logger.Logger.d("上下滑动");
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
                break;
        }
        return super.onTouchEvent(event);
    }


    private void binarySearchHitPoint(MotionEvent ev) {
        Set<Map.Entry<String, List<PointInfo>>> entries = linesInfoMap.entrySet();
        Map.Entry<String, List<PointInfo>> next = entries.iterator().next();
        List<PointInfo> pointInfos = next.getValue();
        //查找范围的上下界
        int low = 0;
        int high = pointInfos.size() - 1;
        //未查找到的返回值
        int tmpHitIndex = -1;
        while (high - low != 1 && high - low != 0) {
            //二分中点=数组左边界+(右边界-左边界)/2
            //整数类型默认取下整
            int mid = low + (high - low) / 2;
            //中间值是如果大于key
            if (pointInfos.get(mid).point.x > ev.getX()) {
                //证明key在[low,mid)这个区间
                high = mid;
            } else if (pointInfos.get(mid).point.x < ev.getX()) {
                //证明key在(mid,high]这个区间
                low = mid;
            } else {
                tmpHitIndex = mid;
                break;
            }
        }

        if (tmpHitIndex == -1) {
            Logger.d("low %d ,high %d", low, high);
            tmpHitIndex = (Math.abs(pointInfos.get(low).point.x - ev.getX()) > Math.abs(pointInfos.get(high).point.x - ev.getX())) ? high : low;
        }
        // 不相等才需要重新绘制
        if (mHitIndex != tmpHitIndex) {
            mHitIndex = tmpHitIndex;
            if (mListener != null) {
                HitInfo hitInfo = new HitInfo();
                hitInfo.hitX = ev.getRawX() + (ev.getX() - pointInfos.get(tmpHitIndex).point.x);
                hitInfo.touchY = ev.getRawY();
                hitInfo.time = mHitIndex + ":00";
                for (Map.Entry<String, List<PointInfo>> entry : entries) {
                    String key = entry.getKey();
                    GrahInfo grahInfo = new GrahInfo();
                    grahInfo.name = key;
                    grahInfo.value = String.valueOf(entry.getValue().get(mHitIndex).value);
                }


                ArrayList<GrahInfo> datas = new ArrayList<>();


//                datas.add()
//
//                datas.add()
                hitInfo.datas = datas;
                mListener.onHit(hitInfo);
            }
            invalidate();
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
    }

    class PointInfo {
        public PointF point;
        public int value;
    }

    public static class HitInfo {
        public float hitX;
        public float touchY;
        public String time;
        public List<GrahInfo> datas;
    }

    class GrahInfo {
        public Color color;
        public String name;
        public String value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GrahInfo grahInfo = (GrahInfo) o;

            return name != null ? name.equals(grahInfo.name) : grahInfo.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }
}
