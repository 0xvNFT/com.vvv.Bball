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
    final float energyLoss = 0.5f;
    private int groundHits = 0;
    private boolean isBallReset = true;


    public Basketball(Context context, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.radius = 50;
        this.x = radius;
        this.y = screenHeight - radius;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.basketball);
        groundHits = 0;

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    public void update() {
        float gravity = 3f;
        velocityY += gravity;
        float drag = 0.99f;
        velocityX *= drag;
        velocityY *= drag;
        float magnusEffect = 0.02f;
        velocityY += magnusEffect * velocityX;
        y += velocityY;

        float angularVelocity = 0.1f;
        velocityX += (float) (angularVelocity * Math.sin(velocityY));

        if (y + radius >= screenHeight) {
            y = screenHeight - radius;
            velocityY = -velocityY * energyLoss;
            groundHits++;
        }

        float velocityThreshold = 0.5f;
        if (y + radius >= screenHeight && Math.abs(velocityY) < velocityThreshold) {
            velocityX = 0;
        } else {
            x += velocityX;
        }


        if (x - radius <= 0 || x + radius >= screenWidth) {
            velocityX = -velocityX * energyLoss;
        }

        if (y - radius <= 0) {
            y = radius;
            velocityY = -velocityY * energyLoss;
        }


        if (groundHits >= 3) {

            this.x = radius;
            this.y = screenHeight - radius;

            this.velocityX = 0;
            this.velocityY = 0;

            groundHits = 0;
            isBallReset = true;

        }
    }

    public boolean isBallReset() {
        return isBallReset;
    }
    public void setVelocity(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        isBallReset = false;
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

    public float getYVelocity() {
        return velocityY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void recycle() {
        this.bitmap.recycle();
    }
}
