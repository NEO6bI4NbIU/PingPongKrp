package com.example.pong;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ControlsScene { // меню с показом клавиш управления

    private static final double WIDTH = 854;
    private static final double HEIGHT = 480;
    private static final Color BG_COLOR = Color.web("#777B7E");
    private static final String FONT_FAMILY = "Arial";
    private static final int TITLE_FONT_SIZE = 30;
    private static final int HEADER_FONT_SIZE = 22;
    private static final int TEXT_FONT_SIZE = 18;
    private static final int SPACING = 15;

    private final Scene scene;

    public ControlsScene(SceneManager sceneManager) {
        Label titleLabel = new Label("Controls");
        titleLabel.setFont(new Font(FONT_FAMILY, TITLE_FONT_SIZE));
        titleLabel.setTextFill(Color.WHITE);

        VBox player1Panel = createControlPanel("Player 1",
                "Q — up",
                "Z — down",
                "A — serve"
        );

        VBox player2Panel = createControlPanel("Player 2",
                "O — up",
                "M — down",
                "K — serve"
        );

        HBox controlsBox = new HBox(80, player1Panel, player2Panel);
        controlsBox.setAlignment(Pos.CENTER);

        Label tipLabel = new Label("Press Enter to serve in the first round. After a goal, the player who conceded serves.");
        tipLabel.setFont(new Font(FONT_FAMILY, TEXT_FONT_SIZE));
        tipLabel.setTextFill(Color.WHITE);
        tipLabel.setWrapText(true);
        tipLabel.setMaxWidth(WIDTH - 100);

        Button backButton = new Button("Back");
        backButton.setFont(new Font(FONT_FAMILY, TEXT_FONT_SIZE));
        backButton.setOnAction(e -> sceneManager.showMainMenu());

        BorderPane root = new BorderPane();
        root.setBackground(new Background(
                new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)
        ));
        root.setTop(titleLabel);
        root.setCenter(controlsBox);
        root.setBottom(new VBox(SPACING, tipLabel, backButton));

        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(20));
        BorderPane.setAlignment(controlsBox, Pos.CENTER);

        VBox bottomBox = (VBox) root.getBottom();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        this.scene = new Scene(root, WIDTH, HEIGHT);
    }

    private VBox createControlPanel(String title, String... lines) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font(FONT_FAMILY, HEADER_FONT_SIZE));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-background-color: RGBA(0, 0, 0, 0.3); -fx-padding: 5 15;");

        VBox panel = new VBox(SPACING);
        panel.setAlignment(Pos.CENTER);
        panel.getChildren().add(titleLabel);

        for (String line : lines) {
            Label label = new Label(line);
            label.setFont(new Font(FONT_FAMILY, TEXT_FONT_SIZE));
            label.setTextFill(Color.WHITE);
            panel.getChildren().add(label);
        }

        return panel;
    }

    public Scene getScene() {
        return scene;
    }
}
