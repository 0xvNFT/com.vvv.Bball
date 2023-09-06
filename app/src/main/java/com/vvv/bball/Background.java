package com.vvv.bball;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    Bitmap backgroundBitmap, developerBitmap;

    public Background(Resources res, int screenWidth, int screenHeight) {

        backgroundBitmap = BitmapFactory.decodeResource(res, R.drawable.game_bg);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, screenWidth, screenHeight, false);

//        developerBitmap = BitmapFactory.decodeResource(res, R.drawable.developer);
//        developerBitmap = Bitmap.createScaledBitmap(developerBitmap, screenWidth, screenHeight, false);

    }

}
