package com.github.kojotak;

import java.util.List;
import java.util.stream.Collectors;

public record LayOut(Run cleanRun, List<Card> dump, Meld... melds) {

    @Override
    public String toString() {
        var builder = new StringBuilder(cleanRun.toString());
        if (melds != null) {
            for (var meld : melds) {
                builder.append(" ").append(meld);
            }
        }
        if (!dump.isEmpty()) {
            builder.append(" {");
            builder.append(dump.stream().map(Card::toString).collect(Collectors.joining(",")));
            builder.append("}");
        }
        return builder.toString();
    }

    int getPoints() {
        var result = cleanRun.getPoints();
        if (melds != null) {
            for (var meld : melds) {
                result += meld.getPoints();
            }
        }
        return result;
    }
}
