package com.example.pong;

public class GameRestarter {
    private final ScoreLogic scoreLogic;
    private final GameState gameState;
    private final Ball ball;
    private final Racquet leftRacquet;
    private final Racquet rightRacquet;
    private final CollisionSystem collisionSystem;
    private final Arena arena;
    private final ServeLogic serveLogic;
    private final CardManager cardManager;
    private final ActiveEffectManager effectManager;
    private final Scoreboard scoreboard;
    private final GameLoop gameLoop;
    private Champion champion;

    public GameRestarter(ScoreLogic scoreLogic, GameState gameState, Ball ball, Racquet leftRacquet, Racquet rightRacquet, CollisionSystem collisionSystem, Arena arena,
                         ServeLogic serveLogic, CardManager cardManager, ActiveEffectManager effectManager, Scoreboard scoreboard, GameLoop gameLoop) {
        this.scoreLogic = scoreLogic;
        this.gameState = gameState;
        this.ball = ball;
        this.leftRacquet = leftRacquet;
        this.rightRacquet = rightRacquet;
        this.collisionSystem = collisionSystem;
        this.arena = arena;
        this.serveLogic = serveLogic;
        this.cardManager = cardManager;
        this.effectManager = effectManager;
        this.scoreboard = scoreboard;
        this.gameLoop = gameLoop;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public void restart() {
        scoreLogic.reset();
        gameState.reset();
        if (champion != null) {
            champion.reset();
        }
        ball.resetSpeed();
        leftRacquet.reset();
        rightRacquet.reset();
        collisionSystem.resetZones();
        arena.reset();
        serveLogic.prepareFirstServe();
        gameState.setLastHitter(0);
        cardManager.clear();
        effectManager.clear();
        scoreboard.setCard(1, null);
        scoreboard.setCard(2, null);
        scoreboard.update(0, 0);
        gameLoop.start();
    }
}
