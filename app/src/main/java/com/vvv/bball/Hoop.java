package com.vvv.bball;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Hoop extends GameObject {

    private float speedX;
    private final int screenWidth;
    private int screenHeight;

    public Hoop(float x, float y, Bitmap image, float speedX, int screenWidth) {
        super(x, y, image);
        this.speedX = speedX;
        this.screenWidth = screenWidth;
    }

    public void update() {
        x += speedX;

        if (x < 0 || x > screenWidth - image.getWidth()) {
            speedX = -speedX;
        }
    }

    public int getWidth() {
        return image.getWidth();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

}