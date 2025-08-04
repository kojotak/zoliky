package com.github.kojotak;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class RankTest {

    @ParameterizedTest
    @EnumSource(Rank.class)
    public void expectedRankValues(Rank rank){
        assertEquals( switch(rank){
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            case TEN -> 10;
            case JACK -> 10;
            case QUEEN -> 10;
            case KING -> 10;
            case ACE -> 10;
        }, rank.getPoints());
    }
}