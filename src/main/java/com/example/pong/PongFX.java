package com.example.pong;

import javafx.application.Application;
import javafx.stage.Stage;


public class PongFX extends Application { // создаёт главное окно и запускает игровую сцену
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showMainMenu();
    }
}