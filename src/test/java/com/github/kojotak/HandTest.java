package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.List;
import static com.github.kojotak.Card.*;
import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    public void getLayOutFromRunUnsufficientPoints(){
        var hand = new Hand(Config.STANDARD.points(), List.of(CJ, CQ, CK));
        var layOuts = hand.getLayOuts();
        assertTrue(layOuts.isEmpty());
    }

    @Test
    public void getLayOutFromRunOfLength4(){
        var hand = new Hand(Config.STANDARD.points(), List.of(C10, CJ, CQ, CK, CA));
        var layOuts = hand.getLayOuts();
        assertEquals(1, layOuts.size());
        assertEquals(new Run(List.of(C10, CJ, CQ, CK, CA)), layOuts.getFirst().cleanRun());
        assertEquals(List.of(), layOuts.getFirst().dump());
    }

    @Test
    public void getLayOutFromRunOfLength4IgnoresDuplicates(){
        var hand = new Hand(Config.STANDARD.points(), List.of(C10, CJ, CQ, CK, CA, CA));
        var layOuts = hand.getLayOuts();
        assertEquals(1, layOuts.size());
        assertEquals(new Run(List.of(C10, CJ, CQ, CK, CA)), layOuts.getFirst().cleanRun());
        assertEquals(List.of(CA), layOuts.getFirst().dump());
    }

    @Test
    public void getLayOutFromRunOfLength4IgnoresMultipleDuplicates(){
        var hand = new Hand(Config.STANDARD.points(), List.of(D10, DJ, DQ, DK, DA, D10, DQ, DA));
        var layOuts = hand.getLayOuts();
        assertEquals(1, layOuts.size());
        assertEquals(new Run(List.of(D10, DJ, DQ, DK, DA)), layOuts.getFirst().cleanRun());
        assertEquals(List.of(D10, DQ, DA), layOuts.getFirst().dump());
    }

    @Test
    public void noLayOutFromImpossibleHand(){
        var hand = new Hand(Config.STANDARD.points(), List.of(HJ, CQ, DK, SA));
        var layOuts = hand.getLayOuts();
        assertTrue(layOuts.isEmpty());
    }

    @Test
    public void noLayOutFromRunWithJoker(){
        var hand = new Hand(Config.STANDARD.points(), List.of(HJ, JOKER, HK));
        var layOuts = hand.getLayOuts();
        assertTrue(layOuts.isEmpty());
    }

}