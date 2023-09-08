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
        // Placeholder: Replace with real collision detection
        if (basketball.getX() > hoop.getX() && basketball.getX() < hoop.getX() + 50 &&
                basketball.getY() > hoop.getY() && basketball.getY() < hoop.getY() + 50) {
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

