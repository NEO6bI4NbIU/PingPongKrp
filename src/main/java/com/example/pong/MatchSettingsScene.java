package com.example.pong;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MatchSettingsScene { // сцена с настройками матча
    private static final double WIDTH = 854;
    private static final double HEIGHT = 480;
    private static final double GAME_WIDTH = 854;
    private static final double GAME_HEIGHT = 480;
    private static final Color BG_COLOR = Color.web("#777B7E");
    private static final int SPACING = 15;
    private static final int FONT_SIZE = 18;
    private static final String FONT_FAMILY = "Arial";

    private final Scene scene;
    private final TextField name1Field;
    private final TextField name2Field;
    private final TextField goalField;
    private final ChoiceBox<GameMode> modeChoiceBox;
    private final Label errorLabel;
    private final Label name2Label;

    public MatchSettingsScene(SceneManager sceneManager) {
        name1Field = createTextField("Player 1 name");
        name2Field = createTextField("Player 2 name");
        goalField = createTextField("Score to win");

        modeChoiceBox = new ChoiceBox<>();
        modeChoiceBox.getItems().addAll(GameMode.values());
        modeChoiceBox.setValue(GameMode.PVP);

        Label name1Label = createLabel("Player 1");
        name2Label = createLabel("Player 2");
        Label goalLabel = createLabel("Score to win");
        Label modeLabel = createLabel("Mode");

        modeChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isPvp = newVal == GameMode.PVP;
            name2Label.setVisible(isPvp);
            name2Field.setVisible(isPvp);
        });

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        errorLabel.setVisible(false);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> sceneManager.showMainMenu());
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> startGame(sceneManager));
        HBox buttonBox = new HBox(SPACING, backButton, startButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox form = new VBox(SPACING, name1Label, name1Field, name2Label, name2Field, goalLabel, goalField, modeLabel, modeChoiceBox, errorLabel, buttonBox);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(400);

        VBox root = new VBox(form);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(
                new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)
        ));

        this.scene = new Scene(root, WIDTH, HEIGHT);
    }

    private TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(400);
        field.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        return field;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private void startGame(SceneManager sceneManager) {
        String name1 = name1Field.getText().trim();
        String name2 = name2Field.getText().trim();
        String goalText = goalField.getText().trim();
        GameMode mode = modeChoiceBox.getValue();

        String error = MatchSettingsValidator.validate(name1, name2, goalText, mode);
        if (error != null) {
            errorLabel.setText(error);
            errorLabel.setVisible(true);
            return;
        }

        String player2Name = (mode == GameMode.PVP) ? name2 : "Bot";
        int goal = Integer.parseInt(goalText);

        ControlType controlType = GameControlsSettings.getPlayer2Control();
        GameConfig config = new GameConfig(GAME_WIDTH, GAME_HEIGHT, name1, player2Name, goal, mode, controlType);

        sceneManager.showGame(config);
    }

    public Scene getScene() {
        return scene;
    }
}