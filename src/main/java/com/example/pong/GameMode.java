package com.example.pong;

public enum GameMode { // перечисление режимов игры
    CLASSIC("Classic"),  // Без ускорения
    ARCADE("Arcade");

    private final String displayName;

    GameMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
