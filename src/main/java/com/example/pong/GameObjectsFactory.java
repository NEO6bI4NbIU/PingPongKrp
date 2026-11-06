package com.example.pong;

public class GameObjectsFactory {
    private static final double RACQUET_MARGIN = 10;

    public static Ball createBall(double width, double height) {
        return new Ball(width / 2, height / 2);
    }

    public static Racquet createLeftRacquet(double height) {
        double minY = Wall.getHeight();
        double maxY = height - Wall.getHeight();
        double startY = height / 2 - Racquet.DEFAULT_HEIGHT / 2;
        return new Racquet(RACQUET_MARGIN, startY, minY, maxY);
    }

    public static Racquet createRightRacquet(double width, double height) {
        double minY = Wall.getHeight();
        double maxY = height - Wall.getHeight();
        double startY = height / 2 - Racquet.DEFAULT_HEIGHT / 2;
        return new Racquet(width - RACQUET_MARGIN - Racquet.DEFAULT_WIDTH, startY, minY, maxY);
    }

    public static Wall createUpperWall(double width) {
        return new Wall(0, width);
    }

    public static Wall createLowerWall(double width, double height) {
        return new Wall(height - Wall.getHeight(), width);
    }

    public static RacquetController createLeftController() {
        return new PlayerController(true);
    }

    public static RacquetController createRightController(GameConfig config, InputHandler input) {
        if (config.getOpponentType() == OpponentType.BOT) {
            return new BotController(config.getHeight());
        }
        return switch (config.getControlType()) {
            case SCROLL -> new ScrollController(input);
            default -> new PlayerController(false);
        };
    }
}
