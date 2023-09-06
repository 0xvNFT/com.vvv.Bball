package com.vvv.bball;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Basketball {
    final float ratioMtoPX;
    final float MAX_VELOCITY;
    public float prevX, prevY, updatedX, updatedY, initialX, initialY, originalX, originalY;
    public boolean thrown;
    public short basketballWidth, basketballHeight;
    public boolean isTouched = false;
    Bitmap basketballBitmap;
    float percentOfPull;
    short maxBallPull;
    byte quarter;
    float time;
    float GRAVITY;
    float removeBall_time;
    float v, vx, vy, v0y;
    byte collision;
    byte howManyCols;
    byte floorHitCount;
    ArrayList<Float> dotArrayListX;
    ArrayList<Float> dotArrayListY;
    private final float screenWidth;
    private final float screenHeight;

    public Basketball(Resources res, float screenWidth, float screenHeight) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        ratioMtoPX = screenWidth / 14f;

        updatedX = (int) (screenWidth - 2 * ratioMtoPX);
        updatedY = (int) (screenHeight - 2 * ratioMtoPX);

        prevX = updatedX;
        prevY = updatedY;

        basketballWidth = (short) (2 * ratioMtoPX);
        basketballHeight = (short) (2 * ratioMtoPX);

        initialX = updatedX + basketballWidth / 2f;
        initialY = updatedY + basketballHeight / 2f;

        originalX = initialX;
        originalY = initialY;

        basketballBitmap = BitmapFactory.decodeResource(res, R.drawable.basketball);
        basketballBitmap = Bitmap.createScaledBitmap(basketballBitmap, basketballWidth, basketballHeight, false);

        GRAVITY = 9.8f * 6.3f * ratioMtoPX;
        MAX_VELOCITY = 21 * 1.4f * ratioMtoPX;
        time = 0;

        howManyCols = 0;
        floorHitCount = 0;
        removeBall_time = 0;

        collision = 0;
        percentOfPull = 0;

        dotArrayListX = new ArrayList<>();
        dotArrayListY = new ArrayList<>();

    }
}
