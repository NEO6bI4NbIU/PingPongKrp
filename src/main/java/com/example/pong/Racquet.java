package com.example.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Racquet { // создание ракетки
    public static final double DEFAULT_WIDTH = 10;
    public static final double DEFAULT_HEIGHT = 70;
    private static final double MIN_HEIGHT = 30;
    private static final double MAX_HEIGHT = 150;
    private static final Color COLOR = Color.BLACK;
    private static final double MOVE_SPEED = 5.5;

    private final Rectangle rectangle;
    private final double minY;
    private final double maxY;
    private final double startY;
    private double height;

    public Racquet(double positionX, double positionY, double minY, double maxY) {
        this.height = DEFAULT_HEIGHT;
        this.rectangle = new Rectangle(DEFAULT_WIDTH, height, COLOR);
        this.rectangle.setX(positionX);
        this.rectangle.setY(positionY);
        this.minY = minY;
        this.maxY = maxY;
        this.startY = positionY;
    }

    public void reset() {
        rectangle.setY(startY);
        height = DEFAULT_HEIGHT;
        rectangle.setHeight(DEFAULT_HEIGHT);
    }

    public Rectangle getRectangle() {return rectangle;}

    public void moveUp() {
        double newY = rectangle.getY() - MOVE_SPEED;
        rectangle.setY(Math.max(newY, minY));
    }

    public void moveDown() {
        double newY = rectangle.getY() + MOVE_SPEED;
        rectangle.setY(Math.min(newY, maxY - height));
    }

    public void setHeight(double newHeight) {
        newHeight = Math.max(MIN_HEIGHT, Math.min(MAX_HEIGHT, newHeight));

        double centerY = getCenterY(); // запоминаю центр ракетки, чтобы она "росла" от центра
        this.height = newHeight;
        rectangle.setHeight(newHeight);

        double newY = centerY - newHeight / 2;

        if (newY < minY) newY = minY;   // проверка выхода за границы
        if (newY + newHeight > maxY) newY = maxY - newHeight;

        rectangle.setY(newY);
    }


    public void increaseHeight(double delta) {setHeight(height + delta);}

    public void decreaseHeight(double delta) {setHeight(height - delta);}

    public void resetHeight() {setHeight(DEFAULT_HEIGHT);}

    public double getX() { return rectangle.getX(); }
    public double getY() { return rectangle.getY(); }
    public double getWidth() { return rectangle.getWidth(); }
    public double getHeight() { return height; } // <-- возвращает поле
    public double getCenterY() { return rectangle.getY() + height / 2; }
}