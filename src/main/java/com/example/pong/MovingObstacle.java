package com.example.pong;

import javafx.scene.shape.Rectangle;

public class MovingObstacle {
    private final Rectangle rectangle;
    private final double axis;       // 0 = X, 1 = Y, движение либо по X, либо по Y
    private final double amplitude;
    private final double speed;
    private final double baseX, baseY;
    private double time = 0;
    private final double fieldWidth, fieldHeight;

    public MovingObstacle(Rectangle rectangle, double axis, double amplitude, double speed, double fieldWidth, double fieldHeight) {
        this.rectangle = rectangle;
        this.axis = axis;
        this.amplitude = amplitude;
        this.speed = speed;
        this.baseX = rectangle.getX();
        this.baseY = rectangle.getY();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    public void update(double deltaTime) {
        time += deltaTime;
        double offset = Math.sin(time * speed) * amplitude;
        if (axis == 0) {
            double newX = baseX + offset;
            newX = Math.max(0, Math.min(newX, fieldWidth - rectangle.getWidth()));
            rectangle.setX(newX);;
        } else {
            double newY = baseY + offset;
            newY = Math.max(0, Math.min(newY, fieldHeight - rectangle.getHeight()));
            rectangle.setY(newY);
        }
    }

    public void reset() {
        time = 0;
        rectangle.setX(baseX);
        rectangle.setY(baseY);
    }

    public double getMinX() {
        return axis == 0 ? baseX - amplitude : baseX;
    }

    public double getMaxX() {
        return axis == 0 ? baseX + amplitude + rectangle.getWidth() : baseX + rectangle.getWidth();
    }

    public double getMinY() {
        return axis == 1 ? baseY - amplitude : baseY;
    }

    public double getMaxY() {
        return axis == 1 ? baseY + amplitude + rectangle.getHeight() : baseY + rectangle.getHeight();
    }

    public Rectangle getRectangle() { return rectangle; }

}
