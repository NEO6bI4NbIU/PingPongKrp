package com.example.pong;

public class ScoreLogic { // логика подсчёта очков

    private int score1 = 0;
    private int score2 = 0;
    private boolean pointScored = false;

    public int update(Ball ball, double width) {
        double ballMinX = ball.getCenterX() - ball.getRadius();
        double ballMaxX = ball.getCenterX() + ball.getRadius();

        if (ballMinX < 0 && !pointScored) {
            score2++;
            pointScored = true;
            return 2;
        }

        if (ballMaxX > width && !pointScored) {
            score1++;
            pointScored = true;
            return 1;
        }

        if (ballMinX >= 0 && ballMaxX <= width) {
            pointScored = false;
        }

        return 0;
    }

    public int getScore1() { return score1; }
    public int getScore2() { return score2; }

    public void reset() {
        score1 = 0;
        score2 = 0;
        pointScored = false;
    }
}
