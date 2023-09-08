package com.vvv.bball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class Basketball implements GameObject {
    private float x, y;
    private final Bitmap bitmap;

    public Basketball(Context context, float x, float y) {
        this.x = x;
        this.y = y;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.basketball);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    public void update() {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            this.x = event.getX() - (float) this.getWidth() / 2;
            this.y = event.getY() - (float) this.getHeight() / 2;
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

}
