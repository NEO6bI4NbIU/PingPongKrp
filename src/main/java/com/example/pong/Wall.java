package com.example.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall{ // создание стены
    private static final double HEIGHT = 10;
    private static final Color COLOR = Color.SILVER;

    private final Rectangle rectangle;

    public Wall(double positionY, double windowWidth) {                // создаёт стену на высоте Y по заданной ширине экрана
        this.rectangle = new Rectangle(windowWidth, HEIGHT, COLOR);
        this.rectangle.setX(0);
        this.rectangle.setY(positionY);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public static double getHeight() {
        return HEIGHT;
    }
}
