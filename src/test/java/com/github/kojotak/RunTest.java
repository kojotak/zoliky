package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.github.kojotak.Card.*;
import static org.junit.jupiter.api.Assertions.*;

class RunTest {

    @Test
    public void runOfThreeFromFive() {
        var run = new Run(H5, H6, H7);
        assertEquals(18, run.getPoints());
    }

    @Test
    public void runOfFromNineToAce() {
        var run = new Run(StandardSuit.SPADE, List.of(Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE));
        assertEquals(59, run.getPoints());
    }

    @Test
    public void illegalRunOfTwo() {
        assertThrows(IllegalStateException.class, () -> new Run(StandardSuit.DIAMOND, List.of(Rank.FIVE, Rank.SIX)));
    }

    @Test
    public void illegalRunOfNonConsecutive() {
        assertThrows(IllegalStateException.class, () -> new Run(StandardSuit.DIAMOND, List.of(Rank.FIVE, Rank.SEVEN, Rank.EIGHT)));
    }

    @Test
    public void aceCanBeUsedAsOne() {
        var run = new Run(StandardSuit.SPADE, List.of(Rank.ACE, Rank.TWO, Rank.THREE));
        assertEquals(6, run.getPoints());
    }

    @Test
    public void aceCanNotBeUsedInTheMiddle() {
        assertThrows(IllegalStateException.class, () -> new Run(StandardSuit.SPADE, List.of(Rank.KING, Rank.ACE, Rank.TWO)));
    }

    @Test
    public void runFromThreeCards() {
        var run = new Run(List.of(C9, C10, CJ));
        assertEquals(29, run.getPoints());
    }

    @Test
    public void runFromThreeCardsWithJoker() {
        var run = new Run(List.of(C9, jokerFor(Rank.TEN), CJ));
        assertEquals(29, run.getPoints());
    }

    @Test
    public void runFromThreeCardsWithJokerAtTheEnd() {
        var run = new Run(List.of(C9, C10, jokerFor(Rank.JACK)));
        assertEquals(29, run.getPoints());
    }

    @Test
    public void runFromThreeCardsWithJokerAtTheBeginning() {
        var run = new Run(List.of(jokerFor(Rank.NINE), C10, CJ));
        assertEquals(29, run.getPoints());
    }

    @Test
    public void runWithTwoConsecutiveJokers() {
        assertThrows(IllegalStateException.class, () -> new Run(List.of(C10, jokerFor(Rank.TEN), jokerFor(Rank.JACK), CQ, CK)));
    }

    @Test
    public void runWithTwoJokers() {
        var run = new Run(List.of(C9, C10, CJ, jokerFor(Rank.QUEEN), CK));
        assertEquals(49, run.getPoints());
    }

    @Test
    public void runWithAllRanks() {
        var run = new Run(StandardSuit.HEART, EnumSet.allOf(Rank.class).stream().toList());
        assertEquals(2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 50, run.getPoints());
    }

    @Test
    public void runWithAllRanksIncludingOne() {
        var ranks = new ArrayList<Rank>();
        ranks.add(Rank.ACE); //acts as one
        ranks.addAll(EnumSet.allOf(Rank.class));
        var run = new Run(StandardSuit.HEART, ranks);
        assertEquals(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 50, run.getPoints());
    }
}