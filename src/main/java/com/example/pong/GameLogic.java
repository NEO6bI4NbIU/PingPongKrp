package com.example.pong;

import javafx.scene.input.KeyCode;

public class GameLogic { // связывает все компоненты игры и управляет ими

    private final double width;
    private final double height;
    private final int goal;
    private static final double RACQUET_MARGIN = 10;
    private final Ball ball;
    private final Racquet leftRacquet;
    private final Racquet rightRacquet;
    private final Wall upperWall;
    private final Wall lowerWall;
    private final CollisionSystem collisionSystem;
    private final RacquetController leftController;
    private final RacquetController rightController;
    private final InputHandler inputHandler;
    private final GameState gameState;
    private final GameLoop gameLoop;
    private final ScoreLogic scoreLogic;
    private final Scoreboard scoreboard;
    private final ServeLogic serveLogic;
    private final Champion champion;

    public GameLogic(GameConfig config, SceneManager sceneManager) {
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.goal = config.getGoal();
        double racquetMinY = Wall.getHeight();
        double racquetMaxY = height - Wall.getHeight();
        double racquetStartY = height / 2 - Racquet.DEFAULT_HEIGHT / 2;

        this.ball = new Ball(width / 2, height / 2);
        this.leftRacquet = new Racquet(RACQUET_MARGIN, racquetStartY, racquetMinY, racquetMaxY);
        this.rightRacquet = new Racquet(width - RACQUET_MARGIN - Racquet.DEFAULT_WIDTH, racquetStartY, racquetMinY, racquetMaxY);
        this.upperWall = new Wall(0, width);
        this.lowerWall = new Wall(height - Wall.getHeight(), width);

        this.collisionSystem = new CollisionSystem();
        this.inputHandler = new InputHandler();
        if (config.getMode() == GameMode.PVP) {
            this.leftController = new PlayerController(true);
            switch (config.getControlType()) {
                case SCROLL -> this.rightController = new ScrollController(inputHandler);
                default -> this.rightController = new PlayerController(false);
            }
        } else {
            this.leftController = new PlayerController(true);
            this.rightController = new BotController();
        }
        this.scoreLogic = new ScoreLogic();
        this.scoreboard = new Scoreboard(config.getPlayer1Name(), config.getPlayer2Name());
        this.gameState = new GameState();
        this.gameLoop = new GameLoop(this);
        this.serveLogic = new ServeLogic(width, height, ball, leftRacquet, rightRacquet, gameState, inputHandler);
        this.champion = new Champion(goal, scoreLogic, config.getPlayer1Name(), config.getPlayer2Name(), this::stop, this::restart, sceneManager::showMainMenu);


        serveLogic.prepareFirstServe();
    }

    public void start() {gameLoop.start();}

    public void stop() {gameLoop.stop();}

    public void update() {
        leftController.update(leftRacquet, ball, inputHandler.getKeysPressed());
        rightController.update(rightRacquet, ball, inputHandler.getKeysPressed());
        if (gameState.isServing()) {
            serveLogic.updateBallForServe();
            int server = gameState.getServingPlayer();
            if (server == 2 && rightController instanceof ScrollController) {
                serveLogic.handleServeInputByMouse();
            } else if (server == 2 && rightController instanceof BotController) {
                if (Math.random() < 0.02) {
                    serveLogic.handleServeInputByBot();
                }
            } else {
                KeyCode serveKey = getServeKey();
                if (serveKey != null) {
                    serveLogic.handleServeInput(serveKey);
                }
            }
            return;
        }

        ball.move();
        collisionSystem.check(ball, upperWall, lowerWall, leftRacquet, rightRacquet);

        int scorer = scoreLogic.update(ball, width);
        if (scorer != 0) {
            scoreboard.update(scoreLogic.getScore1(), scoreLogic.getScore2());
            if (champion.check()) {
                return;
            }
            serveLogic.prepareServe(scorer);
        }
    }

    private void restart() {
        scoreLogic.reset();
        gameState.reset();
        champion.reset();
        serveLogic.prepareFirstServe();
        scoreboard.update(0, 0);
        start();
    }

    private KeyCode getServeKey() {
        int server = gameState.getServingPlayer();

        if (server == 0) {
            return KeyCode.ENTER;
        } else if (server == 1) {
            if (leftController instanceof PlayerController player) {
                return player.getServeKey();
            }
        } else if (server == 2) {
            if (rightController instanceof PlayerController player) {
                return player.getServeKey();
            }
        }

        return null;  // бот подает автоматически
    }

    public Ball getBall() { return ball; }
    public Racquet getLeftRacquet() { return leftRacquet; }
    public Racquet getRightRacquet() { return rightRacquet; }
    public Wall getUpperWall() { return upperWall; }
    public Wall getLowerWall() { return lowerWall; }
    public Scoreboard getScoreboard() { return scoreboard; }
    public InputHandler getInputHandler() { return inputHandler; }
}