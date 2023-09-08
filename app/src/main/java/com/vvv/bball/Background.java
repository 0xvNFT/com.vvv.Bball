package com.vvv.bball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class Background implements GameObject {
    private Bitmap bitmap;
    private int screenW, screenH;

    public Background(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_bg);
    }

    public void setScreenSize(int screenW, int screenH) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.bitmap = Bitmap.createScaledBitmap(this.bitmap, screenW, screenH, false);
    }

    @Override
    public void draw(Canvas canvas) {
        if (canvas != null && bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
