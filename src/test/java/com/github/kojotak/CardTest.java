package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.kojotak.Card.*;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    public void compareToTest() {
        List<Card> cards = Arrays.asList(D7, S6, H5, JOKER, C4, D8, S4, JOKER, HA, HK, HA, HQ, JOKER, D7);
        cards.sort(Card::compareTo);
        assertEquals(List.of(JOKER, JOKER, JOKER, H5, HQ, HK, HA, HA, C4, D7, D7, D8, S4, S6), cards);
    }
}