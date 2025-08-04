package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class RunTest {

    @Test
    public void runOfThreeFromFive(){
        var run = new Run(Suit.HEART, EnumSet.of(Rank.FIVE, Rank.SIX, Rank.SEVEN));
        assertEquals(3*6, run.getPoints());
    }

    @Test
    public void runOfFromNineToAce(){
        var run = new Run(Suit.SPADE, EnumSet.of(Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE));
        assertEquals(9 + 5*10, run.getPoints());
    }

    @Test
    public void illegalRunOfTwo(){
        assertThrows(IllegalStateException.class, () -> new Run(Suit.DIAMOND, EnumSet.of(Rank.FIVE, Rank.SIX)));
    }

    @Test
    public void illegalRunOfNonConsecutives(){
        assertThrows(IllegalStateException.class, () -> new Run(Suit.DIAMOND, EnumSet.of(Rank.FIVE, Rank.SEVEN, Rank.EIGHT)));
    }

    @Test
    public void aceCanBeUsedAsOne(){
        var run = new Run(Suit.SPADE, EnumSet.of(Rank.ACE, Rank.TWO, Rank.THREE));
        assertEquals(6, run.getPoints());
    }

    @Test
    public void aceCanNotBeUsedInTheMiddle(){
        assertThrows(IllegalStateException.class, () -> new Run(Suit.SPADE, EnumSet.of(Rank.KING, Rank.ACE, Rank.TWO)));
    }
}