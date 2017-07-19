package cn.edu.mydotabuff.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Collection;
import java.util.Map;

import cn.edu.mydotabuff.R;

/**
 * Created by sadhu on 2017/7/14.
 * 描述 折线图
 */
public class LineCharView extends View {
    private Map<String, Integer[]> linesMap;
    private Paint mAxisPaint;
    private Paint mTextPaint;
    private float mAxisSpace;
    private float mAxisIndexWidthOrHeight;
    private int mDefaultValuedIndex = 3;
    private float mTextHeight;
    private float mAxisXTextWidth;
    private float mAxisYTextWidth;

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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(mAxisYTextWidth + mAxisIndexWidthOrHeight,
                getPaddingTop());
        path.lineTo(mAxisYTextWidth + mAxisIndexWidthOrHeight,
                getHeight() - mTextHeight - mAxisIndexWidthOrHeight + getPaddingBottom());
        path.lineTo(getWidth() - getPaddingRight(),
                getHeight() - mTextHeight - mAxisIndexWidthOrHeight + getPaddingBottom());
        canvas.drawPath(path, mAxisPaint);
        if (linesMap != null && linesMap.size() > 0) {
            drawAxisX(canvas);
            drawAxisY(canvas);
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
        int offest = (int) ((getWidth() - mAxisYTextWidth - mAxisSpace - mAxisXTextWidth) / (min - 1));
        int valueIndex = Math.round(mAxisXTextWidth / offest) + 1;
        if (valueIndex < mDefaultValuedIndex) {
            valueIndex = mDefaultValuedIndex;
        }
        for (int i = 0; i < min; i++) {
            if (i % valueIndex == 0) {
                float x = offest * i + mAxisYTextWidth + mAxisSpace + textHaftWidth;
                canvas.drawLine(x,
                        getHeight() - mAxisIndexWidthOrHeight - mTextHeight,
                        x,
                        getHeight() - mTextHeight,
                        mAxisPaint);
                drawTime(i, x, canvas);
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
        Data data = getMinAndMaxValue();
        int region = (data.max - data.min);
        int valueIndex = 2000;
        if (region > 25000) {
            valueIndex = 5000;
        }
        int start;
        int end;
        if (data.min > 0) {
            start = (int) Math.ceil((double) data.min / valueIndex);
        } else {
            start = (int) Math.floor((double) data.min / valueIndex);
        }

        if (data.max > 0) {
            end = (int) Math.ceil((double) data.max / valueIndex);
        } else {
            end = (int) Math.floor((double) data.max / valueIndex);
        }
        int startValue = valueIndex * start;
        int endValue = valueIndex * end;
        int size = (int) Math.ceil((double) (endValue - startValue) / valueIndex) + 1;
        int offest = (int) ((getHeight() - mTextHeight - mAxisIndexWidthOrHeight - mAxisXTextWidth) / (size - 1));
        for (int i = 0; i < size; i++) {
            float y = getHeight() - offest * i - mTextHeight - mAxisIndexWidthOrHeight - mAxisXTextWidth / 2;
            canvas.drawLine(mAxisYTextWidth,
                    y,
                    mAxisYTextWidth + mAxisIndexWidthOrHeight,
                    y,
                    mAxisPaint);
            drawValues(startValue, valueIndex, i, y, canvas);
        }
    }

    private void drawValues(int startValue, int valueIndex, int i, float y, Canvas canvas) {
        startValue += (valueIndex * i);
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
        Collection<Integer[]> values = linesMap.values();
        Integer[] next = values.iterator().next();
        return next.length;
    }


    private Data getMinAndMaxValue() {
        Data data = new Data();
        data.min = Integer.MAX_VALUE;
        data.max = Integer.MIN_VALUE;
        Collection<Integer[]> values = linesMap.values();
        for (Integer[] value : values) {
            for (Integer integer : value) {
                data.min = Math.min(integer, data.min);
                data.max = Math.max(integer, data.max);
            }
        }
        return data;
    }


    public void putLinesData(Map<String, Integer[]> linesMap) {
        this.linesMap = linesMap;
        requestLayout();
    }

    class Data {
        int min;
        int max;
    }
}
