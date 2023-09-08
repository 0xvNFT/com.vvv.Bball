package com.vvv.bball;

import android.content.Context;
import android.graphics.Canvas;

public class GameManager {
    private final Basketball basketball;
    private final Hoop hoop;
    private int score;

    public GameManager(Context context) {
        basketball = new Basketball(context, 50, 50);
        hoop = new Hoop(context, 200, 100);
        score = 0;
    }

    public void update() {

        basketball.update();
        hoop.update();
//        basketball.setX(basketball.getX() + 1);
//        basketball.setY(basketball.getY() - 1);

        checkForScoring();
    }

    public void checkForScoring() {
        // More accurate collision logic
        float ballCenterX = basketball.getX() + (float) basketball.getWidth() / 2;
        float ballCenterY = basketball.getY() + (float) basketball.getHeight() / 2;

        float hoopCenterX = hoop.getX() + (float) hoop.getWidth() / 2;
        float hoopCenterY = hoop.getY() + (float) hoop.getHeight() / 2;

        float distance = (float) Math.sqrt(Math.pow(ballCenterX - hoopCenterX, 2) + Math.pow(ballCenterY - hoopCenterY, 2));

        if (distance < (float) basketball.getWidth() / 2 + (float) hoop.getWidth() / 2) {
            score++;
        }
    }


    public void draw(Canvas canvas) {
        basketball.draw(canvas);
        hoop.draw(canvas);
    }

    public int getScore() {
        return score;
    }
}

