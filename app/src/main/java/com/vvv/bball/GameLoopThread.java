package com.vvv.bball;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread {
    private final GameSurface gameSurface;
    private final SurfaceHolder surfaceHolder;
    private final GameManager gameManager;
    private boolean running = false;

    public GameLoopThread(SurfaceHolder surfaceHolder, GameSurface gameSurface, GameManager gameManager) {
        this.surfaceHolder = surfaceHolder;
        this.gameSurface = gameSurface;
        this.gameManager = gameManager;

    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void terminate() {
        running = false;
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;
            try {
                if (surfaceHolder.getSurface().isValid()) {
                    canvas = this.surfaceHolder.lockCanvas();
                }
                synchronized (surfaceHolder) {
                    if (canvas != null) {
                        gameManager.update();
                        this.gameSurface.draw(canvas);
                    }
                }
            } finally {
                if (canvas != null) {
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void pauseGame() {
        this.running = false;
    }

    public void resumeGame() {
        this.running = true;
        this.start();
    }
}
