package com.vvv.bball;

public class CollisionDetector {

    public static boolean checkCollision(Basketball basketball, Hoop hoop) {
        float ballCenterX = basketball.getX() + basketball.getRadius();
        float ballCenterY = basketball.getY() + basketball.getRadius();

        float hoopCenterX = hoop.getX() + hoop.getWidth() / 2.0f;
        float hoopCenterY = hoop.getY() + hoop.getHeight() / 2.0f;

        float dx = ballCenterX - hoopCenterX;
        float dy = ballCenterY - hoopCenterY;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < basketball.getRadius() + hoop.getWidth() / 2.0f;  // Assuming hoop's width roughly matches its circular shape
    }

}
