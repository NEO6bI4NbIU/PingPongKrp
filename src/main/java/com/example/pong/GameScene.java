package com.example.pong;

import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class GameScene { // игровая сцена (сцена матча)

    private static final double SCOREBOARD_HEIGHT = 50;
    private final VBox root;
    private final Scene scene;
    private final GameLogic gameLogic;


    public GameScene(GameConfig config, SceneManager sceneManager) {
        double width = config.getWidth();
        double height = config.getHeight();
        Pane gameField = new Pane();
        gameField.setPrefSize(width, height);
        gameField.setMinSize(width, height);
        gameField.setMaxSize(width, height);
        this.gameLogic = new GameLogic(config, sceneManager);

        /*public void resize(double newWidth, double newHeight) {
            double scale = Math.min(newWidth / width, newHeight / height);
            root.setScaleX(scale);
            root.setScaleY(scale);
            root.setTranslateX((newWidth - width * scale) / 2);
            root.setTranslateY((newHeight - height * scale) / 2);           надо будет реализовать возможность изменения размера окна
        }*/

        gameField.getChildren().addAll(gameLogic.getBall().getCircle(),
                gameLogic.getUpperWall().getRectangle(),
                gameLogic.getLowerWall().getRectangle(),
                gameLogic.getLeftRacquet().getRectangle(),
                gameLogic.getRightRacquet().getRectangle());

        gameLogic.getScoreboard().getPanel().setPrefHeight(SCOREBOARD_HEIGHT);
        gameLogic.getScoreboard().getPanel().setMaxHeight(SCOREBOARD_HEIGHT);
        this.root = new VBox(gameLogic.getScoreboard().getPanel(), gameField);
        this.scene = new Scene(root, width, height + SCOREBOARD_HEIGHT);

        gameLogic.getInputHandler().attachTo(scene);
    }

    public Scene getScene() {return scene;}

    public void start() {gameLogic.start();}

    public void stop() {gameLogic.stop();}

    public void update() {gameLogic.update();}

}