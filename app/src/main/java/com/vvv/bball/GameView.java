package com.vvv.bball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final SurfaceHolder surfaceHolder;
    private Basketball basketball;
    private BasketballRing basketballRing;
    private Bitmap gameBackground;
    private float scaleX, scaleY;
    private final BasketballThread basketballThread;
    private PointF touchStart;
    private long touchStartTime;
    private boolean isSwiping = false;

    public GameView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        basketballThread = new BasketballThread(this);
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
            if (basketball != null) {
                basketball.draw(canvas);
            }
            if (basketballRing != null) {
                basketballRing.draw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        basketball.update();
        basketballRing.update();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Store the initial touch coordinates and time
                touchStart = new PointF(event.getX(), event.getY());
                touchStartTime = System.currentTimeMillis();
                isSwiping = true;
                break;
            case MotionEvent.ACTION_UP:
                if (isSwiping) {
                    long touchEndTime = System.currentTimeMillis();
                    float touchDuration = touchEndTime - touchStartTime;
                    float deltaX = event.getX() - touchStart.x;
                    float deltaY = event.getY() - touchStart.y;

                    // Calculate the speed of the swipe (pixels per millisecond)
                    float speed = (float) (Math.sqrt(deltaX * deltaX + deltaY * deltaY) / touchDuration);

                    // Apply a force to the basketball based on the swipe speed and direction
                    float forceX = deltaX * speed;
                    float forceY = deltaY * speed;

                    // Apply the force to the basketball object
                    basketball.applyForce(forceX, forceY);

                    isSwiping = false; // Reset swipe flag
                }
                break;
        }
        return true;
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        basketballThread.startThread();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        basketballThread.stopThread();
    }
}
