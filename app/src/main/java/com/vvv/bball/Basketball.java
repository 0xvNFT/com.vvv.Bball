package com.vvv.bball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class Basketball implements GameObject {
    private float x, y;
    private final Bitmap bitmap;
    private float velocityX, velocityY;
    private final float radius;
    private final int screenWidth;
    private final int screenHeight;
    private final float gravity = 3f;
    private final float energyLoss = 0.7f;

    public Basketball(Context context, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.radius = 50;
        this.x = radius;
        this.y = screenHeight - radius;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.basketball);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    public void update() {
        velocityY += gravity;
        x += velocityX;
        y += velocityY;

// Bounce off walls and floor
        if (x - radius <= 0 || x + radius >= screenWidth) {
            velocityX = -velocityX * energyLoss;
        }

        if (y - radius <= 0) {  // New condition for the top edge
            y = radius;
            velocityY = -velocityY * energyLoss;
        }

        if (y + radius >= screenHeight) {
            y = screenHeight - radius;
            velocityY = -velocityY * energyLoss;
        }
    }

    public void setVelocity(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
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

    public float getRadius() {
        return radius;
    }
    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

}
