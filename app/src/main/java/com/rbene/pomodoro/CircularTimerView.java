package com.rbene.pomodoro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircularTimerView extends View {

    private float progress = 0f; // 0 a 1
    private Paint bgPaint, fgPaint; //Paint "background" e "frontground"

    public CircularTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bgPaint = new Paint();
        bgPaint.setColor(0x33000000);
        bgPaint.setStrokeWidth(20f);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setAntiAlias(true);

        fgPaint = new Paint();
        fgPaint.setColor(0xFFFF0000);
        fgPaint.setStrokeWidth(20f);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setAntiAlias(true);
    }

    public void setProgress(float value) {
        this.progress = Math.max(0, Math.min(1, value));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float size = Math.min(getWidth(), getHeight());
        float pad = 20f;
        float radius = size / 2 - pad;
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;

        // Fundo completo
        canvas.drawCircle(cx, cy, radius, bgPaint);

        // Arco no sentido anti-horário
        float sweepAngle = 360f * (1 - progress);
        canvas.drawArc(cx - radius, cy - radius, cx + radius, cy + radius,
                -90, sweepAngle, false, fgPaint);
    }
}
