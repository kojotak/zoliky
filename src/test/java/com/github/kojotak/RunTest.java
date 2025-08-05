package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RunTest {

    @Test
    public void runOfThreeFromFive(){
        var run = new Run(Suit.HEART, List.of(Rank.FIVE, Rank.SIX, Rank.SEVEN));
        assertEquals(3*6, run.getPoints());
    }

    @Test
    public void runOfFromNineToAce(){
        var run = new Run(Suit.SPADE, List.of(Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE));
        assertEquals(9 + 5*10, run.getPoints());
    }

    @Test
    public void illegalRunOfTwo(){
        assertThrows(IllegalStateException.class, () -> new Run(Suit.DIAMOND, List.of(Rank.FIVE, Rank.SIX)));
    }

    @Test
    public void illegalRunOfNonConsecutives(){
        assertThrows(IllegalStateException.class, () -> new Run(Suit.DIAMOND, List.of(Rank.FIVE, Rank.SEVEN, Rank.EIGHT)));
    }

    @Test
    public void aceCanBeUsedAsOne(){
        var run = new Run(Suit.SPADE, List.of(Rank.ACE, Rank.TWO, Rank.THREE));
        assertEquals(6, run.getPoints());
    }

    @Test
    public void aceCanNotBeUsedInTheMiddle(){
        assertThrows(IllegalStateException.class, () -> new Run(Suit.SPADE, List.of(Rank.KING, Rank.ACE, Rank.TWO)));
    }

    @Test
    public void runFromThreeCards(){
        var run = new Run(List.of(
                new Card(Rank.NINE, Suit.CLUB),
                new Card(Rank.TEN, Suit.CLUB),
                new Card(Rank.JACK, Suit.CLUB)
        ));
        assertEquals(29, run.getPoints());
    }

    @Test
    public void runFromThreeCardsWithJoker(){
        var run = new Run(List.of(
                new Card(Rank.NINE, Suit.CLUB),
                new Card(Rank.TEN, null),
                new Card(Rank.JACK, Suit.CLUB)
        ));
        assertEquals(29, run.getPoints());
    }

    @Test
    public void runFromThreeCardsWithJokerAtTheEnd(){
        var run = new Run(List.of(
                new Card(Rank.NINE, Suit.CLUB),
                new Card(Rank.TEN, Suit.CLUB),
                new Card(Rank.JACK, null)
        ));
        assertEquals(29, run.getPoints());
    }

    @Test
    public void runFromThreeCardsWithJokerAtTheBeginning(){
        var run = new Run(List.of(
                new Card(Rank.NINE, null),
                new Card(Rank.TEN, Suit.CLUB),
                new Card(Rank.JACK, Suit.CLUB)
        ));
        assertEquals(29, run.getPoints());
    }

    @Test
    public void runWithTwoConsecutiveJokers(){
        assertThrows(IllegalStateException.class, () -> new Run(List.of(
                new Card(Rank.NINE, Suit.CLUB),
                new Card(Rank.TEN, null),
                new Card(Rank.JACK, null),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.KING, Suit.CLUB)
        )));
    }

    @Test
    public void runWithTwoJokers(){
        var run = new Run(List.of(
                new Card(Rank.NINE, Suit.CLUB),
                new Card(Rank.TEN, null),
                new Card(Rank.JACK, Suit.CLUB),
                new Card(Rank.QUEEN, null),
                new Card(Rank.KING, Suit.CLUB)
        ));
        assertEquals(49, run.getPoints());
    }

    @Test
    public void runWithAllRanks(){
        var run = new Run(Suit.HEART, EnumSet.allOf(Rank.class).stream().toList());
        assertEquals(2+3+4+5+6+7+8+9+50, run.getPoints());
    }

    @Test
    public void runWithAllRanksIncludingOne(){
        var ranks = new ArrayList<Rank>();
        ranks.add(Rank.ACE); //acts as one
        ranks.addAll(EnumSet.allOf(Rank.class));
        var run = new Run(Suit.HEART, ranks);
        assertEquals(1+2+3+4+5+6+7+8+9+50, run.getPoints());
    }
}