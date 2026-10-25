package com.example.pong;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuScene { // сцена главного меню

    private static final double WIDTH = 854;
    private static final double HEIGHT = 480;
    private static final Color BG_COLOR = Color.web("#777B7E");
    private static final int SPACING = 20;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 50;
    private static final int FONT_SIZE = 20;

    private final Scene scene;

    public MenuScene(SceneManager sceneManager) {
        Button playButton = createButton("Play", sceneManager::showMatchSettings);

        Button controlsButton = createButton("Controls", sceneManager::showControls);

        Button exitButton = createButton("Exit", () -> System.exit(0));

        VBox root = new VBox(SPACING);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(
                new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)
        ));
        root.getChildren().addAll(playButton, controlsButton, exitButton);

        this.scene = new Scene(root, WIDTH, HEIGHT);
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setPrefWidth(BUTTON_WIDTH);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setFont(new Font("Arial", FONT_SIZE));
        button.setOnAction(e -> action.run());
        return button;
    }

    public Scene getScene() {
        return scene;
    }
}