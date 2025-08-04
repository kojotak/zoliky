package com.github.kojotak;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toSet;

public record Set(

        Rank rank, java.util.Set<Suit> suits

) implements Meld {

    public Set(List<Card> cards){
        this(uniqueRankOrFail(cards),
             cards.stream().map(Card::suit).collect(toSet())
        );
    }

    public Set {
        if (suits == null || suits.size() < MINIMUM_LENGTH) {
            throw new IllegalStateException("Illegal set - minimum length violated");
        }
    }

    private static Rank uniqueRankOrFail(List<Card> cards) {
        var ranks = cards.stream().map(Card::rank).distinct().filter(Objects::nonNull).toList();
        if (ranks.size() != 1) {
            throw new IllegalStateException("Illegal run");
        }
        return ranks.getFirst();
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
