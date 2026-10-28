package com.example.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball { // создание мяча

    private static final double RADIUS = 10;
    private static final Color COLOR = Color.BLUE;
    private static final double BASE_SPEED = 3.0;
    private static final double MAX_MULTIPLIER = 5.0;

    private final Circle circle;
    private double dx;
    private double dy;
    private double speedMultiplier = 1.0;

    public Ball(double startX, double startY) {
        this.circle = new Circle(startX, startY, RADIUS, COLOR);
        this.dx = 0;
        this.dy = 0;
    }

    public Circle getCircle() {
        return circle;
    }

    public void move() {
        circle.setCenterX(circle.getCenterX() + dx * speedMultiplier);
        circle.setCenterY(circle.getCenterY() + dy * speedMultiplier);
    }

    public void speedUp() {
        speedMultiplier = Math.min(speedMultiplier + 0.1, MAX_MULTIPLIER);
    }

    public void resetSpeed() {
        speedMultiplier = 1.0;
    }

    public void bounceX() {dx = -dx;}
    public void bounceY() {dy = -dy;}
    public void setCenterX(double x) {circle.setCenterX(x);}
    public void setCenterY(double y) {circle.setCenterY(y);}

    public void serve(int directionX) {
        double minAngle = Math.toRadians(30);      // угол подачи от 30 до 50
        double maxAngle = Math.toRadians(50);
        double angle = minAngle + Math.random() * (maxAngle - minAngle);

        if (Math.random() < 0.5) {                // случайно вверх или вниз
            angle = -angle;
        }
        this.dx = BASE_SPEED * directionX * Math.cos(angle);
        this.dy = BASE_SPEED * Math.sin(angle);
        resetSpeed();
    }

    public void stop() {
        this.dx = 0;
        this.dy = 0;
    }

    public double getCenterX() { return circle.getCenterX(); }
    public double getCenterY() { return circle.getCenterY(); }
    public double getRadius() { return RADIUS; }
    public double getDx() { return dx; }
    public double getDy() { return dy; }
}