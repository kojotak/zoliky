package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SetTest {

    @Test
    public void fullSetOfFives(){
        var set = new Set(Rank.FIVE, EnumSet.allOf(Suit.class).stream().toList());
        assertEquals(4*5, set.getPoints());
    }

    @Test
    public void threeAces(){
        var set = new Set(Rank.ACE, List.of(Suit.CLUB, Suit.DIAMOND, Suit.HEART));
        assertEquals(30, set.getPoints());
    }

    @Test
    public void illegalSetOfDuplicatedSuits(){
        assertThrows(IllegalStateException.class, () -> new Set(Rank.ACE, List.of(Suit.CLUB, Suit.CLUB, Suit.HEART)));
    }

    @Test
    public void illegalSetOfTwoSuits(){
        assertThrows(IllegalStateException.class, () -> new Set(Rank.ACE, List.of(Suit.CLUB, Suit.DIAMOND)));
    }

    @Test
    public void illegalSetOfOneSuit(){
        assertThrows(IllegalStateException.class, () -> new Set(Rank.KING, List.of(Suit.HEART)));
    }

    @Test
    public void illegalEmptySet(){
        assertThrows(IllegalStateException.class, () -> new Set(Rank.ACE, List.of()));
    }

    @Test
    public void illegalSetOfDifferentRanks(){
        assertThrows(IllegalStateException.class, () -> new Set(List.of(
                new Card(Rank.KING, Suit.CLUB),
                new Card(Rank.JACK, Suit.CLUB),
                new Card(Rank.QUEEN, Suit.CLUB)
                )));
    }

    @Test
    public void setFromThreeCards(){
        var set = new Set(List.of(
                new Card(Rank.KING, Suit.CLUB),
                new Card(Rank.KING, Suit.DIAMOND),
                new Card(Rank.KING, Suit.HEART)
        ));
        assertEquals(30, set.getPoints());
    }

    @Test
    public void setFromThreeCardsIncludingJoker(){
        var set = new Set(List.of(
                new Card(Rank.KING, Suit.CLUB),
                new Card(Rank.KING, Suit.DIAMOND),
                Card.JOKER
        ));
        assertEquals(30, set.getPoints());
    }

    @Test
    public void setCanNotPermitsTwoConsecutiveJokers() {
        assertThrows(IllegalStateException.class, () -> new Set(List.of(
                new Card(Rank.KING, Suit.CLUB),
                new Card(Rank.KING, Suit.DIAMOND),
                Card.JOKER,
                Card.JOKER
        )));
    }

    @Test
    public void setCanNotPermitsTwoJokers() {
        assertThrows(IllegalStateException.class, () -> new Set(List.of(
                new Card(Rank.KING, Suit.CLUB),
                Card.JOKER,
                new Card(Rank.KING, Suit.DIAMOND),
                Card.JOKER
        )));
    }

    @Test
    public void setCanNotPermitJustOneNonJokerCard() {
        assertThrows(IllegalStateException.class, () -> new Set(List.of(
                Card.JOKER,
                new Card(Rank.ACE, Suit.HEART),
                Card.JOKER
        )));
    }
}