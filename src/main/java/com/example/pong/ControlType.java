package com.example.pong;

public enum ControlType {
    KEYBOARD("Keyboard"),
    SCROLL("Scroll Wheel");

    private final String displayName;

    ControlType(String displayName) {
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