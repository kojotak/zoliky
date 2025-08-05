package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static com.github.kojotak.Util.uniqueSetOrFail;
import static com.github.kojotak.Util.uniqueOrFail;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    public void noDuplicates() {
        var result = uniqueSetOrFail(List.of(1, 2, 3));
        assertEquals(java.util.Set.of(1, 2, 3), result);
    }

    @Test
    public void noDuplicatesAllowsNull() {
        var result = uniqueSetOrFail(asList(42, null));
        assertTrue(result.contains(null));
        assertTrue(result.contains(42));
    }

    @Test
    public void duplicatesThrowAnException() {
        assertThrows(IllegalStateException.class, () -> uniqueSetOrFail(asList(1, 1)));
    }

    @Test
    public void uniqueFromList() {
        var result = uniqueOrFail(asList(1, 1, 1), Function.identity());
        assertEquals(1, result);
    }

    @Test
    public void nonUniqueThrowAnException() {
        assertThrows(IllegalStateException.class, () -> uniqueOrFail(asList(1, 2, 3), Function.identity()));
    }

    @Test
    public void nonUniqueWithNullsThrowAnException() {
        assertThrows(IllegalStateException.class, () -> uniqueOrFail(asList(1, null, 2, null, 3), Function.identity()));
    }
}