package com.example.pong;

import javafx.scene.shape.Shape;


public class CollisionSystem { // проверяет коллизии между мячом, стенами и ракеткой. Вызывает отскоки у мяча

    public void check(Ball ball, Wall upperWall, Wall lowerWall,
                      Racquet leftRacquet, Racquet rightRacquet) {

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
        }

        if (ball.getDx() > 0 && collides(ball.getCircle(), rightRacquet.getRectangle())) {
            handleRacquetHit(ball, rightRacquet, false);
        }
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

    private boolean collides(Shape a, Shape b) {
        return a.intersects(b.getBoundsInLocal());
    } // проверка пересечения двух фигур
}