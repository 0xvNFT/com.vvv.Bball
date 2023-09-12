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

        return distance < basketball.getRadius() + hoop.getWidth() / 2.0f;
    }

    public static boolean checkCollisionWithHoopBottom(Basketball basketball, Hoop hoop) {
        float ballTopY = basketball.getY();

        if (ballTopY <= (hoop.getY() + hoop.getHeight()) && ballTopY >= hoop.getY()) {
            float ballLeftX = basketball.getX();
            float ballRightX = basketball.getX() + 2 * basketball.getRadius();

            return ballRightX >= hoop.getX() && ballLeftX <= (hoop.getX() + hoop.getWidth());
        }
        return false;
    }

    public static boolean checkCollisionWithHoopTop(Basketball basketball, Hoop hoop) {
        float ballBottomY = basketball.getY() + 2 * basketball.getRadius();

        if (ballBottomY <= (hoop.getY() + hoop.getHeight()) && ballBottomY >= hoop.getY()) {
            float ballLeftX = basketball.getX();
            float ballRightX = basketball.getX() + 2 * basketball.getRadius();

            return ballRightX >= hoop.getX() && ballLeftX <= (hoop.getX() + hoop.getWidth());
        }
        return false;
    }

    public static boolean checkCollisionWithHoopRightSide(Basketball basketball, Hoop hoop) {
        float ballRightX = basketball.getX() + 2 * basketball.getRadius();
        float ballTopY = basketball.getY();
        float ballBottomY = basketball.getY() + 2 * basketball.getRadius();

        boolean collisionWithRight = ballRightX >= (hoop.getX() + hoop.getWidth()) && ballRightX <= (hoop.getX() + hoop.getWidth());

        boolean withinYBounds = ballBottomY >= hoop.getY() && ballTopY <= (hoop.getY() + hoop.getHeight());

        if (collisionWithRight && withinYBounds) {
            basketball.setVelocity(-Math.abs(basketball.getVelocityX()), basketball.getYVelocity());
            return true;
        }
        return false;
    }

    public static boolean checkCollisionWithHoopLeftSide(Basketball basketball, Hoop hoop) {
        float ballLeftX = basketball.getX();
        float ballTopY = basketball.getY();
        float ballBottomY = basketball.getY() + 2 * basketball.getRadius();

        boolean collisionWithLeft = ballLeftX <= hoop.getX() && ballLeftX >= (hoop.getX() - 2 * basketball.getRadius());
        boolean withinYBounds = ballBottomY >= hoop.getY() && ballTopY <= (hoop.getY() + hoop.getHeight());

        if (collisionWithLeft && withinYBounds) {
            basketball.setVelocity(Math.abs(basketball.getVelocityX()), basketball.getYVelocity());
            basketball.setX(hoop.getX() + 1);
            return true;
        }
        return false;
    }

    public static boolean checkCollisionWithHoopCorners(Basketball basketball, Hoop hoop) {
        float ballCenterX = basketball.getX() + basketball.getRadius();
        float ballCenterY = basketball.getY() + basketball.getRadius();

        float topLeftX = hoop.getX();
        float topLeftY = hoop.getY();

        float topRightX = hoop.getX() + hoop.getWidth();
        float topRightY = hoop.getY();

        boolean collideTopLeft = isCollidingWithCorner(ballCenterX, ballCenterY, topLeftX, topLeftY, basketball.getRadius());
        boolean collideTopRight = isCollidingWithCorner(ballCenterX, ballCenterY, topRightX, topRightY, basketball.getRadius());

        if (collideTopLeft || collideTopRight) {
            basketball.setX(basketball.getX() - 5);
            basketball.setY(basketball.getY() - 5);
            basketball.setVelocity(-Math.abs(basketball.getVelocityX()), -Math.abs(basketball.getYVelocity()));
            return true;
        }

        return false;
    }

    private static boolean isCollidingWithCorner(float ballCenterX, float ballCenterY, float cornerX, float cornerY, float radius) {
        float dx = ballCenterX - cornerX;
        float dy = ballCenterY - cornerY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < radius;
    }

}
