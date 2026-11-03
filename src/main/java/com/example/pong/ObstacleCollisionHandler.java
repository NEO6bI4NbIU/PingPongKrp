package com.example.pong;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.List;

public class ObstacleCollisionHandler {

    public void checkObstacles(Ball ball, List<Shape> obstacles) {
        for (Shape obstacle : obstacles) {
            if (obstacle instanceof Circle circle) {
                if (collidesCircle(ball, circle)) {
                    handleCircleHit(ball, circle);
                }
            } else if (obstacle instanceof Rectangle rect) {
                if (collidesRectangleCircle(rect, ball.getCircle())) {
                    handlePaddleHit(ball, rect);
                }
            }
        }
    }

    private boolean collidesCircle(Ball ball, Circle circle) {
        double dx = ball.getCenterX() - circle.getCenterX();
        double dy = ball.getCenterY() - circle.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < ball.getRadius() + circle.getRadius();
    }

    private void handleCircleHit(Ball ball, Circle circle) {
        double dx = ball.getCenterX() - circle.getCenterX();
        double dy = ball.getCenterY() - circle.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 0.001) return;

        double nx = dx / distance;  // нормаль от центра круга к мячу
        double ny = dy / distance;

        ball.reflect(nx, ny);

        double newX = circle.getCenterX() + nx * (circle.getRadius() + ball.getRadius());
        double newY = circle.getCenterY() + ny * (circle.getRadius() + ball.getRadius());
        ball.setCenterX(newX);
        ball.setCenterY(newY);
    }

    private boolean collidesRectangleCircle(Rectangle rect, Circle circle) {
        double closestX = Math.max(rect.getX(), Math.min(circle.getCenterX(), rect.getX() + rect.getWidth()));
        double closestY = Math.max(rect.getY(), Math.min(circle.getCenterY(), rect.getY() + rect.getHeight()));
        double dx = circle.getCenterX() - closestX;
        double dy = circle.getCenterY() - closestY;
        return dx * dx + dy * dy < circle.getRadius() * circle.getRadius();
    }

    private void handlePaddleHit(Ball ball, Rectangle paddle) {
        double closestX = Math.max(paddle.getX(), Math.min(ball.getCenterX(), paddle.getX() + paddle.getWidth()));
        double closestY = Math.max(paddle.getY(), Math.min(ball.getCenterY(), paddle.getY() + paddle.getHeight()));

        double dx = ball.getCenterX() - closestX;
        double dy = ball.getCenterY() - closestY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 0.001) return;

        double nx = dx / distance;
        double ny = dy / distance;

        ball.reflect(nx, ny);

        double newX = closestX + nx * ball.getRadius();
        double newY = closestY + ny * ball.getRadius();
        ball.setCenterX(newX);
        ball.setCenterY(newY);
    }
}