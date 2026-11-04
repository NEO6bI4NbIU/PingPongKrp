package com.example.pong;

public enum OpponentType {
    PLAYER("Player"),
    BOT("Bot");

    private final String displayName;

    OpponentType(String displayName) {
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
