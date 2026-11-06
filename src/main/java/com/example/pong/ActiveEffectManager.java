package com.example.pong;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActiveEffectManager {
    private final List<ActiveEffect> effects = new ArrayList<>();

    public void add(CardType card, int player) {
        if (!card.isInstant()) {
            effects.add(new ActiveEffect(card, player, card.getDurationMs()));
        }
    }

    public void update(long deltaMs, GameLogic game) {
        Iterator<ActiveEffect> it = effects.iterator();
        while (it.hasNext()) {
            ActiveEffect effect = it.next();
            if (effect.update(deltaMs)) {
                effect.getCard().revert(game, effect.getPlayer());
                it.remove();
            }
        }
    }

    public void clear() { effects.clear(); }

    private static class ActiveEffect {
        private final CardType card;
        private final int player;
        private long remainingMs;

        ActiveEffect(CardType card, int player, long durationMs) {
            this.card = card;
            this.player = player;
            this.remainingMs = durationMs;
        }

        boolean update(long deltaMs) {
            remainingMs -= deltaMs;
            return remainingMs <= 0;
        }

        CardType getCard() { return card; }
        int getPlayer() { return player; }
    }
}
