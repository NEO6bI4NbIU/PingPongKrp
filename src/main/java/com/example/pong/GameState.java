package com.example.pong;

public class GameState { // хранит состояние игры

    private boolean isServing = true;
    private int servingPlayer = 0;
    private boolean isGameOver = false;


    public boolean isServing() { return isServing; }
    public void setServing(boolean serving) { isServing = serving; }

    public int getServingPlayer() { return servingPlayer; }
    public void setServingPlayer(int player) { servingPlayer = player; }

    public boolean isGameOver() { return isGameOver; }
    public void setGameOver(boolean gameOver) { isGameOver = gameOver; }

    public void reset() {
        isServing = true;
        servingPlayer = 1;
        isGameOver = false;
    }
}
