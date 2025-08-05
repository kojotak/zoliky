package com.github.kojotak;

import java.util.List;

import static com.github.kojotak.Util.*;
import static java.util.stream.Collectors.toList;

public record Set(

        Rank rank, java.util.Set<Suit> suits

) implements Meld {

    public Set(List<Card> cards){
        this(uniqueOrFail(cards, Card::rank),
             uniqueSetOrFail(cards.stream().map(Card::suit).collect(toList()))
        );
    }

    public Set {
        if (suits == null || suits.size() < MINIMUM_LENGTH) {
            throw new IllegalStateException("Illegal set - minimum length violated");
        }
    }

    @Override
    public List<Card> getCards() {
        return suits.stream().map( suit -> new Card(rank, suit)).toList();
    }

    @Override
    public int getPoints() {
        return rank.getPoints() * suits.size();
    }
}
