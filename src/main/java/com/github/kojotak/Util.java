package com.github.kojotak;

import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

class Util {

    static <T> java.util.Set<T> uniqueSetOrFail(List<T> list) {
        var result = new HashSet<T>();
        for (T item : list) {
            if (!result.add(item)) {
                throw new IllegalStateException("Found duplicate item " + item);
            }
        }
        return result;
    }

    @Nullable
    static <T, F> T uniqueOrFail(List<F> list, Function<F,@Nullable T> mapper) {
        var distinct = list.stream().map(mapper).filter(Objects::nonNull).distinct().toList();
        if (distinct.size() != 1) {
            throw new IllegalStateException("Non unique " + distinct);
        }
        return distinct.getFirst();
    }

    static List<Card> difference(List<Card> origin, List<Card> toSubtract) {
        var result = new LinkedList<>(origin);
        for(Card card : toSubtract) {
            result.remove(card);
        }
        result.sort(Card::compareTo);
        return result;
    }
}
