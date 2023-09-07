package com.vvv.bball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final SurfaceHolder surfaceHolder;
    private final boolean isRunning = false;
    private Basketball basketball;
    private BasketballRing basketballRing;
    private Bitmap gameBackground;
    private float scaleX, scaleY;
    private GameThread gameThread;
    private boolean isBasketballTouched = false;
    private float touchX, touchY;
    private float launchStartX, launchStartY;

    public GameView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        initializeResources(context);

    }

    private void initializeResources(Context context) {
        gameBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_bg);
        basketball = new Basketball(BitmapFactory.decodeResource(getResources(), R.drawable.basketball), 10);
        basketballRing = new BasketballRing(BitmapFactory.decodeResource(getResources(), R.drawable.basketball_ring), 10);
    }

    public void render() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            if (gameBackground != null) {
                scaleX = (float) canvas.getWidth() / gameBackground.getWidth();
                scaleY = (float) canvas.getHeight() / gameBackground.getHeight();

                Bitmap scaledBackground = Bitmap.createScaledBitmap(gameBackground, canvas.getWidth(), canvas.getHeight(), true);

                canvas.drawBitmap(scaledBackground, scaleX, scaleY, null);

            }
            if (basketballRing != null) {
                basketballRing.draw(canvas);
            }

            if (isBasketballTouched) {
                // Draw the trajectory path as a dotted line
                Paint pathPaint = new Paint();
                pathPaint.setColor(Color.WHITE);
                pathPaint.setStyle(Paint.Style.STROKE);
                pathPaint.setStrokeWidth(2);
                pathPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));

                Path trajectoryPath = new Path();
                trajectoryPath.moveTo(launchStartX, launchStartY);
                // Calculate and add points to represent the trajectory (you can use physics equations)
                // For a simple example, you can just add a line from launch start to touch point:
                trajectoryPath.lineTo(touchX, touchY);

                canvas.drawPath(trajectoryPath, pathPaint);
            }

            // Draw the basketball last
            if (basketball != null) {
                basketball.draw(canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Check if the touch is within the basketball's bounds
                if (basketball.isTouched(x, y)) {
                    isBasketballTouched = true;
                    touchX = x;
                    touchY = y;
                    launchStartX = x; // Record the launch start point
                    launchStartY = y;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // Update touch coordinates if the basketball is still being touched
                if (isBasketballTouched) {
                    touchX = x;
                    touchY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                isBasketballTouched = false;
                basketball.launch(launchStartX, launchStartY, x, y);

                break;
        }

        return true;
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameThread = new GameThread(this);
        gameThread.startThread();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        if (gameThread != null) {
            gameThread.stopThread();
        }
    }

    public void update() {
        basketball.update();
        basketballRing.update();
    }
}
