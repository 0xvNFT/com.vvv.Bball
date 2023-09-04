package com.vvv.bball;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BasketballRing extends GameObject {
    private final Bitmap bitmap;

    public BasketballRing(Bitmap bitmap, int x, int y) {
        super(x, y);
        this.bitmap = bitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    @Override
    public void update() {
        // Implement your update logic for the basketball ring, if needed
    }
}


