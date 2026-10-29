package com.example.pong;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private static final double FIELD_WIDTH = 400;

    private final Scene scene;
    private final TextField name1Field;
    private final TextField name2Field;
    private final TextField goalField;
    private final ChoiceBox<OpponentType> opponentChoiceBox;
    private final ChoiceBox<GameMode> modeChoiceBox;
    private final CheckBox circlesCheckBox;
    private final CheckBox movingBarCheckBox;
    private final CheckBox speedZonesCheckBox;
    private final Label errorLabel;
    private final Label name2Label;

    public MatchSettingsScene(SceneManager sceneManager) {
        name1Field = createTextField("Player 1 name");
        name2Field = createTextField("Player 2 name");
        goalField = createTextField("Score to win");

        opponentChoiceBox = createChoiceBox(OpponentType.values(), OpponentType.PLAYER);
        modeChoiceBox = createChoiceBox(GameMode.values(), GameMode.CLASSIC);
        modeChoiceBox.setMaxWidth(140);

        circlesCheckBox = new CheckBox("Circles");
        circlesCheckBox.setTextFill(Color.WHITE);
        circlesCheckBox.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        movingBarCheckBox = new CheckBox("Moving Bar");
        movingBarCheckBox.setTextFill(Color.WHITE);
        movingBarCheckBox.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        speedZonesCheckBox = new CheckBox("Speed Zones");
        speedZonesCheckBox.setTextFill(Color.WHITE);
        speedZonesCheckBox.setFont(new Font(FONT_FAMILY, FONT_SIZE));

        Label name1Label = createLabel("Player 1");
        name2Label = createLabel("Player 2");
        Label goalLabel = createLabel("Score to win");
        Label opponentLabel = createLabel("Opponent");
        Label modeLabel = createLabel("Mode");
        Label arenaLabel = createLabel("Arena");

        opponentChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isPlayer = (newVal == OpponentType.PLAYER);
            name2Label.setVisible(isPlayer);
            name2Field.setVisible(isPlayer);
        });

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        errorLabel.setVisible(false);
        errorLabel.setWrapText(true);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> sceneManager.showMainMenu());
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> startGame(sceneManager));
        HBox buttonBox = new HBox(SPACING, backButton, startButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox namesBox = new HBox(SPACING,
                new VBox(5, name1Label, name1Field),
                new VBox(5, name2Label, name2Field)
        );
        namesBox.setAlignment(Pos.CENTER);

        HBox goalOpponentBox = new HBox(SPACING,
                new VBox(5, goalLabel, goalField),
                new VBox(5, opponentLabel, opponentChoiceBox)
        );
        goalOpponentBox.setAlignment(Pos.CENTER);

        HBox modeArenaBox = new HBox(SPACING,
                new VBox(5, modeLabel, modeChoiceBox),
                new VBox(5, arenaLabel, new VBox(5, circlesCheckBox, movingBarCheckBox, speedZonesCheckBox))
        );
        modeArenaBox.setAlignment(Pos.CENTER);

        VBox form = new VBox(SPACING, namesBox, goalOpponentBox, modeArenaBox, errorLabel, buttonBox);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(700);

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
        field.setPrefWidth(FIELD_WIDTH);
        field.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        return field;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font(FONT_FAMILY, FONT_SIZE));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private <T> ChoiceBox<T> createChoiceBox(T[] values, T defaultValue) {
        ChoiceBox<T> box = new ChoiceBox<>();
        box.getItems().addAll(values);
        box.setValue(defaultValue);
        box.setPrefWidth(FIELD_WIDTH);
        return box;
    }

    private void startGame(SceneManager sceneManager) {
        String name1 = name1Field.getText().trim();
        String name2 = name2Field.getText().trim();
        String goalText = goalField.getText().trim();
        OpponentType opponentType = opponentChoiceBox.getValue();
        GameMode gameMode = modeChoiceBox.getValue();

        String error = MatchSettingsValidator.validate(name1, name2, goalText, opponentType);
        if (error != null) {
            errorLabel.setText(error);
            errorLabel.setVisible(true);
            return;
        }

        String player2Name = (opponentType == OpponentType.PLAYER) ? name2 : "Bot";
        int goal = Integer.parseInt(goalText);

        ControlType controlType = GameControlsSettings.getPlayer2Control();
        Arena arena = new CustomArena(GAME_WIDTH, GAME_HEIGHT, circlesCheckBox.isSelected() ? 3 : 0, movingBarCheckBox.isSelected(), speedZonesCheckBox.isSelected());
        GameConfig config = new GameConfig(GAME_WIDTH, GAME_HEIGHT, name1, player2Name, goal,opponentType, gameMode, controlType, arena);

        sceneManager.showGame(config);
    }

    public Scene getScene() { return scene; }
}