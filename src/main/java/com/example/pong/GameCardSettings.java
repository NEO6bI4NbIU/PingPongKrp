package com.example.pong;

import java.util.HashSet;
import java.util.Set;

public class GameCardSettings { // хранит настройки карт (Включенные карты)
    private static final Set<CardType> disabledCards = new HashSet<>();

    public static boolean isCardEnabled(CardType card) { return !disabledCards.contains(card); }
    public static void setCardEnabled(CardType card, boolean enabled) {
        if (enabled) disabledCards.remove(card);
        else disabledCards.add(card);
    }
}
