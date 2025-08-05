package com.github.kojotak;

import java.util.HashSet;
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

    static <T, F> T uniqueOrFail(List<F> list, Function<F,T> mapper) {
        var distinct = list.stream().map(mapper).filter(Objects::nonNull).distinct().toList();
        if (distinct.size() != 1) {
            throw new IllegalStateException("Non unique " + distinct);
        }
        return distinct.getFirst();
    }
}
