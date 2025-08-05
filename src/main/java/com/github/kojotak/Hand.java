package com.github.kojotak;

import java.util.LinkedList;
import java.util.List;

/**
 * Cards holding by one player
 */
public class Hand implements CardCarrier {

    private final LinkedList<Card> cards = new LinkedList<>();

    public List<Card> getCards(){
        return cards;
    }
}
