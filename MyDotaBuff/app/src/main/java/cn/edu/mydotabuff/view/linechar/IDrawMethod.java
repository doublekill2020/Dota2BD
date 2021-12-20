package cn.edu.mydotabuff.view.linechar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.List;

/**
 * @author linzewu
 * @date 2017/7/21
 */

public interface IDrawMethod {

    void preparePoints(List<PointF> pointFList);

    void drawPoints(Canvas canvas, Paint paint, List<PointF> pointList);

}
