package com.example.pong;

import javafx.scene.shape.Shape;

public class MovingObstacle {
    private final Shape shape;
    private final double axis;       // 0 = X, 1 = Y, движение либо по X, либо по Y
    private final double amplitude;
    private final double speed;
    private final double baseX, baseY;
    private double time = 0;
    private final double fieldWidth, fieldHeight;

    public MovingObstacle(Shape shape, double axis, double amplitude, double speed, double fieldWidth, double fieldHeight) {
        this.shape = shape;
        this.axis = axis;
        this.amplitude = amplitude;
        this.speed = speed;
        this.baseX = shape.getLayoutX();
        this.baseY = shape.getLayoutY();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    public void update(double deltaTime) {
        time += deltaTime;
        double offset = Math.sin(time * speed) * amplitude;
        if (axis == 0) {
            double newX = baseX + offset;
            newX = Math.max(0, Math.min(newX, fieldWidth - shape.getBoundsInLocal().getWidth()));
            shape.setLayoutX(newX);
        } else {
            double newY = baseY + offset;
            newY = Math.max(0, Math.min(newY, fieldHeight - shape.getBoundsInLocal().getHeight()));
            shape.setLayoutY(newY);
        }
    }

    public void reset() {
        time = 0;
        shape.setLayoutX(baseX);
        shape.setLayoutY(baseY);
    }

    public Shape getShape() { return shape; }
}
