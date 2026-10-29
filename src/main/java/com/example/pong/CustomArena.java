package com.example.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;

public class CustomArena implements Arena {
    private final List<Shape> obstacles = new ArrayList<>();
    private final List<Zone> zones = new ArrayList<>();
    private final List<MovingObstacle> movingObstacles = new ArrayList<>();

    public CustomArena(double width, double height,
                       int circleCount,
                       boolean hasMovingBar,
                       boolean hasSpeedZones) {
        // Круги
        for (int i = 0; i < circleCount; i++) {
            Circle c = new Circle(200 + i * 150, height / 2, 30);
            c.setFill(Color.GRAY);
            obstacles.add(c);
        }

        // Движущаяся палка
        if (hasMovingBar) {
            double barWidth = 10;
            double barHeight = 100;

            Rectangle bar1 = new Rectangle(width / 3.0 - barWidth / 2, height / 2 - barHeight / 2, barWidth, barHeight);
            bar1.setFill(Color.GRAY);
            obstacles.add(bar1);
            movingObstacles.add(new MovingObstacle(bar1, 1, 150, 0.5, width, height));

            Rectangle bar2 = new Rectangle(2 * width / 3.0 - barWidth / 2, height / 2 - barHeight / 2, barWidth, barHeight);
            bar2.setFill(Color.GRAY);
            obstacles.add(bar2);
            movingObstacles.add(new MovingObstacle(bar2, 1, 150, 0.5, width, height));
        }

        // Зоны
        if (hasSpeedZones) {
            Rectangle boost = new Rectangle(50, height / 2 - 50, 100, 100);
            boost.setFill(Color.LIGHTGREEN);
            zones.add(new Zone(boost, 3.5));

            Rectangle slow = new Rectangle(width - 150, height / 2 - 50, 100, 100);
            slow.setFill(Color.LIGHTBLUE);
            zones.add(new Zone(slow, 0.15));
        }
    }

    @Override
    public List<Shape> getObstacles() { return obstacles; }
    @Override
    public List<Zone> getZones() { return zones; }
    @Override
    public void update(double deltaTime) {
        for (MovingObstacle mo : movingObstacles) {
            mo.update(deltaTime);
        }
    }
    public void reset() {
        for (MovingObstacle mo : movingObstacles) {
            mo.reset();
        }
    }
}
