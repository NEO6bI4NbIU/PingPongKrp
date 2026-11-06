package com.example.pong;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class CardSelectionScene {
    private final Scene scene;

    public CardSelectionScene(SceneManager sceneManager) {
        Label title = new Label("Select Cards for Match");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.WHITE);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        List<CheckBox> checkBoxes = new ArrayList<>();
        int col = 0, row = 0;

        for (CardType card : CardType.values()) {
            ImageView icon = new ImageView();
            try {
                icon.setImage(new Image(getClass().getResourceAsStream("/icons/" + card.getIconPath())));
                icon.setFitWidth(40);
                icon.setFitHeight(40);
            } catch (Exception e) {
            }
            CheckBox cb = new CheckBox(card.getDisplayName());
            cb.setSelected(GameCardSettings.isCardEnabled(card));
            cb.setTextFill(Color.WHITE);
            cb.setFont(new Font("Arial", 18));

            HBox cardRow = new HBox(10, icon, cb);
            cardRow.setAlignment(Pos.CENTER_LEFT);
            checkBoxes.add(cb);
            grid.add(cardRow, col, row);
            col++;
            if (col > 1) { col = 0; row++; }
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            // Сохраняем выбор в глобальные настройки
            for (int i = 0; i < checkBoxes.size(); i++) {
                GameCardSettings.setCardEnabled(CardType.values()[i], checkBoxes.get(i).isSelected());
            }
            sceneManager.showMainMenu();
        });

        Button selectAll = new Button("Select All");
        selectAll.setOnAction(e -> checkBoxes.forEach(cb -> cb.setSelected(true)));

        HBox buttons = new HBox(20, selectAll, backButton);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(30, title, grid, buttons);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#777B7E"), CornerRadii.EMPTY, Insets.EMPTY)));

        this.scene = new Scene(root, 854, 480);
    }

    public Scene getScene() { return scene; }
}