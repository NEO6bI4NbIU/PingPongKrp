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
    private double zoneMultiplier = 1.0;

    public Ball(double startX, double startY) {
        this.circle = new Circle(startX, startY, RADIUS, COLOR);
        this.dx = 0;
        this.dy = 0;
    }

    public Circle getCircle() {
        return circle;
    }

    public void move(double deltaTime) {
        circle.setCenterX(circle.getCenterX() + dx * speedMultiplier * zoneMultiplier * deltaTime * 60);
        circle.setCenterY(circle.getCenterY() + dy * speedMultiplier * zoneMultiplier * deltaTime * 60);
    }

    public void speedUp() {
        speedMultiplier = Math.min(speedMultiplier + 0.1, MAX_MULTIPLIER);
    }

    public void resetSpeed() {
        speedMultiplier = 1.0;
    }

    public void setZoneMultiplier(double multiplier) {
        this.zoneMultiplier = multiplier;
    }

    public void bounceX() {dx = -dx;}
    public void bounceY() {dy = -dy;}
    public void reflect(double nx, double ny) {  // отскок от "сложных"(круги, треугольники) поверхностей
        double dot = dx * nx + dy * ny;
        if (dot < 0) {  // мяч летит в сторону поверхности
            dx = dx - 2 * dot * nx;
            dy = dy - 2 * dot * ny;
        }
    }
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
    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }
}