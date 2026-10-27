package com.example.pong;

// Хранит выбор управления между сценами
public class GameControlsSettings {

    private static ControlType player2Control = ControlType.KEYBOARD;

    public static ControlType getPlayer2Control() {
        return player2Control;
    }

    public static void setPlayer2Control(ControlType type) {
        player2Control = type;
    }
}
