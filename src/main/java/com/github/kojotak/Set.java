package com.github.kojotak;

import java.util.EnumSet;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public record Set(

        Rank rank, EnumSet<Suit> suits

) implements Meld {

    public Set(List<Card> cards){
        this(uniqueRankOrFail(cards),
             cards.stream().map(Card::suit).collect(collectingAndThen(toSet(), EnumSet::copyOf))
        );
    }

    public Set {
        if (suits == null || suits.size() < MINIMUM_LENGTH) {
            throw new IllegalStateException("Illegal set - minimum length violated");
        }
    }

    private static Rank uniqueRankOrFail(List<Card> cards) {
        var ranks = cards.stream().map(Card::rank).distinct().toList();
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
