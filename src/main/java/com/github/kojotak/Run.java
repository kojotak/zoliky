package com.github.kojotak;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.kojotak.Util.uniqueOrFail;

public record Run(List<Card> cards) implements Meld {

    public Run(Card ... cards){
        this(List.of(cards));
    }

    public Run(StandardSuit suit, List<Rank> ranks) {
        this(ranks.stream().map(r -> new Card(r, suit)).toList());
    }

    public Run {
        if (cards.size() < MINIMUM_LENGTH) {
            throw new IllegalStateException("Illegal run - minimum length violated");
        }
        var iterator = cards.listIterator();
        while (iterator.hasNext()) {
            Card previous = null;
            if (iterator.hasPrevious()) {
                previous = iterator.previous();
                iterator.next();
            }
            var current = iterator.next();
            if (previous == null) {
                continue;
            }
            if (Rank.ACE.equals(current.rank()) && Rank.KING.equals(previous.rank()) && iterator.hasNext()) {
                throw new IllegalStateException("Illegal run - ace can not be in the middle");
            }
            if (current.isJoker() && previous.isJoker()) {
                throw new IllegalStateException("Illegal run - jokers can not be consecutive");
            }
            var previousOrdinal = previous.rank().ordinal();
            var currentOrdinal = current.rank().ordinal();

            if (Rank.ACE.equals(previous.rank()) && Rank.TWO.equals(current.rank())) {
                previousOrdinal = -1;
            }
            if (previousOrdinal + 1 != currentOrdinal) {
                throw new IllegalStateException("Illegal run - missing some predecessor or successor for " + cards);
            }
        }

    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public int getPoints() {
        var ranks = cards.stream().map(Card::rank).toList();
        var points = ranks.stream().reduce(0, (sum, rank) -> sum + rank.getPoints(), Integer::sum);
        if (Rank.ACE.equals(cards.getFirst().rank())) {
            //ace at first position counts as one
            points -= 9;
        }
        return points;
    }

    public Enum<? extends Suit> suit() {
        return uniqueOrFail(cards, Card::suit);
    }

    @Override
    public String toString() {
        return suit() + "[" + cards.stream()
                .map(Card::rank)
                .map(Rank::toString)
                .collect(Collectors.joining(",")) + "]";
    }
}
