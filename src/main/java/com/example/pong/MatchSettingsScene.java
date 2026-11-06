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

public class MatchSettingsScene { // сцена с настройками матча
    private static final double WIDTH = 854;
    private static final double HEIGHT = 480;
    private static final double GAME_WIDTH = 854;
    private static final double GAME_HEIGHT = 480;
    private static final Color BG_COLOR = Color.web("#777B7E");

    private final Scene scene;
    private final TextField name1Field;
    private final TextField name2Field;
    private final TextField goalField;
    private final ChoiceBox<OpponentType> opponentChoiceBox;
    private final ChoiceBox<GameMode> modeChoiceBox;
    private final CheckBox circlesCheckBox;
    private final CheckBox movingBarCheckBox;
    private final CheckBox speedZonesCheckBox;
    private final CheckBox cardsCheckBox;
    private final Label errorLabel;
    private final Label name2Label;

    public MatchSettingsScene(SceneManager sceneManager) {
        name1Field = MatchSettingsFactory.createTextField("Player 1 name");
        name2Field = MatchSettingsFactory.createTextField("Player 2 name");
        goalField = MatchSettingsFactory.createTextField("Score to win");

        opponentChoiceBox = MatchSettingsFactory.createChoiceBox(OpponentType.values(), OpponentType.PLAYER, 400);
        modeChoiceBox = MatchSettingsFactory.createChoiceBox(GameMode.values(), GameMode.CLASSIC,140 );

        circlesCheckBox = MatchSettingsFactory.createCheckBox("Circles");
        movingBarCheckBox = MatchSettingsFactory.createCheckBox("Moving Bar");
        speedZonesCheckBox = MatchSettingsFactory.createCheckBox("Speed Zones");
        cardsCheckBox = MatchSettingsFactory.createCheckBox("Enable Cards");
        cardsCheckBox.setSelected(true);

        name2Label = MatchSettingsFactory.createLabel("Player 2");

        opponentChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isPlayer = (newVal == OpponentType.PLAYER);
            name2Label.setVisible(isPlayer);
            name2Field.setVisible(isPlayer);
        });

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(false);
        errorLabel.setWrapText(true);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> sceneManager.showMainMenu());
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> startGame(sceneManager));
        HBox buttonBox = new HBox(15, backButton, startButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox form = MatchSettingsFactory.createForm(name1Field, name2Field, goalField, opponentChoiceBox, modeChoiceBox,
                circlesCheckBox, movingBarCheckBox, speedZonesCheckBox, cardsCheckBox, name2Label, errorLabel, buttonBox);

        VBox root = new VBox(form);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(
                new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)
        ));

        this.scene = new Scene(root, WIDTH, HEIGHT);
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
        Arena arena = new CustomArena(GAME_WIDTH, GAME_HEIGHT, circlesCheckBox.isSelected() ? 6 : 0, movingBarCheckBox.isSelected(), speedZonesCheckBox.isSelected());
        GameConfig config = new GameConfig(GAME_WIDTH, GAME_HEIGHT, name1, player2Name, goal,opponentType, gameMode, controlType, arena, cardsCheckBox.isSelected());

        sceneManager.showGame(config);
    }

    public Scene getScene() { return scene; }
}