package com.example.pong;

public enum CardType implements CardEffect {
    INCREASE_RACQUET("Увеличить ракетку", "increase.png", 5000) {
        @Override
        public void apply(GameLogic game, int player) {
            game.getRacquet(player).increaseHeight(20);
        }
        @Override
        public void revert(GameLogic game, int player) {
            game.getRacquet(player).decreaseHeight(20);
        }
    },
    SLOW_OPPONENT("Замедлить врага", "slow.png", 3000) {
        @Override
        public void apply(GameLogic game, int player) {
            game.getRacquet(3 - player).setSpeedMultiplier(0.5);
        }
        @Override
        public void revert(GameLogic game, int player) {
            game.getRacquet(3 - player).setSpeedMultiplier(1);
        }
    },
    SHRINK_OPPONENT("Уменьшить врага", "shrink.png", 5000) {
        public void apply(GameLogic game, int player) {
            game.getRacquet(3 - player).decreaseHeight(20);
        }
        public void revert(GameLogic game, int player) {
            game.getRacquet(3 - player).increaseHeight(20);
        }
    },
    FREEZE_BALL("Заморозка мяча", "freeze.png", 1500) {
        public void apply(GameLogic game, int player) {
            Ball ball = game.getBall();
            ball.saveVelocity();
            ball.stop();
        }
        public void revert(GameLogic game, int player) {
            Ball ball = game.getBall();
            ball.restoreVelocity();
        }

    },
    SWAP_RACQUETS("Поменять местами", "swap.png", 0) {
        public void apply(GameLogic game, int player) {
            game.swapRacquetPositions();
        }
        public void revert(GameLogic game, int player) { }
    };

    private final String displayName;
    private final String iconPath;
    private final long durationMs; // 0 если моментальная

    CardType(String displayName, String iconPath, long durationMs) {
        this.displayName = displayName;
        this.iconPath = iconPath;
        this.durationMs = durationMs;
    }


    public String getDisplayName() { return displayName; }
    public String getIconPath() { return iconPath; }
    public long getDurationMs() { return durationMs; }
    public boolean isInstant() { return durationMs == 0; }
}
