package com.example.pong;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// отвечает за создание панелей (в ControlsScene)
public class ControlPanelFactory {

    private static final String FONT_FAMILY = "Arial";
    private static final int HEADER_FONT_SIZE = 22;
    private static final int TEXT_FONT_SIZE = 18;
    private static final int SPACING = 15;


    public static VBox createPanel(String title, String... lines) {
        VBox panel = new VBox(SPACING);
        panel.setAlignment(Pos.CENTER);
        panel.getChildren().add(createTitleLabel(title));

        for (String line : lines) {
            panel.getChildren().add(createKeyLabel(line));
        }

        return panel;
    }

    public static void updatePlayer2Panel(VBox panel, ControlType type) {
        panel.getChildren().clear();
        panel.getChildren().add(createTitleLabel("Player 2"));

        switch (type) {
            case KEYBOARD -> panel.getChildren().addAll(
                    createKeyLabel("O — up"),
                    createKeyLabel("M — down"),
                    createKeyLabel("K — serve")
            );
            case SCROLL -> panel.getChildren().addAll(
                    createKeyLabel("Scroll Up — up"),
                    createKeyLabel("Scroll Down — down"),
                    createKeyLabel("LMB — serve")
            );
        }
    }

    private static Label createTitleLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font(FONT_FAMILY, HEADER_FONT_SIZE));
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-background-color: RGBA(0, 0, 0, 0.3); -fx-padding: 5 15;");
        return label;
    }

    private static Label createKeyLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font(FONT_FAMILY, TEXT_FONT_SIZE));
        label.setTextFill(Color.WHITE);
        return label;
    }
}