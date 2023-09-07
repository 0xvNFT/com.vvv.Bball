package com.vvv.bball;

public class GameThread extends Thread {
    private static final long FRAME_RATE = 16; // 60 FPS (1000 milliseconds / 60 frames)

    private final GameView gameView;
    private boolean isRunning = false;

    public GameThread(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();

            synchronized (gameView.getHolder()) {
                gameView.update();
                gameView.render();
                gameView.invalidate();
            }

            long endTime = System.currentTimeMillis();
            long frameTime = endTime - startTime;

            // Add a delay to achieve the desired frame rate
            if (frameTime < FRAME_RATE) {
                try {
                    Thread.sleep(FRAME_RATE - frameTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startThread() {
        isRunning = true;
        start();
    }

    public void stopThread() {
        isRunning = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
