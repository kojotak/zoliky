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

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "[" + cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(",")) + "]";
    }

    public List<LayOut> getLayOuts() {
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
                            var remainingMelds = getRemainingMelds(Util.difference(cards, cleanRun));
                            for(var remaining : remainingMelds){
                                var layOut = new LayOut(new Run(cleanRun), remaining.melds, remaining.remaining);
                                if (layOut.points() >= minimumPoints) {
                                    layOuts.add(layOut);
                                }
                            }
                        }
                    }
                }
            }
        }
        return layOuts;
    }

    private List<Remaining> getRemainingMelds(List<Card> cards) {
        var result = new ArrayList<Remaining>();

        // No melds, just dump the cards
        result.add(new Remaining(List.of(), cards));

        if (cards.isEmpty())
            return result;

        // Split jokers and non-jokers
        List<Card> jokers = cards.stream().filter(Card::isJoker).toList();
        List<Card> nonJokers = cards.stream().filter(c -> !c.isJoker()).toList();

        // --- Find all possible SETS (same rank, 3-4 cards, different suits; use jokers if needed) ---
        // Group non-jokers by rank
        var byRank = nonJokers.stream().collect(Collectors.groupingBy(Card::rank));
        for (var entry : byRank.entrySet()) {
            var thisRankCards = entry.getValue();
            int need = Meld.MINIMUM_LENGTH - thisRankCards.size();
            if (need <= jokers.size() && thisRankCards.size() + jokers.size() >= Meld.MINIMUM_LENGTH && thisRankCards.size()<4) {
                // Can create a set with jokers. Try all possible joker combinations (amount inserted)
                for (int use = Math.max(1, need); use <= Math.min(4 - thisRankCards.size(), jokers.size()); use++) {
                    List<Card> set = new ArrayList<>(thisRankCards);
                    set.addAll(jokers.subList(0, use));
                    // Remove these from hand
                    List<Card> remainingCards = new ArrayList<>(cards);
                    set.forEach(remainingCards::remove);
                    // Recurse
                    for (Remaining rem : getRemainingMelds(remainingCards)) {
                        List<Meld> meldPath = new ArrayList<>();
                        meldPath.addAll(rem.melds);
                        meldPath.add(new Set(set)); // our new set
                        result.add(new Remaining(meldPath, rem.remaining));
                    }
                }
            }
            // Add pure sets (if any)
            if (thisRankCards.size() >= Meld.MINIMUM_LENGTH) {
                List<Card> set = new ArrayList<>(thisRankCards.subList(0, Math.min(thisRankCards.size(), 4)));
                List<Card> remainingCards = Util.difference(cards, set);
                for (Remaining rem : getRemainingMelds(remainingCards)) {
                    List<Meld> meldPath = new ArrayList<>();
                    meldPath.addAll(rem.melds);
                    meldPath.add(new Set(set));
                    result.add(new Remaining(meldPath, rem.remaining));
                }
            }
        }

        // --- Find all possible RUNS (consecutive, same suit, 3+; allow jokers as gaps) ---
        // Group non-jokers by suit
        var bySuit = nonJokers.stream().collect(Collectors.groupingBy(Card::suit));
        for (var entry : bySuit.entrySet()) {
            var suit = entry.getKey();
            var suitCards = entry.getValue();
            if (suitCards.isEmpty())
                continue;

            // Build sorted list of ranks present
            var sortedCards = suitCards.stream().sorted().toList();
            var sortedRanks = sortedCards.stream().map(Card::rank).map(Enum::ordinal).toList();

            // Try every block start; end block must form a true consecutive run (with possible joker fill-ins)
            for (int start = 0; start < sortedRanks.size(); start++) {
                for (int end = start + Meld.MINIMUM_LENGTH - 1; end < sortedRanks.size(); end++) {
                    int blockStartOrdinal = sortedRanks.get(start);
                    int blockEndOrdinal = sortedRanks.get(end);
                    int runLength = blockEndOrdinal - blockStartOrdinal + 1;
                    int numCards = end - start + 1;
                    int jokersNeeded = runLength - numCards;
                    if (jokersNeeded < 0 || jokersNeeded > jokers.size()) {
                        continue;
                    }
                    // Verify all intermediate slots between start and end are filled by cards or jokers
                    List<Integer> runOrdinals = new ArrayList<>(sortedRanks.subList(start, end + 1));
                    List<Card> runCore = sortedCards.subList(start, end + 1);
                    List<Card> run = new ArrayList<>();
                    int jokerIdx = 0;
                    boolean valid = true;
                    int idxInCore = 0;
                    for (int rk = blockStartOrdinal; rk <= blockEndOrdinal; rk++) {
                        if (idxInCore < runOrdinals.size() && runOrdinals.get(idxInCore) == rk) {
                            run.add(runCore.get(idxInCore));
                            idxInCore++;
                        } else if (jokerIdx < jokersNeeded) {
                            run.add(jokers.get(jokerIdx));
                            jokerIdx++;
                        } else {
                            valid = false;
                            break;
                        }
                    }
                    if (valid && run.size() >= Meld.MINIMUM_LENGTH) {
                        // Remove the run's cards and its jokers
                        List<Card> removeRun = run.stream().toList();
                        List<Card> remainingCards = new ArrayList<>(cards);
                        for (Card rc : removeRun) {
                            remainingCards.remove(rc);
                        }
                        for (Remaining rem : getRemainingMelds(remainingCards)) {
                            List<Meld> meldPath = new ArrayList<>();
                            meldPath.addAll(rem.melds);
                            meldPath.add(new Run(run));
                            result.add(new Remaining(meldPath, rem.remaining));
                        }
                    }
                }
            }
        }

        // -- Additional: try all runs formed by majority jokers (e.g. 2 jokers + a natural card)
        // This handling is optional but can catch joker-joker-natural minimal runs missing from above
        if (jokers.size() >= 1 && nonJokers.size() >= 1) {
            // Try using jokers with each non-joker as a run
            for (Card nat : nonJokers) {
                for (int use = 1; use <= Math.min(jokers.size(), 4 - 1); use++) {
                    List<Card> run = new ArrayList<>();
                    run.add(nat);
                    run.addAll(jokers.subList(0, use));
                    List<Card> remaining = new ArrayList<>(cards);
                    run.forEach(remaining::remove);
                    if (run.size() >= Meld.MINIMUM_LENGTH) {
                        for (Remaining rem : getRemainingMelds(remaining)) {
                            List<Meld> meldPath = new ArrayList<>();
                            meldPath.addAll(rem.melds);
                            meldPath.add(new Run(run));
                            result.add(new Remaining(meldPath, rem.remaining));
                        }
                    }
                }
            }
        }

        return result;
    }

    record Remaining(
            List<Meld> melds,
            List<Card> remaining
    ) {
    }
}
