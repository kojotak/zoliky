package com.github.kojotak;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.kojotak.Card.*;
import static org.junit.jupiter.api.Assertions.*;

class LayOutTest {

    @Test
    public void testLayOutGetPoints(){
     var layOut = new LayOut(
       new Run(D2, D3, D4),
       List.of(HQ, JOKER, SA),
       new Run(S7, jokerFor(Rank.EIGHT), S9),
       new Set(Rank.ACE, List.of(Suit.CLUB, Suit.DIAMOND, Suit.HEART))
     );
     assertEquals((2+3+4)+(7+8+9)+30, layOut.getPoints());
    }

    @Test
    public void testLayOutGetPointsWithoutMelds(){
        var layOut = new LayOut(
                new Run(S10, SJ, SQ, SK, SA),
                List.of(HQ, JOKER, SA)
        );
        assertEquals(50, layOut.getPoints());
    }

}