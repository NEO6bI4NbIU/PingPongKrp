package com.example.pong;

import javafx.scene.input.KeyCode;
import java.util.Set;

public class PlayerController implements RacquetController { // управление ракеткой игрока через клавиатуру
    private final KeyCode upKey;
    private final KeyCode downKey;
    private final KeyCode serveKey;

    public PlayerController(boolean isPlayerOne) {
        if (isPlayerOne) {
            this.upKey = KeyCode.Q;
            this.downKey = KeyCode.Z;
            this.serveKey = KeyCode.A;
        } else {
            this.upKey = KeyCode.O;
            this.downKey = KeyCode.M;
            this.serveKey = KeyCode.K;
        }
    }

    public KeyCode getServeKey() {
        return serveKey;
    }

    @Override
    public void update(Racquet racquet, Ball ball, Set<KeyCode> keysPressed) {
        if (keysPressed.contains(upKey)) {
            racquet.moveUp();
        }
        if (keysPressed.contains(downKey)) {
            racquet.moveDown();
        }
    }
}
