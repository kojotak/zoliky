package com.github.kojotak;

import java.util.EnumSet;
import java.util.List;

import static com.github.kojotak.Util.*;
import static java.util.stream.Collectors.toList;

public record Set(List<Card> cards) implements Meld, Ranked {

    public Set(Rank rank, EnumSet<Suit> suits) {
        this(suits.stream().map(suit -> new Card(rank, suit)).toList());
    }

    public Set {
        uniqueOrFail(cards, Card::rank);
        var suits = uniqueSetOrFail(cards.stream().map(Card::suit).collect(toList()));
        if (suits.size() < MINIMUM_LENGTH) {
            throw new IllegalStateException("Illegal set - minimum length violated");
        }
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public int getPoints() {
        return rank().getPoints() * cards.size();
    }

    @Override
    public Rank rank() {
        return uniqueOrFail(cards, Card::rank);
    }
}
