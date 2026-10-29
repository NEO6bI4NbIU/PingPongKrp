package com.example.pong;

import javafx.scene.shape.Rectangle;

public class Zone {
    private final Rectangle area;
    private final double speedMultiplier;  // 1.5 - ускорение, 0.5 - замедление

    public Zone(Rectangle area, double speedMultiplier) {
        this.area = area;
        this.speedMultiplier = speedMultiplier;
    }

    public Rectangle getArea() { return area; }
    public double getSpeedMultiplier() { return speedMultiplier; }
}
