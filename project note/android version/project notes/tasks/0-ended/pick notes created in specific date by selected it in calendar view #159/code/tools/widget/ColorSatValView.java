package com.lukaszjag.diet_tracker_android.tools.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ColorSatValView extends View {
    private Paint paint;
    private Paint thumbPaint;
    private Shader valShader;
    private float hue = 0f;
    private float sat = 0f;
    private float val = 1f;
    private OnColorChangedListener listener;

    public interface OnColorChangedListener {
        void onSatValChanged(float sat, float val);
    }

    public ColorSatValView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        thumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        thumbPaint.setStyle(Paint.Style.STROKE);
        thumbPaint.setStrokeWidth(5f);
    }

    public void setListener(OnColorChangedListener listener) {
        this.listener = listener;
    }

    public void setHue(float hue) {
        this.hue = hue;
        updateShader();
        invalidate();
    }

    public void setSatVal(float sat, float val) {
        this.sat = Math.max(0f, Math.min(1f, sat));
        this.val = Math.max(0f, Math.min(1f, val));
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        valShader = new LinearGradient(0, 0, 0, h, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP);
        updateShader();
    }

    private void updateShader() {
        int w = getWidth();
        if (w <= 0 || valShader == null) return;
        int pureHue = Color.HSVToColor(new float[]{hue, 1f, 1f});
        Shader satShader = new LinearGradient(0, 0, w, 0, Color.WHITE, pureHue, Shader.TileMode.CLAMP);
        ComposeShader composeShader = new ComposeShader(satShader, valShader, PorterDuff.Mode.SRC_OVER);
        paint.setShader(composeShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        canvas.drawRect(0, 0, w, h, paint);

        float cx = sat * w;
        float cy = (1f - val) * h;
        thumbPaint.setColor(val > 0.5f ? Color.BLACK : Color.WHITE);
        canvas.drawCircle(cx, cy, 14f, thumbPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = Math.max(0f, Math.min(getWidth(), event.getX()));
            float y = Math.max(0f, Math.min(getHeight(), event.getY()));
            sat = x / getWidth();
            val = 1f - (y / getHeight());
            invalidate();
            if (listener != null) listener.onSatValChanged(sat, val);
            return true;
        }
        return super.onTouchEvent(event);
    }
}