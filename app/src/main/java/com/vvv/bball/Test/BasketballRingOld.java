//package com.vvv.bball.Test;
//
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//
//public class BasketballRingOld extends GameObjectOld {
//    protected final Bitmap basketballRingBitmap;
//    private final int speed;
//
//    public BasketballRingOld(Bitmap basketballRingBitmap, int speed) {
//        super(basketballRingBitmap, 0, 0);
//        this.basketballRingBitmap = basketballRingBitmap;
//        this.speed = speed;
//
//        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
//
//        int basketballRingWidth = basketballRingBitmap.getWidth();
//        int basketballRingHeight = basketballRingBitmap.getHeight();
//
//        x = screenWidth - basketballRingWidth;
//        y = 350;
//    }
//
//    @Override
//    public void draw(Canvas canvas) {
//        if (basketballRingBitmap != null) {
//            canvas.drawBitmap(basketballRingBitmap, x, y, null);
//        }
//    }
//
//    @Override
//    public void update() {
//    }
//}
//
//
