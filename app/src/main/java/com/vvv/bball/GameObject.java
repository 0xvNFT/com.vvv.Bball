package com.vvv.bball;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject {
    protected float x, y;
    protected Bitmap image;

    public GameObject(float x, float y, Bitmap image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public abstract void update();

    public abstract void draw(Canvas canvas);
}

