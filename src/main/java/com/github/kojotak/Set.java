package com.github.kojotak;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.kojotak.Util.*;

public record Set(List<Card> cards) implements Meld {

    public Set(Card ... cards){
        this(List.of(cards));
    }

    public Set {
        uniqueOrFail(cards, Card::rank);
        uniqueSetOrFail(cards.stream().map(Card::suit).filter(StandardSuit.class::isInstance).toList());
        if (cards.size() < MINIMUM_LENGTH) {
            throw new IllegalStateException("Illegal set - minimum length violated");
        }
        cards = cards.stream().sorted().toList();
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
                .map(suit -> Objects.toString(suit, "*"))
                .collect(Collectors.joining(",")) + "]";
    }
}
