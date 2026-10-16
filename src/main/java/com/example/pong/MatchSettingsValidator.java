package com.example.pong;

public class MatchSettingsValidator { // проверка верности ввода настроек
    private static final int MAX_NAME_LENGTH = 15;

    public static String validate(String name1, String name2, String goalText, GameMode mode) {
        if (name1.isEmpty() || name1.length() > MAX_NAME_LENGTH) {
            return "Player 1 name must be 1-" + MAX_NAME_LENGTH + " characters";
        }
        if (mode == GameMode.PVP && (name2.isEmpty() || name2.length() > MAX_NAME_LENGTH)) {
            return "Player 2 name must be 1-" + MAX_NAME_LENGTH + " characters";
        }
        if (goalText.isEmpty()) {
            return "Enter score to win";
        }
        if (!goalText.matches("\\d+")) {
            return "Score must be a positive number";
        }
        int goal = Integer.parseInt(goalText);
        if (goal <= 0) {
            return "Score must be greater than 0";
        }
        return null;
    }
}
