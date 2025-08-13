package com.github.kojotak;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cards holding by one player
 */
public class Hand {

    private final int minimumPoints;
    private final List<Card> cards = new LinkedList<>();

    public Hand(int minimumPoints, List<Card> cards) {
        this.minimumPoints = minimumPoints;
        this.cards.addAll(cards);
        this.cards.sort(Card::compareTo);
    }

    public List<Card> getCards(){
        return cards;
    }

    @Override
    public String toString() {
        return  "[" + cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(",")) + "]";
    }

    public List<LayOut> getLayOuts(){
        if (cards.isEmpty()) {
            return List.of();
        }
        var nonJokers = cards.stream()
                .filter(c -> !c.isJoker())
                .toList();
        if (nonJokers.size() < Meld.MINIMUM_LENGTH) {
            return List.of();
        }

        var bySuit = nonJokers.stream()
                .collect(Collectors.groupingBy(Card::suit));

        var layOuts = new ArrayList<LayOut>();
        for (var entry : bySuit.entrySet()) {
            var cardsOfSuit = entry.getValue();

            // Group cards by rank (using the enum ordinal for consecutiveness)
            var byRank = cardsOfSuit.stream()
                    .collect(Collectors.groupingBy(c -> c.rank().ordinal()));

            // Sorted unique ranks available for this suit
            var sortedRanks = byRank.keySet().stream().sorted().toList();

            int i = 0;
            while (i < sortedRanks.size()) {
                int start = i;
                int prev = sortedRanks.get(i);
                i++;
                while (i < sortedRanks.size() && sortedRanks.get(i) == prev + 1) {
                    prev = sortedRanks.get(i);
                    i++;
                }
                int end = i; // exclusive

                int blockLen = end - start;
                if (blockLen >= Meld.MINIMUM_LENGTH) {
                    // Generate all contiguous sub-layOuts of length >= 3 within this block
                    for (int s = start; s <= end - Meld.MINIMUM_LENGTH; s++) {
                        for (int e = s + Meld.MINIMUM_LENGTH; e <= end; e++) {
                            List<Card> cleanRun = new ArrayList<>(e - s);
                            for (int idx = s; idx < e; idx++) {
                                int rank = sortedRanks.get(idx);
                                // Pick one representative card for this rank
                                cleanRun.add(byRank.get(rank).get(0));
                            }
                            var remaining = Util.difference(cards, cleanRun);
                            var layout = new LayOut(new Run(cleanRun), remaining);
                            if(layout.getPoints() >= minimumPoints){
                                layOuts.add(layout);
                            }
                        }
                    }
                }
            }
        }
        return layOuts;
    }
}
