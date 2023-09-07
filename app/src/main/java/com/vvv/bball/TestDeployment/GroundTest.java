//package com.vvv.bball.TestDeployment;
//
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//
//import com.vvv.bball.R;
//
//public class GroundTest {
//    Bitmap groundBitmap;
//    short x, y;
//    short width, height;
//    float ratioPXtoM;
//
//    public GroundTest(Resources res, int screenWidth, int screenHeight) {
//
//        ratioPXtoM = screenWidth / 14f;
//
//        width = (short) screenWidth;
//
//        height = (short) (0.4 * ratioPXtoM);
//
//        x = 0;
//        y = (short) (screenHeight - height);
//
//        groundBitmap = BitmapFactory.decodeResource(res, R.drawable.ground);
//        groundBitmap = Bitmap.createScaledBitmap(groundBitmap, screenWidth, screenHeight, false);
//    }
//
//}
