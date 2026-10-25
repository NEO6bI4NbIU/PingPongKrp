package com.example.pong;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class InputHandler { // обрабатывает ввод с клавиатуры, хранит мн-во нажатых клавиш

    private final Set<KeyCode> keysPressed = new HashSet<>();

    /**
     * Привязывает обработчики к сцене.
     */
    public void attachTo(Scene scene) { // подключает InputHandler к Scene чтобы она ловила нажатия
        scene.setOnKeyPressed(e -> keysPressed.add(e.getCode()));
        scene.setOnKeyReleased(e -> keysPressed.remove(e.getCode()));
    }

    public Set<KeyCode> getKeysPressed() {
        return keysPressed;
    }

    public void clear() {
        keysPressed.clear();
    }
}
