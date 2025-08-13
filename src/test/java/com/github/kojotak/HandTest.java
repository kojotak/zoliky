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
        assertEquals(new Run(C10, CJ, CQ, CK, CA), layOuts.getFirst().cleanRun());
        assertEquals(List.of(), layOuts.getFirst().dump());
    }

    @Test
    public void getLayOutFromRunOfLength4IgnoresDuplicates(){
        var hand = new Hand(Config.STANDARD.points(), List.of(C10, CJ, CQ, CK, CA, CA));
        var layOuts = hand.getLayOuts();
        assertEquals(1, layOuts.size());
        assertEquals(new Run(C10, CJ, CQ, CK, CA), layOuts.getFirst().cleanRun());
        assertEquals(List.of(CA), layOuts.getFirst().dump());
    }

    @Test
    public void getLayOutFromRunOfLength4IgnoresMultipleDuplicates(){
        var hand = new Hand(Config.STANDARD.points(), List.of(D10, DJ, DQ, DK, DA, D10, DQ, DA));
        var result = hand.getLayOuts();
        assertEquals(3, result.size());
        assertTrue(result.contains(new LayOut(new Run(D10, DJ, DQ, DK, DA), List.of(), List.of(D10, DQ, DA))));
        assertTrue(result.contains(new LayOut(new Run(D10, DJ, DQ), List.of(new Run(DQ, DK, DA)), List.of(D10, DA))));
        assertTrue(result.contains(new LayOut(new Run(DQ, DK, DA), List.of(new Run(D10, DJ, DQ)), List.of(D10, DA))));
    }

    @Test
    public void getLayOutFromRunOfLength4WithMultipleDuplicates(){
        var hand = new Hand(Config.STANDARD.points(), List.of(D10, DJ, DQ, DK, DA, D10, DJ, DQ, DK, DA));
        var layOuts = hand.getLayOuts();
        assertTrue(layOuts.size() > 1);
        //best layOut: two runs of length 5, 2*50 = 100 points
        var layOut = layOuts.stream().filter( l -> l.points() == 100).findFirst().orElseThrow();
        assertEquals(new Run(D10, DJ, DQ, DK, DA), layOut.cleanRun());
        assertEquals(List.of(), layOut.dump());
        assertEquals(List.of(new Run(D10, DJ, DQ, DK, DA)), layOut.melds());
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