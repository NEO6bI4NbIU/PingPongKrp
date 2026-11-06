package com.example.pong;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardManager {
    private final Pane gameField;
    private final List<CardTarget> activeTargets = new ArrayList<>();
    private final CardCollisionHandler collisionHandler = new CardCollisionHandler();
    private final Random random = new Random();
    private final GameState gameState;
    private final Scoreboard scoreboard;

    public CardManager(GameState gameState, Scoreboard scoreboard, Pane gameField) {
        this.gameState = gameState;
        this.scoreboard = scoreboard;
        this.gameField = gameField;
    }

    public void update(double deltaTime, double width, double height, Ball ball) {
        if (activeTargets.isEmpty() && random.nextDouble() < 0.0037) { // карта появляется ~ раз в 5 секунд
            spawnCard(width, height);
        }
        CardTarget hitCard = collisionHandler.checkCardHit(ball, activeTargets);
        if (hitCard != null && gameState.getLastHitter() != 0) {
            gameState.setPlayerCard(gameState.getLastHitter(), hitCard.getType());
            activeTargets.remove(hitCard);
            gameField.getChildren().remove(hitCard.getShape()); // удаление с экрана
            scoreboard.setCard(gameState.getLastHitter(), hitCard.getType());
        }
    }

    private void spawnCard(double width, double height) {
        List<CardType> enabled = getEnabledCards();
        if (enabled.isEmpty()) return;
        CardType type = enabled.get(random.nextInt(enabled.size()));
        double x = 100 + random.nextDouble() * (width - 200);
        double y = 100 + random.nextDouble() * (height - 200);
        CardTarget target = new CardTarget(type, x, y);
        activeTargets.add(target);
        gameField.getChildren().add(target.getShape()); // добавление на экран
    }

    public void clear() {
        for (CardTarget target : activeTargets) {
            gameField.getChildren().remove(target.getShape()); // удаление с экрана
        }
        activeTargets.clear();
    }

    private List<CardType> getEnabledCards() {
        List<CardType> enabled = new ArrayList<>();
        for (CardType c : CardType.values()) {
            if (GameCardSettings.isCardEnabled(c)) enabled.add(c);
        }
        return enabled;
    }
}
