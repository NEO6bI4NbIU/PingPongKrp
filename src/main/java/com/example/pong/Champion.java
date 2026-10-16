package com.example.pong;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Champion {  // проверяет условие победы и показывает сообщение

    private final int goal;
    private final ScoreLogic scoreLogic;
    private final String player1Name;
    private final String player2Name;
    private final Runnable onGameOver;
    private final Runnable onPlayAgain;
    private final Runnable onMainMenu;

    private boolean gameOver = false;

    public Champion(int goal, ScoreLogic scoreLogic,String player1Name, String player2Name, Runnable onGameOver, Runnable onPlayAgain, Runnable onMainMenu) {
        this.goal = goal;
        this.scoreLogic = scoreLogic;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.onGameOver = onGameOver;
        this.onPlayAgain = onPlayAgain;
        this.onMainMenu = onMainMenu;
    }

    public boolean check() {
        if (gameOver) return true;

        int score1 = scoreLogic.getScore1();
        int score2 = scoreLogic.getScore2();

        if (score1 >= goal) {
            declareWinner(player1Name);
            return true;
        } else if (score2 >= goal) {
            declareWinner(player2Name);
            return true;
        }

        return false;
    }

    private void declareWinner(String winnerName) {
        gameOver = true;

        Platform.runLater(() -> {
            onGameOver.run();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(winnerName + " wins!");

            ButtonType playAgain = new ButtonType("Play Again");
            ButtonType mainMenu = new ButtonType("Main Menu");
            ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(playAgain, mainMenu, exit);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == playAgain) {
                    onPlayAgain.run();
                } else if (result.get() == mainMenu) {
                    onMainMenu.run();
                } else {
                    System.exit(0);
                }
            }
        });
    }

    public void reset() {gameOver = false;}
}