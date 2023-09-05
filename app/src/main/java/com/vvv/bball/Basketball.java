package com.vvv.bball;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Basketball extends GameObject {
    protected final Bitmap basketballBitmap;
    private final int speed;

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
    }
}


