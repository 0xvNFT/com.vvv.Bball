package com.vvv.bball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoopThread gameLoopThread;
    private final Basketball basketball;
    private final Hoop hoop;
    private final Background background;
    private int screenW;
    private int screenH;
    private int score;
    private float initialTouchX, initialTouchY;
    private final Paint paint = new Paint();
    private boolean hasScored = false;


    public GameSurface(Context context) {
        super(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;

        gameLoopThread = new GameLoopThread(getHolder(), this);

        basketball = new Basketball(context, screenW, screenH);
        hoop = new Hoop(context, screenW, screenH);

        background = new Background(context);

        score = 0;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (gameLoopThread.getState() == Thread.State.NEW) {
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        } else if (gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(holder, this);
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.screenW = width;
        this.screenH = height;
        this.background.setScreenSize(screenW, screenH);

        float hoopX = screenW - hoop.getWidth() - 10;
        float hoopY = 300;

        hoop.setPosition(hoopX, hoopY);

        float basketballX = 100;
        float basketballY = screenH - basketball.getHeight() - 300;

        basketball.setX(basketballX);
        basketball.setY(basketballY);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameLoopThread.terminate();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        background.draw(canvas);
        basketball.draw(canvas);
        hoop.draw(canvas);
        debugDraw(canvas);
    }

    private void debugDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawLine(0, 0, screenW, 0, paint); // Top edge
        canvas.drawLine(screenW, 0, screenW, screenH, paint); // Right edge
        canvas.drawLine(0, screenH, screenW, screenH, paint); // Bottom edge
        canvas.drawLine(0, 0, 0, screenH, paint); // Left edge
        Log.d("ScreenDimensions", "Width: " + screenW + ", Height: " + screenH);

    }

    public void update() {
        basketball.update();
        hoop.update();
        checkForScoring();

        if (CollisionDetector.checkCollision(basketball, hoop)) {
            if (!hasScored) {
                score++;
                hasScored = true;
            }
        } else {
            hasScored = false;
        }
    }

    public void checkForScoring() {
        // More accurate collision logic
        float ballCenterX = basketball.getX() + (float) basketball.getWidth() / 2;
        float ballCenterY = basketball.getY() + (float) basketball.getHeight() / 2;

        float hoopCenterX = hoop.getX() + (float) hoop.getWidth() / 2;
        float hoopCenterY = hoop.getY() + (float) hoop.getHeight() / 2;

        float distance = (float) Math.sqrt(Math.pow(ballCenterX - hoopCenterX, 2) + Math.pow(ballCenterY - hoopCenterY, 2));

        if (distance < (float) basketball.getWidth() / 2 + (float) hoop.getWidth() / 2) {
            score++;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialTouchX = event.getX();
                initialTouchY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float dx = (event.getX() - initialTouchX) / 8;
                float dy = (event.getY() - initialTouchY) / 8;
                basketball.setVelocity(dx, dy);
                break;
        }
        return true;
    }

    public int getScore() {
        return score;
    }
    public GameLoopThread getGameLoopThread() {
        return gameLoopThread;
    }
}
