package com.example.pong;

import javafx.scene.input.KeyCode;
import java.util.Set;


public interface RacquetController { // интерфейс для управления ракеткой
    // обновляет состояние ракетки на основе ввода
    void update(Racquet racquet, Ball ball, Set<KeyCode> keysPressed);
}