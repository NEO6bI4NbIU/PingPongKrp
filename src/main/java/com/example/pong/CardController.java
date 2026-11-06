package com.example.pong;

import javafx.scene.input.KeyCode;

public class CardController {
    private final InputHandler input;
    private final GameLogic game;

    public CardController(InputHandler input, GameLogic game) {
        this.input = input;
        this.game = game;
    }

    public void update() {
        if (input.getKeysPressed().contains(KeyCode.E)) {
            game.activateCard(1);
            input.getKeysPressed().remove(KeyCode.E);
        }
        if (input.getKeysPressed().contains(KeyCode.P)) {
            game.activateCard(2);
            input.getKeysPressed().remove(KeyCode.P);
        } else if (input.isMouseRightClicked()) {
            game.activateCard(2);
            input.clearMouseRightClick();
        }
        if (game.isBotPlayer(2) && Math.random() < 0.005) {
            game.activateCard(2);
        }
    }
}
