package com.github.kojotak;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck implements CardCarrier {

    private final LinkedList<Card> cards = new LinkedList<>();
    private final Config config;

    public Deck(Config config) {
        this.config = config;
        for (int i = 0; i < config.packs(); i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    cards.add(new Card(rank, suit));
                }
            }
            if(config.useJokers()){
                cards.add(Card.JOKER);
                cards.add(Card.JOKER);
            }
        }
        Collections.shuffle(cards);
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    public Hand drawHand() {
        var hand = new Hand();
        for (int i = 0; i < config.cards(); i++) {
            hand.getCards().add(cards.poll());
        }
        return hand;
    }

}
