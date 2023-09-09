package com.vvv.bball;

public class CollisionDetector {

    public static boolean checkCollision(Basketball basketball, Hoop hoop) {
        float ballCenterX = basketball.getX() + basketball.getRadius();
        float ballCenterY = basketball.getY() + basketball.getRadius();

        // Assuming hoop's x, y coordinates are the top-left corner of the rectangle
        return ballCenterX >= hoop.getX() && ballCenterX <= hoop.getX() + hoop.getWidth() &&
                ballCenterY >= hoop.getY() && ballCenterY <= hoop.getY() + hoop.getHeight();
    }

}
