package com.vvv.bball;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject {
    protected Bitmap bitmap;
    protected float x, y;

    public GameObject(Bitmap bitmap, float x, float y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();
}

