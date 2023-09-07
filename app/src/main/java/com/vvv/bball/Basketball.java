package com.vvv.bball;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Basketball extends GameObject {
    protected final Bitmap basketballBitmap;
    private final int speed;
    private float velocityX, velocityY;
    private boolean isThrow = false;

    public Basketball(Bitmap basketballBitmap, int speed) {
        super(basketballBitmap, 0, 0);
        this.basketballBitmap = basketballBitmap;
        this.speed = speed;

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        int basketballWidth = basketballBitmap.getWidth();
        int basketballHeight = basketballBitmap.getHeight();

        x = (screenWidth - basketballWidth) / 2.0f;
        y = screenHeight - basketballHeight - 50;
    }

    @Override
    public void draw(Canvas canvas) {
        if (basketballBitmap != null) {
            canvas.drawBitmap(basketballBitmap, x, y, null);
        }
    }

    @Override
    public void update() {
        if (isThrow) {
            x += velocityX;
            y += velocityY;
        }
    }

    public boolean isTouched(float x, float y) {
        return x > this.x && x < this.x + basketballBitmap.getWidth()
                && y > this.y && y < this.y + basketballBitmap.getHeight();
    }

    public void isThrown(float startX, float startY, float endX, float endY) {
        if (!isThrow) {
            // Log touch coordinates
            Log.d("Launch", "StartX: " + startX + ", StartY: " + startY + ", EndX: " + endX + ", EndY: " + endY);

            // Calculate the velocity components based on the touch release point
            float distanceX = endX - startX;
            float distanceY = endY - startY;
            float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            // Calculate velocity components
            velocityX = (distanceX / distance) * speed;
            velocityY = (distanceY / distance) * speed;

            // Log velocity components
            Log.d("Launch", "VelocityX: " + velocityX + ", VelocityY: " + velocityY);

            isThrow = true;
        }
    }
}


