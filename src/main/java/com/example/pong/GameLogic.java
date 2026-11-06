package com.example.pong;

import javafx.scene.layout.Pane;

public class GameLogic { // связывает все компоненты игры и управляет ими

    private final double width;
    private final double height;
    private boolean isArcade;
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
    private final Arena arena;

    private final CardManager cardManager;
    private final Pane gameField;
    private final CardController cardController;
    private final ActiveEffectManager effectManager;
    private final GoalHandler goalHandler;
    private final GameRestarter restarter;
    private final boolean useCards;

    public GameLogic(GameConfig config, SceneManager sceneManager, Pane gameField) {
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.isArcade = (config.getGameMode() == GameMode.ARCADE);
        this.arena = config.getArena();

        this.ball = GameObjectsFactory.createBall(width, height);
        this.leftRacquet = GameObjectsFactory.createLeftRacquet(height);
        this.rightRacquet = GameObjectsFactory.createRightRacquet(width, height);
        this.upperWall = GameObjectsFactory.createUpperWall(width);
        this.lowerWall = GameObjectsFactory.createLowerWall(width, height);

        this.collisionSystem = new CollisionSystem();
        this.inputHandler = new InputHandler();
        this.leftController = GameObjectsFactory.createLeftController();
        this.rightController = GameObjectsFactory.createRightController(config, inputHandler);
        this.scoreLogic = new ScoreLogic();
        this.scoreboard = new Scoreboard(config.getPlayer1Name(), config.getPlayer2Name());
        this.gameState = new GameState();
        this.gameLoop = new GameLoop(this);
        this.serveLogic = new ServeLogic(width, height, ball, leftRacquet, rightRacquet, gameState, inputHandler);
        this.gameField = gameField;
        this.useCards = config.isUseCards();
        this.cardManager = new CardManager(gameState, scoreboard, gameField);
        this.cardController = new CardController(inputHandler, this);
        this.effectManager = new ActiveEffectManager();
        this.restarter = new GameRestarter(scoreLogic, gameState, ball, leftRacquet, rightRacquet ,collisionSystem, arena, serveLogic, cardManager, effectManager, scoreboard, gameLoop);
        this.champion = new Champion(config.getGoal(), scoreLogic, config.getPlayer1Name(), config.getPlayer2Name(), this::stop, () -> restarter.restart(), sceneManager::showMainMenu);
        this.restarter.setChampion(champion);
        this.goalHandler = new GoalHandler(scoreLogic, scoreboard, champion, serveLogic, collisionSystem, gameState, ball, width);
        serveLogic.prepareFirstServe();
    }

    public void start() { gameLoop.start(); }

    public void stop() { gameLoop.stop(); }

    public void update(double deltaTime) {
        leftController.update(leftRacquet, ball, inputHandler.getKeysPressed(), deltaTime);
        rightController.update(rightRacquet, ball, inputHandler.getKeysPressed(), deltaTime);
        if (gameState.isServing()) {
            serveLogic.handleServe(leftController, rightController, inputHandler);
            return;
        }

        ball.move(deltaTime);
        arena.update(deltaTime);
        if (useCards) {
            cardManager.update(deltaTime, width, height, ball);
            cardController.update();
            effectManager.update((long) (deltaTime * 1000), this);
        }
        boolean hitRacquet = collisionSystem.check(ball, upperWall, lowerWall, leftRacquet, rightRacquet, arena.getObstacles(), arena.getZones(), gameState);
        if (hitRacquet && isArcade) ball.speedUp();

        if (goalHandler.checkGoal()) return;
    }

    public void activateCard(int player) {
        CardType card = gameState.getPlayerCard(player);
        if (card == null) return;
        if (gameState.isServing() && card == CardType.FREEZE_BALL) {
            return; // не замораживаем мяч во время подачи
        }
        card.apply(this, player); // применить эффект
        gameState.setPlayerCard(player, null); // карта использована
        effectManager.add(card, player);
        scoreboard.setCard(player, null);
    }

    public Racquet getRacquet(int player) {
        return (player == 1) ? leftRacquet : rightRacquet;
    }

    public void swapRacquetPositions() {
        double tempY = leftRacquet.getY();
        leftRacquet.setY(rightRacquet.getY());
        rightRacquet.setY(tempY);
    }

    public boolean isBotPlayer(int player) {
        return player == 2 && rightController instanceof BotController;
    }

    public Ball getBall() { return ball; }
    public Racquet getLeftRacquet() { return leftRacquet; }
    public Racquet getRightRacquet() { return rightRacquet; }
    public Wall getUpperWall() { return upperWall; }
    public Wall getLowerWall() { return lowerWall; }
    public Scoreboard getScoreboard() { return scoreboard; }
    public InputHandler getInputHandler() { return inputHandler; }
}