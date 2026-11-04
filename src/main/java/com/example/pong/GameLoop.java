package com.example.pong;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer { // игровой цикл

    private final GameLogic gameLogic;
    private long lastTime = 0;

    public GameLoop(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void handle(long now) {
        if (lastTime == 0) {
            lastTime = now;
            return;  // первый кадр пропускаю, т.к дальше при вычислении может выскочить огромное число
        }
        double deltaTime = (now - lastTime) / 1_000_000_000.0;  // в секундах
        lastTime = now;
        if (deltaTime > 0.1) deltaTime = 0.1;
        gameLogic.update(deltaTime);
    }
}