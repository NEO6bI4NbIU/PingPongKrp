package com.example.pong;

import javafx.geometry.Bounds;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.List;


public class CollisionSystem { // проверяет коллизии между мячом, стенами и ракеткой. Вызывает отскоки у мяча

    public boolean check(Ball ball, Wall upperWall, Wall lowerWall,
                      Racquet leftRacquet, Racquet rightRacquet, List<Shape> obstacles, List<Zone> zones) {

        boolean hitRacquet = false;
        if (collides(ball.getCircle(), upperWall.getRectangle())) {
            ball.bounceY();
            ball.setCenterY(upperWall.getRectangle().getY() // корректирую позицию, чтобы мяч не "залип" в стене
                    + upperWall.getRectangle().getHeight()
                    + ball.getRadius());
        }

        if (collides(ball.getCircle(), lowerWall.getRectangle())) {
            ball.bounceY();
            ball.setCenterY(lowerWall.getRectangle().getY() - ball.getRadius());
        }

        if (ball.getDx() < 0 && collides(ball.getCircle(), leftRacquet.getRectangle())) {
            handleRacquetHit(ball, leftRacquet, true);
            hitRacquet = true;
        }

        if (ball.getDx() > 0 && collides(ball.getCircle(), rightRacquet.getRectangle())) {
            handleRacquetHit(ball, rightRacquet, false);
            hitRacquet = true;
        }

        for (Shape obstacle : obstacles) {
            if (obstacle instanceof Circle circle) {
                if (collidesCircle(ball, circle)) {
                    handleCircleHit(ball, circle);
                }
            } else {
                if (collides(ball.getCircle(), obstacle)) {
                    handleObstacleHit(ball, obstacle);
                }
            }
        }
        double zoneMultiplier = 1.0;
        for (Zone zone : zones) {
            if (collides(ball.getCircle(), zone.getArea())) {
                zoneMultiplier = zone.getSpeedMultiplier();;
                break;
            }
        }
        ball.setZoneMultiplier(zoneMultiplier);
        return hitRacquet;
    }


    private void handleRacquetHit(Ball ball, Racquet racquet, boolean isLeftRacquet) { // реализация отскока мяча от ракеток
        double ballCenterY = ball.getCenterY();
        double racquetTop = racquet.getY();
        double racquetBottom = racquet.getY() + racquet.getHeight();

        if (ballCenterY > racquetTop && ballCenterY < racquetBottom) {
            ball.bounceX();

            if (isLeftRacquet) {
                ball.setCenterX(racquet.getX() + racquet.getWidth() + ball.getRadius());
            } else {
                ball.setCenterX(racquet.getX() - ball.getRadius());
            }
        } else {
            ball.bounceY();

            if (ballCenterY < racquetTop) {
                ball.setCenterY(racquetTop - ball.getRadius());
            } else {
                ball.setCenterY(racquetBottom + ball.getRadius());
            }
        }
    }

    private void handleObstacleHit(Ball ball, Shape obstacle) {
        Bounds obstacleBounds = obstacle.getBoundsInParent();

        double ballCenterX = ball.getCenterX();
        double ballCenterY = ball.getCenterY();

        double distLeft = Math.abs(ballCenterX - obstacleBounds.getMinX());
        double distRight = Math.abs(obstacleBounds.getMaxX() - ballCenterX);
        double distTop = Math.abs(ballCenterY - obstacleBounds.getMinY());
        double distBottom = Math.abs(obstacleBounds.getMaxY() - ballCenterY);

        double minDist = Math.min(Math.min(distLeft, distRight), Math.min(distTop, distBottom));

        if (minDist == distLeft) {
            ball.bounceX();
            ball.setCenterX(obstacleBounds.getMinX() - ball.getRadius());
        } else if (minDist == distRight) {
            ball.bounceX();
            ball.setCenterX(obstacleBounds.getMaxX() + ball.getRadius());
        } else if (minDist == distTop) {
            ball.bounceY();
            ball.setCenterY(obstacleBounds.getMinY() - ball.getRadius());
        } else {
            ball.bounceY();
            ball.setCenterY(obstacleBounds.getMaxY() + ball.getRadius());
        }
    }

    private boolean collidesCircle(Ball ball, Circle circle) {
        double dx = ball.getCenterX() - circle.getCenterX();
        double dy = ball.getCenterY() - circle.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < ball.getRadius() + circle.getRadius();
    }

    private void handleCircleHit(Ball ball, Circle circle) {
        double dx = ball.getCenterX() - circle.getCenterX();
        double dy = ball.getCenterY() - circle.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance == 0) return;

        // Нормаль от центра круга к мячу
        double nx = dx / distance;
        double ny = dy / distance;

        // Отражение скорости относительно нормали
        double dot = ball.getDx() * nx + ball.getDy() * ny;
        if (dot < 0) {  // мяч летит в сторону круга
            ball.setDx(ball.getDx() - 2 * dot * nx);
            ball.setDy(ball.getDy() - 2 * dot * ny);
        }

        // Корректировка позиции
        double newX = circle.getCenterX() + nx * (circle.getRadius() + ball.getRadius());
        double newY = circle.getCenterY() + ny * (circle.getRadius() + ball.getRadius());
        ball.setCenterX(newX);
        ball.setCenterY(newY);
    }

    private boolean collides(Shape a, Shape b) {
        return a.intersects(b.getBoundsInLocal());
    } // проверка пересечения двух фигур
}