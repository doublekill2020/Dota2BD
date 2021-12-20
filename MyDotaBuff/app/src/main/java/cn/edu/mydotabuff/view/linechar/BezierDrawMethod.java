package cn.edu.mydotabuff.view.linechar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadhu on 2017/7/14.
 * 描述 利用贝塞尔曲线画平滑曲线
 */
public class BezierDrawMethod implements IDrawMethod {

    private Path mPath;
    private List<PointF> mSavePoints = new ArrayList<>();

    @Override
    public void preparePoints(List<PointF> pointFList) {
        mSavePoints.clear();
        mSavePoints.addAll(pointFList);


        if(mSavePoints.size() == 0){
            return;
        }
        mSavePoints.add(0, new PointF(mSavePoints.get(0).x, mSavePoints.get(0).y));
        mSavePoints.add(new PointF(mSavePoints.get(mSavePoints.size() - 1).x,
                mSavePoints.get(mSavePoints.size() - 1).y));
        mSavePoints.add(new PointF(mSavePoints.get(mSavePoints.size() - 1).x,
                mSavePoints.get(mSavePoints.size() - 1).y));

        mPath = new Path();
        mPath.moveTo(mSavePoints.get(0).x, mSavePoints.get(0).y);

        for (int i = 1; i < mSavePoints.size() - 3; i++) {
            PointF ctrlPointA = new PointF();
            PointF ctrlPointB = new PointF();
            getCtrlPoint(mSavePoints, i, ctrlPointA, ctrlPointB);
            mPath.cubicTo(ctrlPointA.x, ctrlPointA.y, ctrlPointB.x, ctrlPointB.y,
                    mSavePoints.get(i + 1).x, mSavePoints.get(i + 1).y);
        }
    }

    @Override
    public void drawPoints(Canvas canvas, Paint paint, List<PointF> pointList) {
        if(mPath == null){
            return;
        }
        canvas.drawPath(mPath, paint);
    }

    private static final float CTRL_VALUE_A = 0.2f;
    private static final float CTRL_VALUE_B = 0.2f;

    /**
     * 根据已知点获取第i个控制点的坐标
     *
     * @param pointFList
     * @param currentIndex
     * @param ctrlPointA
     * @param ctrlPointB
     */
    private void getCtrlPoint(List<PointF> pointFList, int currentIndex,
                              PointF ctrlPointA, PointF ctrlPointB) {
        ctrlPointA.x = pointFList.get(currentIndex).x +
                (pointFList.get(currentIndex + 1).x - pointFList.get(currentIndex - 1).x) * CTRL_VALUE_A;
        ctrlPointA.y = pointFList.get(currentIndex).y +
                (pointFList.get(currentIndex + 1).y - pointFList.get(currentIndex - 1).y) * CTRL_VALUE_A;
        ctrlPointB.x = pointFList.get(currentIndex + 1).x -
                (pointFList.get(currentIndex + 2).x - pointFList.get(currentIndex).x) * CTRL_VALUE_B;
        ctrlPointB.y = pointFList.get(currentIndex + 1).y -
                (pointFList.get(currentIndex + 2).y - pointFList.get(currentIndex).y) * CTRL_VALUE_B;
    }
}
