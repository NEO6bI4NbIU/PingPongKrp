package com.example.pong;

public class GameState { // хранит состояние игры

    private boolean isServing = true;
    private int servingPlayer = 0;
    private CardType player1Card = null;
    private CardType player2Card = null;
    private int lastHitter = 0;
    private boolean isGameOver = false;


    public boolean isServing() { return isServing; }
    public void setServing(boolean serving) { isServing = serving; }

    public int getServingPlayer() { return servingPlayer; }
    public void setServingPlayer(int player) { servingPlayer = player; }

    public void setPlayerCard(int player, CardType card) {
        if (player == 1) player1Card = card;
        else player2Card = card;
    }
    public CardType getPlayerCard(int player) {
        return player == 1 ? player1Card : player2Card;
    }

    public void setLastHitter(int player) { lastHitter = player; }
    public int getLastHitter() { return lastHitter; }


    public void reset() {
        isServing = true;
        servingPlayer = 1;
        lastHitter = 0;
        isGameOver = false;
    }
}
