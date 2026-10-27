package com.example.pong;

public class GameConfig { // параметры для начала матча
    private final double width;
    private final double height;
    private final String player1Name;
    private final String player2Name;
    private final int goal;
    private final GameMode mode;
    private final ControlType controlType;

    public GameConfig(double width, double height, String player1Name, String player2Name, int goal, GameMode mode, ControlType controlType) {
        this.width = width;
        this.height = height;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.goal = goal;
        this.mode = mode;
        this.controlType = controlType;
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public int getGoal() { return goal; }
    public String getPlayer1Name() { return player1Name; }
    public String getPlayer2Name() { return player2Name; }
    public GameMode getMode() { return mode; }
    public ControlType getControlType() { return controlType; }
}
