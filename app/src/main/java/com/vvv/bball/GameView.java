package com.vvv.bball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final Bitmap gameBackground, basketball, basketball_ring;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        gameBackground = BitmapFactory.decodeResource(getResources(), R.drawable.game_bg);
        basketball = BitmapFactory.decodeResource(getResources(), R.drawable.basketball);
        basketball_ring = BitmapFactory.decodeResource(getResources(), R.drawable.basketball_ring);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            if (gameBackground != null) {
                canvas.drawBitmap(gameBackground, 0, 0, null);
            }
            if (basketball != null) {
                canvas.drawBitmap(basketball, 0, 0, null);
            }
            if (basketball_ring != null) {
                canvas.drawBitmap(basketball_ring, 0, 0, null);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
