package com.vvv.bball;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private final GameActivity activity;
    private final short screenWidth, screenHeight;
    private final Context gameActivityContext;
    float SLEEP_MILLIS;
    private boolean isPlaying;
    private final Basketball basketball;
    private final Background background;
    //private final Ground ground;
    private final Basket basket;
    private Thread thread;
    private final float game_time;
    private byte quarterOfThrow;

    public GameView(GameActivity activity, short screenWidth, short screenHeight) {

        super(activity);
        this.activity = activity;
        gameActivityContext = activity;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        isPlaying = true;

        background = new Background(activity.getResources(), screenWidth, screenHeight);
        basketball = new Basketball(activity.getResources(), screenWidth, screenHeight);
        basket = new Basket(activity.getResources(), screenWidth, screenHeight);
        //ground = new Ground(activity.getResources(), screenWidth + 100, screenHeight);

        game_time = 0;
        quarterOfThrow = 0;
        basketball.thrown = false;

    }

    @Override
    public void run() {
        while (isPlaying) {
            sleep();
            update();
            draw();
        }

    }

    public void update() {

        if (basketball.thrown) {
            basketball.prevX = basketball.updatedX;
            basketball.prevY = basketball.updatedY;

            //physicsUpdateNoCol();
        } else if (basketball.isTouched)
            quarterOfThrow = basketball.quarter;

        basketball.dotArrayListX.add(basketball.prevX + fixX());
        basketball.dotArrayListY.add(basketball.prevY + fixY());

    }

//    public void physicsUpdateNoCol() {
//
//        if (basketball.isTouched) {
//            basketball.prevX = basketball.updatedX;
//            basketball.prevY = basketball.updatedY;
//        }
//    }

    public void draw() {

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            //canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);

            canvas.drawBitmap(background.backgroundBitmap, 0, 0, null);
//            canvas.drawBitmap(background.developerBitmap, 0, 0, null);


            if (basketball.time > 0.032)
                for (short i = 0; i < basketball.dotArrayListX.size() - 1; i++)
                    try {
                        canvas.drawLine(basketball.dotArrayListX.get(i), basketball.dotArrayListY.get(i), basketball.dotArrayListX.get(i + 1), basketball.dotArrayListY.get(i + 1), null);
                    } catch (Exception ignored) {

                    }

            canvas.drawBitmap(basketball.basketballBitmap, basketball.updatedX, basketball.updatedY, null);
            //canvas.drawBitmap(ground.groundBitmap, ground.x, ground.y, null);

            if (!basketball.thrown) {
                basketball.v = 0;
                basketball.vx = 0;
                basketball.vy = 0;
            }
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void sleep() {

        SLEEP_MILLIS = 1000 / 120f;

        try {
            Thread.sleep((long) (SLEEP_MILLIS / 2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPlaying = false;
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }

        return super.onTouchEvent(event);
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
            Thread.sleep(100);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public float fixX() {
        return basketball.basketballWidth / 2f;
    }

    public float fixY() {
        return basketball.basketballHeight / 2f;
    }
}
