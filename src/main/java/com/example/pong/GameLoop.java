package com.example.pong;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer { // игровой цикл

    private final GameLogic gameLogic;

    public GameLoop(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void handle(long now) {
        gameLogic.update();
    }
}