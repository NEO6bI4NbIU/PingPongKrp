package com.example.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CardTarget {
    private final Circle shape;
    private final CardType type;
    private boolean active = true;

    public CardTarget(CardType type, double x, double y) {
        this.type = type;
        this.shape = new Circle(x, y,55,  Color.GOLD);
    }


    public Circle getShape() { return shape; }
    public CardType getType() { return type; }
    public boolean isActive() { return active; }
}
