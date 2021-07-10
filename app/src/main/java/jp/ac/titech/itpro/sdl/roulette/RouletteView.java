package jp.ac.titech.itpro.sdl.roulette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class RouletteView extends View {
    private final Paint paint = new Paint();

    private int split = 6;
    private float direction = 0;


    public RouletteView(Context context) {
        this(context, null);
    }

    public RouletteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RouletteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int cx = w / 2;
        int cy = h / 2;
        float angle = 360 / split;
        int r = Math.min(cx, cy) - 20;
        paint.setColor(Color.argb(255, 0, 0, 255));
        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(cx, cy-r);
        path.lineTo(cx+r/4, cy-r-r/4);
        path.lineTo(cx-r/4, cy-r-r/4);
        path.close();
        canvas.drawPath(path, paint);
        for (int i=0; i<split; i++) {
            paint.setColor(Color.argb(255,0,0,255/split*i));
            RectF oval = new RectF(cx-r, cy-r, cx+r, cy+r);
            canvas.drawArc(oval, -90+direction+angle*i, angle, true, paint);
            float mid = (direction + angle*i + angle/2) * (float) Math.PI / 180;
            float x = cx + r / 2 * (float) Math.sin(mid);
            float y = cy - r / 2 * (float) Math.cos(mid);
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(50);
            canvas.drawText(Integer.toString(i+1), x, y, paint);
        }
    }

    void setSplit(int n) {
        split = n;
        invalidate();
    }

    void setDirection(float th) {
        direction = th;
        this.getRotation();
        invalidate();
    }
}
