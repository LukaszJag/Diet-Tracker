package com.lukaszjag.diet_tracker_android.tools.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ColorHueBarView extends View {
    private Paint paint;
    private Paint thumbPaint;
    private float hue = 0f;
    private OnHueChangedListener listener;

    private static final int[] RAINBOW = {
            0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000
    };

    public interface OnHueChangedListener {
        void onHueChanged(float hue);
    }

    public ColorHueBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        thumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        thumbPaint.setStyle(Paint.Style.STROKE);
        thumbPaint.setStrokeWidth(5f);
        thumbPaint.setColor(Color.WHITE);
    }

    public void setListener(OnHueChangedListener listener) {
        this.listener = listener;
    }

    public void setHue(float hue) {
        this.hue = Math.max(0f, Math.min(360f, hue));
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LinearGradient gradient = new LinearGradient(0, 0, 0, h, RAINBOW, null, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        canvas.drawRect(0, 0, w, h, paint);

        float y = (hue / 360f) * h;
        canvas.drawRect(0, y - 4f, w, y + 4f, thumbPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float y = Math.max(0f, Math.min(getHeight(), event.getY()));
            hue = (y / getHeight()) * 360f;
            invalidate();
            if (listener != null) listener.onHueChanged(hue);
            return true;
        }
        return super.onTouchEvent(event);
    }
}