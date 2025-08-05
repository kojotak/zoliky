package com.github.kojotak;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.kojotak.Util.*;
import static java.util.stream.Collectors.toList;

public record Set(List<Card> cards) implements Meld {

    public Set(Rank rank, List<Suit> suits) {
        this(suits.stream().map(suit -> new Card(rank, suit)).toList());
    }

    public Set {
        uniqueOrFail(cards, Card::rank);
        var suits = uniqueSetOrFail(cards.stream().map(Card::suit).collect(toList()));
        if (suits.size() < MINIMUM_LENGTH) {
            throw new IllegalStateException("Illegal set - minimum length violated");
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public int getPoints() {
        return rank().getPoints() * cards.size();
    }

    public Rank rank() {
        return uniqueOrFail(cards, Card::rank);
    }

    @Override
    public String toString() {
        return rank() + "[" + cards.stream()
                .map(Card::suit)
                .map(suit -> suit != null ? suit.toString() : "*")
                .collect(Collectors.joining(",")) + "]";
    }
}
