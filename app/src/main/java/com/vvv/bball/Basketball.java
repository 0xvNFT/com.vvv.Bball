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
    private final float energyLoss = 0.5f;
    private final float velocityThreshold = 0.5f;
    private int groundHits = 0;


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
        velocityY += gravity;
        y += velocityY;

        if (y + radius >= screenHeight) {
            y = screenHeight - radius;
            velocityY = -velocityY * energyLoss;
            groundHits++; // Increment ground hit counter
        }

        // Separate condition for stopping x-axis movement when on ground
        if (y + radius >= screenHeight && Math.abs(velocityY) < velocityThreshold) {
            velocityX = 0;  // Stop x movement when on the ground
        } else {
            x += velocityX; // Otherwise, update x
        }

        // Bounce off walls
        if (x - radius <= 0 || x + radius >= screenWidth) {
            velocityX = -velocityX * energyLoss;
        }

        if (y - radius <= 0) {  // New condition for the top edge
            y = radius;
            velocityY = -velocityY * energyLoss;
        }

        // Reset ball's position after 3 ground hits
        if (groundHits >= 3) {
            // Reset the ball to its initial position
            this.x = radius;
            this.y = screenHeight - radius;

            // Reset velocities
            this.velocityX = 0;
            this.velocityY = 0;

            groundHits = 0; // Reset the ground hits counter
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
