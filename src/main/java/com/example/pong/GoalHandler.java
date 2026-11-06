package com.example.pong;

public class GoalHandler {
    private final ScoreLogic scoreLogic;
    private final Scoreboard scoreboard;
    private final Champion champion;
    private final ServeLogic serveLogic;
    private final CollisionSystem collisionSystem;
    private final GameState gameState;
    private final Ball ball;
    private final double width;

    public GoalHandler(ScoreLogic scoreLogic, Scoreboard scoreboard, Champion champion,
                       ServeLogic serveLogic, CollisionSystem collisionSystem,
                       GameState gameState, Ball ball, double width) {
        this.scoreLogic = scoreLogic;
        this.scoreboard = scoreboard;
        this.champion = champion;
        this.serveLogic = serveLogic;
        this.collisionSystem = collisionSystem;
        this.gameState = gameState;
        this.ball = ball;
        this.width = width;
    }

    public boolean checkGoal() {
        int scorer = scoreLogic.update(ball, width);
        if (scorer == 0) return false;
        scoreboard.update(scoreLogic.getScore1(), scoreLogic.getScore2());
        if (champion.check()) return true;
        serveLogic.prepareServe(scorer);
        collisionSystem.resetZones();
        gameState.setLastHitter(0);
        return false;
    }
}
