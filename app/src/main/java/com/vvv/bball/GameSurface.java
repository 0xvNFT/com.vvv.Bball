package com.vvv.bball;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoopThread gameLoopThread;
    private final GameManager gameManager;

    private final Basketball basketball;
    private final Hoop hoop;
    private final Background background;
    private int screenW;
    private int screenH;

    public GameSurface(Context context) {
        super(context);
        gameManager = new GameManager(context);
        getHolder().addCallback(this);
        gameLoopThread = new GameLoopThread(getHolder(), this, gameManager);
        basketball = new Basketball(context, 50, 50);
        hoop = new Hoop(context, 200, 100);
        background = new Background(context);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (gameLoopThread.getState() == Thread.State.NEW) {
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        } else if (gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(holder, this, gameManager);
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }
    }


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.screenW = width;
        this.screenH = height;
        this.background.setScreenSize(screenW, screenH);
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
    }

    public GameLoopThread getGameLoopThread() {
        return gameLoopThread;
    }
}
