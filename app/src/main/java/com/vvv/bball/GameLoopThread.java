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
        long startTime;
        long timeMillis;
        long waitTime;
        long targetTime = 1000 / 60;

        while (running) {
            Canvas canvas = null;
            startTime = System.nanoTime();

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

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if (waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (InterruptedException e) {
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
