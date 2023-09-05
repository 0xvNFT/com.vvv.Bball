package com.vvv.bball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    private void render() {
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
        return true;
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        render();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
