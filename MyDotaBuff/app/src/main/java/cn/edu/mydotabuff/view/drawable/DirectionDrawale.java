package cn.edu.mydotabuff.view.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by sadhu on 2017/7/13.
 * 描述
 */
public class DirectionDrawale extends Drawable {
    private Paint mPaint;
    private Path mPath;
    private float width;
    private float height;
    private int color;

    public DirectionDrawale(float width, float height, int color) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.mPaint = new Paint(5);
        this.mPath = new Path();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        this.mPaint.setColor(color);
        this.mPath.reset();
        this.mPath.moveTo(0f, 0f);
        this.mPath.lineTo(width, 0f);
        this.mPath.lineTo(0f, height);
        this.mPath.close();
        canvas.drawPath(this.mPath, this.mPaint);
    }

    @Override
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
