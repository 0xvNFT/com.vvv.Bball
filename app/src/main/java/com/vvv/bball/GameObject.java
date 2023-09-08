package com.vvv.bball;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface GameObject {
    void draw(Canvas canvas);

    void update();

    void onTouchEvent(MotionEvent event);
}
