package com.vvv.bball;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Ground {
    Bitmap groundBitmap;
    short x, y;
    short width, height;
    float ratioPXtoM;

    public Ground(Resources res, int screenWidth, int screenHeight) {

        ratioPXtoM = screenWidth / 14f;

        width = (short) screenWidth;

        height = (short) (0.4 * ratioPXtoM); // discussion: screen ratios #21

        x = 0;
        y = (short) (screenHeight - height);

        groundBitmap = BitmapFactory.decodeResource(res, R.drawable.ground);
        groundBitmap = Bitmap.createScaledBitmap(groundBitmap, screenWidth, screenHeight, false);
    }

}
