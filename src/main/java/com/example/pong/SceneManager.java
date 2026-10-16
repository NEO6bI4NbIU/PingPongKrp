package com.example.pong;

import javafx.stage.Stage;

public class SceneManager { // менеджер сцен (выполнение перехода между сценами)
    private final Stage primaryStage;
    private GameScene currentGame;

    public SceneManager(Stage primaryStage) {this.primaryStage = primaryStage;}

    public void showMainMenu() {
        stopCurrentGame();
        MenuScene menu = new MenuScene(this);
        primaryStage.setTitle("Ping Pong");
        primaryStage.setScene(menu.getScene());
        primaryStage.show();
    }

    public void showMatchSettings() {
        stopCurrentGame();
        MatchSettingsScene matchSettings = new MatchSettingsScene(this);
        primaryStage.setTitle("Match Settings");
        primaryStage.setScene(matchSettings.getScene());
        primaryStage.show();
    }

    public void showControls() {
        stopCurrentGame();
        ControlsScene controls = new ControlsScene(this);
        primaryStage.setTitle("Controls");
        primaryStage.setScene(controls.getScene());
        primaryStage.show();
    }

    public void showGame(GameConfig config) {
        stopCurrentGame();
        GameScene gameScene = new GameScene(config, this);
        this.currentGame = gameScene;
        primaryStage.setTitle("Ping Pong");
        primaryStage.setScene(gameScene.getScene());
        primaryStage.show();
        gameScene.start();
    }

    private void stopCurrentGame() {
        if (currentGame != null) {
            currentGame.stop();
            currentGame = null;
        }
    }
}
