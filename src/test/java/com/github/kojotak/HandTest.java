package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.List;
import static com.github.kojotak.Card.*;
import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    public void getLayOutFromRun(){
        var hand = new Hand(List.of(CJ, CQ, CK));
        var layOuts = hand.getLayOuts();
        assertEquals(1, layOuts.size());
        assertEquals(new Run(List.of(CJ, CQ, CK)), layOuts.getFirst().cleanRun());
        assertEquals(List.of(), layOuts.getFirst().dump());
    }

    @Test
    public void getLayOutFromRunOfLength4(){
        var hand = new Hand(List.of(CJ, CQ, CK, CA));
        var layOuts = hand.getLayOuts();
        assertEquals(3, layOuts.size());
        assertEquals(new Run(List.of(CJ, CQ, CK)), layOuts.getFirst().cleanRun());
        assertEquals(new Run(List.of(CJ, CQ, CK, CA)), layOuts.get(1).cleanRun());
        assertEquals(new Run(List.of(CQ, CK, CA)), layOuts.get(2).cleanRun());
        assertEquals(List.of(CA), layOuts.getFirst().dump());
        assertEquals(List.of(), layOuts.get(1).dump());
        assertEquals(List.of(CJ), layOuts.get(2).dump());
    }

    @Test
    public void noLayOutFromImpossibleHand(){
        var hand = new Hand(List.of(HJ, CQ, DK, SA));
        var layOuts = hand.getLayOuts();
        assertTrue(layOuts.isEmpty());
    }

    @Test
    public void noLayOutFromRunWithJoker(){
        var hand = new Hand(List.of(HJ, JOKER, HK));
        var layOuts = hand.getLayOuts();
        assertTrue(layOuts.isEmpty());
    }

}