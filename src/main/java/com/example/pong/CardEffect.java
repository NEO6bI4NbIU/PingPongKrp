package com.example.pong;

public interface CardEffect {
    void apply(GameLogic game, int player);
    void revert(GameLogic game, int player);
}
