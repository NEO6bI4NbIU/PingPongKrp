package com.example.pong;

import javafx.scene.input.KeyCode;
import java.util.Random;
import java.util.Set;

public class BotController implements RacquetController {

    // ===== Параметры «сложности» =====
    private static final double ERROR_MARGIN = 38.0;   // погрешность (пикселей)
    private static final double MISTAKE_CHANCE = 0.35; // 35% шанс ошибиться сильно
    private final double fieldHeight;
    private final Random random = new Random();
    private double lastDx = 0;
    private double errorOffset = 0;


    public BotController(double fieldHeight) {
        this.fieldHeight = fieldHeight;
    }
    @Override
    public void update(Racquet racquet, Ball ball, Set<KeyCode> keysPressed) {
        if (ball.getDx() == 0 && ball.getDy() == 0) {
            return;
        }

        double ballDx = ball.getDx();
        if (Math.signum(ballDx) != Math.signum(lastDx) && ballDx != 0) { // сменилось направление мяча
            errorOffset = generateError();
            lastDx = ballDx;
        }

        if (ballDx < 0) {
            moveToward(racquet, fieldHeight / 2);
            return;
        }

        double targetY = predictBallY(ball, racquet) + errorOffset;
        moveToward(racquet, targetY);
    }


    private double generateError() {
        if (random.nextDouble() < MISTAKE_CHANCE) {
            return (random.nextDouble() * 2 - 1) * ERROR_MARGIN * 3.5; // сильная ошибка
        } else {
            return (random.nextDouble() * 2 - 1) * ERROR_MARGIN;  // обычная ошибка
        }
    }

    private double predictBallY(Ball ball, Racquet racquet) {
        double ballX = ball.getCenterX();
        double racquetX = racquet.getX();
        double dx = ball.getDx();
        double dy = ball.getDy();

        if (Math.abs(dx) < 0.01) return ball.getCenterY();

        double timeToRacquet = (racquetX - ballX) / dx;
        if (timeToRacquet < 0) return ball.getCenterY();  // мяч летит не к ракетке

        double predictedY = ball.getCenterY() + dy * timeToRacquet;
        while (predictedY < 0 || predictedY > fieldHeight) { // проверка на выход за границы
            if (predictedY < 0) predictedY = -predictedY;
            if (predictedY > fieldHeight) predictedY = 2 * fieldHeight - predictedY;
        }

        return predictedY;
    }

    private void moveToward(Racquet racquet, double targetY) {
        double currentY = racquet.getCenterY();
        double diff = targetY - currentY;

        if (Math.abs(diff) < 5) return;

        if (diff > 0) {
            racquet.moveDown();
        } else {
            racquet.moveUp();
        }
    }
}
