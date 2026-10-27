package com.example.pong;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.HashSet;
import java.util.Set;

public class InputHandler { // обрабатывает ввод с клавиатуры, хранит мн-во нажатых клавиш

    private final Set<KeyCode> keysPressed = new HashSet<>();
    private double scrollDelta = 0;
    private boolean mouseClicked = false;


    public void attachTo(Scene scene) { // подключает InputHandler к Scene чтобы она ловила нажатия
        scene.setOnKeyPressed(e -> keysPressed.add(e.getCode()));
        scene.setOnKeyReleased(e -> keysPressed.remove(e.getCode()));
        scene.setOnScroll(e -> scrollDelta += e.getDeltaY());

        scene.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) mouseClicked = true;
        });
        scene.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.PRIMARY) mouseClicked = false;
        });
    }

    public Set<KeyCode> getKeysPressed() {
        return keysPressed;
    }

    public void clear() {
        keysPressed.clear();
    }

    public double consumeScrollDelta() {
        double delta = scrollDelta;
        scrollDelta = 0;
        return delta;
    }

    public boolean isMouseClicked() { return mouseClicked; }
    public void clearMouseClick() { mouseClicked = false; }
}
