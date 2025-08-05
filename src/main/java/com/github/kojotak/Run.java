package com.github.kojotak;

import java.util.EnumSet;
import java.util.List;

import static com.github.kojotak.Util.uniqueOrFail;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public record Run (

        Suit suit, EnumSet<Rank> ranks

) implements Meld {

    public Run(List<Card> cards) {
        this(uniqueOrFail(cards, Card::suit),
            cards.stream().map(Card::rank)
                    .collect(collectingAndThen(toSet(), EnumSet::copyOf)));
    }

    public Run {
        if (ranks == null || ranks.size() < MINIMUM_LENGTH) {
            throw new IllegalStateException("Illegal run - minimum length violated");
        }
        var ordinals = ranks.stream().map(Rank::ordinal).sorted().toList();
        var allRanks = Rank.values();
        var ranksCount = allRanks.length;
        int predecessors = 0;
        int successors = 0;
        for (int index = 0; index < ordinals.size(); index++) {
            var ordinal = ordinals.get(index);
            var predecessor = ordinal > 0 ? allRanks[ordinal - 1] : allRanks[ranksCount-1];
            if(ranks.contains(predecessor)){
                predecessors += 1;
            }
            var successor = ordinal +1 < ranksCount ? allRanks[ordinal + 1] : allRanks[0];
            if (ranks.contains(successor)) {
                successors += 1;
            }
            if(Rank.ACE.equals(allRanks[ordinal])){
                if(ranks.contains(Rank.TWO) && ranks.contains(Rank.KING)){
                    throw new IllegalStateException("Illegal run - ace can not be in the middle");
                }
            }
        }
        if (predecessors != successors || predecessors != ordinals.size()-1 || successors != ordinals.size()-1) {
            throw new IllegalStateException("Illegal run - missing some predecessor or successor");
        }
    }

    @Override
    public List<Card> getCards() {
        return ranks.stream().map(rank -> new Card(rank, suit)).toList();
    }

    @Override
    public int getPoints() {
        var points = ranks.stream().reduce(0, (sum, rank) -> sum + rank.getPoints(), Integer::sum);
        if(ranks.contains(Rank.ACE) && !ranks.contains(Rank.KING) && ranks.contains(Rank.TWO)){
            points -= 9;
        }
        return points;
    }
}
