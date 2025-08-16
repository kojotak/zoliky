package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.kojotak.Card.*;
import static org.junit.jupiter.api.Assertions.*;

class SetTest {

    @Test
    public void fullSetOfFives(){
        assertEquals(4*5, new Set(C5, S5, D5, H5).getPoints());
    }

    @Test
    public void threeAces(){
        assertEquals(30, new Set(CA, DA, HA).getPoints());
    }

    @Test
    public void illegalSetOfDuplicatedSuits(){
        assertThrows(IllegalStateException.class, () -> new Set(CA, HA, CA));
    }

    @Test
    public void illegalSetOfTwoSuits(){
        assertThrows(IllegalStateException.class, () -> new Set(CA, SA));
    }

    @Test
    public void illegalSetOfOneSuit(){
        assertThrows(IllegalStateException.class, () -> new Set(HK));
    }

    @Test
    public void illegalEmptySet(){
        assertThrows(IllegalStateException.class, () -> new Set(List.of()));
    }

    @Test
    public void illegalSetOfDifferentRanks(){
        assertThrows(IllegalStateException.class, () -> new Set(CK, CJ, CQ));
    }

    @Test
    public void setFromThreeCards(){
        assertEquals(30, new Set(CK, DK, HK).getPoints());
    }

    @Test
    public void setFromThreeCardsIncludingJoker(){
        assertEquals(30, new Set(CK, DK, JOKER).getPoints());
    }

    @Test
    public void setPermitsTwoConsecutiveJokers() {
        assertEquals(40, new Set(CK, DK, JOKER, JOKER).getPoints());
    }

    @Test
    public void setPermitsTwoJokers() {
        assertEquals(40, new Set(CK, JOKER, DK, JOKER).getPoints());
    }

    @Test
    public void canNotPermitJustOneNonJokerCard() {
        assertEquals(30, new Set(JOKER, HA, JOKER).getPoints());
    }
}