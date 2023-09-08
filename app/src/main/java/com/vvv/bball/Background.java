package com.vvv.bball;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background extends GameObject {

    public Background(float x, float y, Bitmap image) {
        super(x, y, image);
    }

    public void update() {
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}
