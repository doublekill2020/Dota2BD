package cn.edu.mydotabuff.view.linechar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import java.util.List;

/**
 * Created by sadhu on 2017/7/14.
 * 描述  Catmull-Rom算法绘制曲线
 */
public class CatmullDrawMethod implements IDrawMethod {

    private Path mPath;

    @Override
    public void preparePoints(List<PointF> pointFList) {
        if (pointFList == null || pointFList.size() < 2) {
            return ;
        }
        mPath = savePathCatmullRom(pointFList);
    }

    @Override
    public void drawPoints(Canvas canvas, Paint paint,List<PointF> pointList) {
        canvas.drawPath(mPath, paint);
    }


    private Path savePathCatmullRom(List<PointF> pointFList) {
        if (pointFList == null) {
            return null;
        }
        int length = pointFList.size();
        Path path = new Path();
        path.moveTo(pointFList.get(0).x, pointFList.get(0).y);
        for (int i = 0; i < length-1; i++) {
            for (float u = 0.0f; u < 1.0f; u += 0.001) {
                PointF pointF = interpolatedPosition(
                                pointFList.get(Math.max(0, i-1)),
                                pointFList.get(i),
                                pointFList.get(Math.min(i+1, length-1)),
                                pointFList.get(Math.min(i+2, length-1)),
                                u);
                path.lineTo(pointF.x, pointF.y);
            }
        }
        return path;
    }
    
    private PointF interpolatedPosition(PointF point0, PointF point1,
                                        PointF point2, PointF point3, float i) {
        float u3 = i * i * i;
        float u2 = i * i;
        float f1 = -0.5f * u3 + u2 - 0.5f * i;
        float f2 = 1.5f * u3 - 2.5f * u2 + 1.0f;
        float f3 = -1.5f * u3 + 2.0f * u2 + 0.5f * i;
        float f4 = 0.5f * u3 - 0.5f * u2;
        float x = point0.x * f1 + point1.x * f2 + point2.x * f3 + point3.x * f4;
        float y = point0.y * f1 + point1.y * f2 + point2.y * f3 + point3.y * f4;
        return new PointF(x, y);
    }
}
