package com.vvv.bball;

public class BasketballThread implements Runnable {
    //private final Thread gameThread;
    private final GameView gameView;
    private final Object lock = new Object();
    private boolean isRunning = false;

    public BasketballThread(GameView gameView) {
        this.gameView = gameView;
        //gameThread = new Thread(this);
    }

    @Override
    public void run() {
        while (isRunning) {
            synchronized (lock) {
                gameView.render();
            }

            try {

                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startThread() {
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stopThread() {
        isRunning = false;
    }
}
