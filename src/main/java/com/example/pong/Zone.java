package com.example.pong;

import javafx.scene.shape.Rectangle;

public class Zone {
    private static final double BOOST_ZONE_FACTOR = 1.3;
    private static final double SLOW_ZONE_FACTOR = 0.75;
    private final Rectangle area;
    private final double speedFactor;
    private final boolean isPermanent; // изменениe скорости на постоянной основе - true, только внутри зоны - false

    public Zone(Rectangle area, double speedFactor, boolean isPermanent) {
        this.area = area;
        this.speedFactor = speedFactor;
        this.isPermanent = isPermanent;
    }

    public static Zone createBoost(Rectangle area) {
        return new Zone(area, BOOST_ZONE_FACTOR, true);
    }

    public static Zone createSlow(Rectangle area) {
        return new Zone(area, SLOW_ZONE_FACTOR, true);
    }

    public Rectangle getArea() { return area; }
    public double getSpeedFactor() { return speedFactor; }
    public boolean isPermanent() { return isPermanent; }
}
