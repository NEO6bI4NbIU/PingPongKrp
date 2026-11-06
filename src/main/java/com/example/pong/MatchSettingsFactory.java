package com.example.pong;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MatchSettingsFactory { // cоздание (почти)всех элементов для MatchSettingsScene
    private static final int SPACING = 15;
    private static final int FONT_SIZE = 18;
    private static final String FONT_FAMILY = "Arial";
    private static final double FIELD_WIDTH = 400;

    public static VBox createForm(TextField name1Field, TextField name2Field, TextField goalField,
            ChoiceBox<OpponentType> opponentChoiceBox, ChoiceBox<GameMode> modeChoiceBox, CheckBox circlesCheckBox,
            CheckBox movingBarCheckBox, CheckBox speedZonesCheckBox,CheckBox cardsCheckBox,
            Label name2Label, Label errorLabel, HBox buttonBox) {

        HBox namesBox = new HBox(SPACING,
                new VBox(5, createLabel("Player 1"), name1Field),
                new VBox(5, name2Label, name2Field)
        );
        namesBox.setAlignment(Pos.CENTER);

        HBox goalOpponentBox = new HBox(SPACING,
                new VBox(5, createLabel("Score to win"), goalField),
                new VBox(5, createLabel("Opponent"), opponentChoiceBox)
        );
        goalOpponentBox.setAlignment(Pos.CENTER);

        HBox modeArenaBox = new HBox(SPACING,
                new VBox(5, createLabel("Mode"), modeChoiceBox),
                new VBox(5, createLabel("Arena"),
                        new VBox(5, circlesCheckBox, movingBarCheckBox, speedZonesCheckBox, cardsCheckBox))
        );
        modeArenaBox.setAlignment(Pos.CENTER);

        VBox form = new VBox(SPACING, namesBox, goalOpponentBox, modeArenaBox, errorLabel, buttonBox);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(700);
        return form;
    }

    public static TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(FIELD_WIDTH);
        field.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        return field;
    }

    public static Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        label.setTextFill(Color.WHITE);
        return label;
    }

    public static CheckBox createCheckBox(String text) {
        CheckBox box = new CheckBox(text);
        box.setTextFill(Color.WHITE);
        box.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        return box;
    }

    public static <T> ChoiceBox<T> createChoiceBox(T[] values, T defaultValue, double width) {
        ChoiceBox<T> box = new ChoiceBox<>();
        box.getItems().addAll(values);
        box.setValue(defaultValue);
        box.setPrefWidth(width);
        return box;
    }
}