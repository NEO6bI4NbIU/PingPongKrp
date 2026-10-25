package com.example.pong;

import javafx.scene.input.KeyCode;

import java.util.Set;

public class BotController implements RacquetController{



    public BotController() {

    }


    @Override // пока просто пустышка
    public void update(Racquet racquet, Ball ball, Set<KeyCode> keysPressed) {
        if (keysPressed.contains(1)) {
            racquet.moveUp();
        }
        if (keysPressed.contains(2)) {
            racquet.moveDown();
        }
    }
}
