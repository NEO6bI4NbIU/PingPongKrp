package com.example.pong;

import javafx.scene.shape.Shape;

import java.util.List;

public class CardCollisionHandler {
    public CardTarget checkCardHit(Ball ball, List<CardTarget> cardTargets) {
        for (CardTarget target : cardTargets) {
            if (target.isActive() && collides(ball.getCircle(), target.getShape())) {
                return target;
            }
        }
        return null;
    }

    private boolean collides(Shape a, Shape b) {
        return a.intersects(b.getBoundsInParent());
    }
}
