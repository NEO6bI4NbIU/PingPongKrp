package com.example.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomArena implements Arena {
    private static final Random RANDOM = new Random();

    private final List<Shape> obstacles = new ArrayList<>();
    private final List<Zone> zones = new ArrayList<>();
    private final List<MovingObstacle> movingObstacles = new ArrayList<>();

    public CustomArena(double width, double height,
                       int maxCircleCount,
                       boolean hasMovingBar,
                       boolean hasSpeedZones) {

        double leftRacquetX = 10;
        double rightRacquetX = width - 20;
        double racquetWidth = Racquet.DEFAULT_WIDTH;

        // Движущиеся палки
        List<Rectangle> bars = new ArrayList<>();
        if (hasMovingBar) {
            double barWidth = 10;
            double barHeight = 100;

            Rectangle bar1 = new Rectangle(width / 3.0 - barWidth / 2, height / 2 - barHeight / 2, barWidth, barHeight);
            bar1.setFill(Color.GRAY);
            bars.add(bar1);
            movingObstacles.add(new MovingObstacle(bar1, 1, 150, 0.5, width, height));

            Rectangle bar2 = new Rectangle(2 * width / 3.0 - barWidth / 2, height / 2 - barHeight / 2, barWidth, barHeight);
            bar2.setFill(Color.GRAY);
            bars.add(bar2);
            movingObstacles.add(new MovingObstacle(bar2, 1, 150, 0.5, width, height));
        }

        // Круги
        int circleCount = 3 + RANDOM.nextInt(Math.max(1, maxCircleCount - 2));
        List<Circle> circles = new ArrayList<>();
        for (int i = 0; i < circleCount; i++) {
            Circle circle = tryCreateCircle(width, height, circles, leftRacquetX, rightRacquetX, racquetWidth);
            if (circle != null) {
                circle.setFill(Color.GRAY);
                circles.add(circle);
                obstacles.add(circle);
            }
        }

        for (Rectangle bar : bars) {
            obstacles.add(bar);
        }

        // Зоны
        if (hasSpeedZones) {
            Rectangle boost = new Rectangle(50, height / 2 - 50, 100, 100);
            boost.setFill(Color.LIGHTGREEN);
            zones.add(new Zone(boost, 1.5));

            Rectangle slow = new Rectangle(width - 150, height / 2 - 50, 100, 100);
            slow.setFill(Color.LIGHTBLUE);
            zones.add(new Zone(slow, 0.5));
        }
    }

    // проверка пересечения круга с остальными объектами при его создании. 20 попыток - иначе возвращает ничего
    private Circle tryCreateCircle(double width, double height, List<Circle> existing, double leftRacquetX, double rightRacquetX, double racquetWidth) {
        int maxAttempts = 20;
        double centerX = width / 2;
        double centerY = height / 2;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            double radius = 20 + RANDOM.nextDouble() * 20;  // 20-40
            double x = radius + RANDOM.nextDouble() * (width - 2 * radius);
            double y = radius + RANDOM.nextDouble() * (height - 2 * radius);

            Circle candidate = new Circle(x, y, radius);

            boolean overlaps = false; // отступ от уже существующих кругов на 40 пикселей
            for (Circle other : existing) {
                double dx = candidate.getCenterX() - other.getCenterX();
                double dy = candidate.getCenterY() - other.getCenterY();
                double distance = Math.hypot(dx, dy);
                if (distance < candidate.getRadius() + other.getRadius() + 40) {
                    overlaps = true;
                    break;
                }
            }
            if (overlaps) continue;

            if (x - radius < leftRacquetX + racquetWidth + 20) continue; // отступ от ракеток на 20 пикселей
            if (x + radius > rightRacquetX - 20) continue;

            boolean overlapsMoving = false;          // отступ от движущихся балок 10 пикселей
            for (MovingObstacle mo : movingObstacles) {
                double barMinX = mo.getMinX() - radius - 10;
                double barMaxX = mo.getMaxX() + radius + 10;
                double barMinY = mo.getMinY() - radius - 10;
                double barMaxY = mo.getMaxY() + radius + 10;

                if (x + radius > barMinX && x - radius < barMaxX &&
                        y + radius > barMinY && y - radius < barMaxY) {
                    overlapsMoving = true;
                    break;
                }
            }
            if (overlapsMoving) continue;

            double distToCenter = Math.hypot(x - centerX, y - centerY); // отступ от центра поля
            if (distToCenter < radius + 30) continue;

            if (y - radius < 20 || y + radius > height - 20) continue; // отступ от стен на 10 пикселей

            return candidate;
        }

        return null;  // не удалось - пропуск
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
    @Override
    public void reset() {
        for (MovingObstacle mo : movingObstacles) {
            mo.reset();
        }
    }
}
