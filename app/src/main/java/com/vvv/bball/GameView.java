package com.vvv.bball;

import static com.vvv.bball.Constants.THROW_POWER_MULTIPLIER;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private final int screenWidth, screenHeight;
    private Background background;
    private Basketball basketball;
    private Hoop hoop;
    private boolean touchingBall;
    private float touchStartX;
    private float touchStartY;
    private float touchEndX;
    private float touchEndY;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Bitmap backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.game_bg);
        Bitmap basketballImg = BitmapFactory.decodeResource(getResources(), R.drawable.basketball);
        Bitmap hoopImg = BitmapFactory.decodeResource(getResources(), R.drawable.hoop);

        background = new Background(0, 0, backgroundImg);

        float basketballX = (float) (getWidth() - basketballImg.getWidth()) / 2;
        float basketballY = (getHeight() - basketballImg.getHeight() - 50);
        basketball = new Basketball(basketballX, basketballY, basketballImg, getHeight(), getWidth());

        hoop = new Hoop(400, 100, hoopImg, 10, getWidth());

        if (!gameThread.isRunning()) {
            gameThread.setRunning(true);
            gameThread.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            background.draw(canvas);
            basketball.draw(canvas);
            drawTrajectory(canvas);
            checkBallStopped();
            hoop.draw(canvas);
        }
    }

    public void update() {
        if (background != null) {
            background.update();
        }
        basketball.update();
        hoop.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float touchX = event.getX();
                float touchY = event.getY();

                // Check if the touch is within the basketball bounds
                if (touchX >= basketball.getX() && touchX <= basketball.getX() + basketball.getImage().getWidth() &&
                        touchY >= basketball.getY() && touchY <= basketball.getY() + basketball.getImage().getHeight()) {
                    touchingBall = true;
                    touchStartX = touchX;
                    touchStartY = touchY;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (touchingBall) {
                    touchEndX = event.getX();
                    touchEndY = event.getY();
                }
                break;

            case MotionEvent.ACTION_UP:
                if (touchingBall) {
                    touchingBall = false;

                    float velocityX = (touchStartX - touchEndX) * THROW_POWER_MULTIPLIER;
                    float velocityY = (touchStartY - touchEndY) * THROW_POWER_MULTIPLIER;
                    basketball.throwBall(velocityX, velocityY);
                }
                break;
        }

        return true;
    }

    private void drawTrajectory(Canvas canvas) {
        if (touchingBall) {
            // Set the paint for the trajectory
            Paint trajectoryPaint = new Paint();
            trajectoryPaint.setColor(Color.WHITE);
            trajectoryPaint.setStyle(Paint.Style.STROKE);
            trajectoryPaint.setStrokeWidth(5);
            trajectoryPaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));

            // Draw the trajectory path
            Path trajectoryPath = new Path();
            trajectoryPath.moveTo(touchStartX, touchStartY);
            trajectoryPath.lineTo(touchEndX, touchEndY);
            canvas.drawPath(trajectoryPath, trajectoryPaint);
        }
    }

    private void checkBallStopped() {
        if (basketball.isThrown() && Math.abs(basketball.getVelocityY()) < 1 && basketball.getY() > screenHeight * 3 / 4) {
            basketball.reset();
        }
    }

    public void pause() {
        gameThread.setRunning(false);
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

}