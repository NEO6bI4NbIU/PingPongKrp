package com.example.pong;

import javafx.scene.input.KeyCode;
import java.util.Set;

public class ScrollController implements RacquetController {

    private final InputHandler inputHandler;
    private long lastScrollTime = 0;
    private double lastDirection = 0;
    private static final long CONTINUOUS_THRESHOLD_MS = 125;

    public ScrollController(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public void update(Racquet racquet, Ball ball, Set<KeyCode> keysPressed) {
        double delta = inputHandler.consumeScrollDelta();
        long now = System.currentTimeMillis();

        if (delta != 0) {
            lastScrollTime = now;
            lastDirection = delta;
            if (delta > 0) {
                racquet.moveUp();
            } else {
                racquet.moveDown();
            }
        } else if (now - lastScrollTime < CONTINUOUS_THRESHOLD_MS && lastDirection != 0) {  // проверяет сколько времени прошло с последнего "щелчка" колёсика и направление
            if (lastDirection > 0) {
                racquet.moveUp();
            } else {
                racquet.moveDown();
            }
        } else {
            lastDirection = 0;
        }
    }
}