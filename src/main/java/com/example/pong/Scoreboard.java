package com.example.pong;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Scoreboard { // отображение счета в верхней части экрана

    private static final String FONT_FAMILY = "Arial";
    private static final int FONT_SIZE = 25;
    private static final int SPACING = 30;
    private static final int SCORE_WIDTH = 50;

    private final HBox panel;
    private final Label score1Label;
    private final Label score2Label;

    public Scoreboard(String name1, String name2) {
        Label name1Label = createLabel(name1);
        Label name2Label = createLabel(name2);
        this.score1Label = createScoreLabel("0");
        this.score2Label = createScoreLabel("0");
        Label separator = createLabel("X");

        this.panel = new HBox(SPACING);
        panel.setAlignment(Pos.CENTER);
        panel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        panel.getChildren().addAll(name1Label, score1Label, separator, score2Label, name2Label);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        label.setTextFill(Color.BLACK);
        return label;
    }

    private Label createScoreLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        label.setTextFill(Color.BLACK);
        label.setPrefWidth(SCORE_WIDTH);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public void update(int score1, int score2) {
        score1Label.setText(String.valueOf(score1));
        score2Label.setText(String.valueOf(score2));
    }

    public HBox getPanel() {
        return panel;
    }
}