package com.vvv.bball;

import static com.vvv.bball.Constants.DAMPING_FACTOR;
import static com.vvv.bball.Constants.THROW_POWER_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Basketball extends GameObject {
    private final float gravity;
    private final int screenHeight, screenWidth;
    private float velX, velY;
    private boolean isThrown;

    public Basketball(float x, float y, Bitmap image, int screenHeight, int screenWidth) {
        super(x, y, image);
        velX = 0;
        velY = 0;
        gravity = 1;
        this.screenHeight = screenHeight / 4;
        this.screenWidth = screenWidth * 3 / 4;
    }

    public void setVelocity(float velocityX, float velocityY) {
        velX = velocityX * THROW_POWER_MULTIPLIER;
        velY = velocityY * THROW_POWER_MULTIPLIER;
    }

    public void update() {
        x += velX;
        y += velY;

        if (x < 0 || x + image.getWidth() > screenWidth) {
            velX = -velX * DAMPING_FACTOR;
        }

        if (y < 0) {
            velY = -velY * DAMPING_FACTOR;
        }

        if (y + image.getHeight() > screenHeight) {
            velY = -Math.abs(velY) * DAMPING_FACTOR;
            y = screenHeight - image.getHeight();
        }

        velY += gravity;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void throwBall(float velX, float velY) {
        this.velX = velX;
        this.velY = velY;
        isThrown = true;
    }

    public void reset() {
        x = 0;
        y = screenHeight - image.getHeight();
        velX = 0;
        velY = 0;
        isThrown = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Bitmap getImage() {
        return image;
    }

    public boolean isThrown() {
        return isThrown;
    }

    public int getVelocityY() {
        return (int) velY;
    }
}