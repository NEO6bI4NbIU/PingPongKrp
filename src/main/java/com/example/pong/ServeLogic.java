package com.example.pong;

import javafx.scene.input.KeyCode;

public class ServeLogic { // логика подачи мяча

    private final double width;
    private final double height;
    private final Ball ball;
    private final Racquet leftRacquet;
    private final Racquet rightRacquet;
    private final GameState gameState;
    private final InputHandler inputHandler;

    public ServeLogic(double width, double height,
                      Ball ball, Racquet leftRacquet, Racquet rightRacquet,
                      GameState gameState, InputHandler inputHandler) {
        this.width = width;
        this.height = height;
        this.ball = ball;
        this.leftRacquet = leftRacquet;
        this.rightRacquet = rightRacquet;
        this.gameState = gameState;
        this.inputHandler = inputHandler;
    }

    public void prepareFirstServe() { // первая подача из центра поля
        ball.stop();
        leftRacquet.reset();
        rightRacquet.reset();

        gameState.setServing(true);
        gameState.setServingPlayer(0);

        ball.setCenterX(width / 2);
        ball.setCenterY(height / 2);
    }

    public void prepareServe(int scorer) { // подача после гола
        ball.stop();
        leftRacquet.reset();
        rightRacquet.reset();

        gameState.setServing(true);
        int server = (scorer == 1) ? 2 : 1;
        gameState.setServingPlayer(server);

        updateBallForServe();
    }

    public void updateBallForServe() {
        int server = gameState.getServingPlayer();

        if (server == 0) {
            ball.setCenterX(width / 2);
            ball.setCenterY(height / 2);
        } else if (server == 1) {
            ball.setCenterX(leftRacquet.getX() + leftRacquet.getWidth() + ball.getRadius());
            ball.setCenterY(leftRacquet.getCenterY());
        } else {
            ball.setCenterX(rightRacquet.getX() - ball.getRadius());
            ball.setCenterY(rightRacquet.getCenterY());
        }
    }

    public void handleServeInput(KeyCode serveKey) {
        if (inputHandler.getKeysPressed().contains(serveKey)) {
            launchServe();
            inputHandler.getKeysPressed().remove(serveKey);
        }
    }
    public void handleServeInputByMouse() {
        if (inputHandler.isMouseClicked()) {
            launchServe();
            inputHandler.clearMouseClick();
        }
    }

    public void handleServeInputByBot() {
        launchServe();
    }

    private void launchServe() {
        int server = gameState.getServingPlayer();
        int directionX;
        if (server == 0) {
            directionX = (Math.random() < 0.5) ? 1 : -1;
        } else {
            directionX = (server == 1) ? 1 : -1;
        }

        ball.serve(directionX);
        gameState.setServing(false);
    }
}
