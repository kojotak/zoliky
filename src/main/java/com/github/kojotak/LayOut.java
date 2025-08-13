package com.github.kojotak;


import java.util.List;
import java.util.stream.Collectors;

//we cannot "hide" precomputed points attribute into java's record
public record LayOut(Run cleanRun, List<Meld> melds, List<Card> dump, int points) {

    public LayOut(Run cleanRun, List<Meld> melds, List<Card> dump) {
        this(cleanRun, melds, dump, computePoints(cleanRun, melds));
    }

    @Override
    public String toString() {
        var builder = new StringBuilder(cleanRun.toString());
        for (var meld : melds) {
            builder.append(" ").append(meld);
        }
        if (!dump.isEmpty()) {
            builder.append(" {");
            builder.append(dump.stream().map(Card::toString).collect(Collectors.joining(",")));
            builder.append("}");
        }
        builder.append(" ").append(points).append(" points");
        return builder.toString();
    }

    private static int computePoints(Run cleanRun, List<Meld> melds) {
        var result = cleanRun.getPoints();
        for (var meld : melds) {
            result += meld.getPoints();
        }
        return result;
    }
}
