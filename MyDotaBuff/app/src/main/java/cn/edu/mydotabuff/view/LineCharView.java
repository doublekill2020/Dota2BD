package cn.edu.mydotabuff.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by sadhu on 2017/7/14.
 * 描述 折线图
 */
public class LineCharView extends View {
    private Map<String, List<Integer>> linesMap;

    public LineCharView(Context context) {
        super(context);
    }

    public LineCharView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void calculateAxisXWidth() {
        Collection<List<Integer>> values = linesMap.values();
        int min = values.size();
    }
    private void calculateAxisYHeight() {
        Collection<List<Integer>> values = linesMap.values();
    }
}
