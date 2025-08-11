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