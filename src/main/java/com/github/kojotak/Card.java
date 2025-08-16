package com.github.kojotak;


import org.jspecify.annotations.Nullable;

import java.util.Objects;

import static com.github.kojotak.Rank.*;
import static com.github.kojotak.StandardSuit.*;

public record Card(

        @Nullable Rank rank, Enum<? extends Suit> suit

) implements Comparable<Card> {

    public static final Card JOKER = new Card(null, SpecialSuit.JOKER);

    public static final Card H2 = new Card(TWO, HEART);
    public static final Card H3 = new Card(THREE, HEART);
    public static final Card H4 = new Card(FOUR, HEART);
    public static final Card H5 = new Card(FIVE, HEART);
    public static final Card H6 = new Card(SIX, HEART);
    public static final Card H7 = new Card(SEVEN, HEART);
    public static final Card H8 = new Card(EIGHT, HEART);
    public static final Card H9 = new Card(NINE, HEART);
    public static final Card H10 = new Card(TEN, HEART);
    public static final Card HJ = new Card(JACK, HEART);
    public static final Card HQ = new Card(QUEEN, HEART);
    public static final Card HK = new Card(KING, HEART);
    public static final Card HA = new Card(ACE, HEART);

    public static final Card C2 = new Card(TWO, CLUB);
    public static final Card C3 = new Card(THREE, CLUB);
    public static final Card C4 = new Card(FOUR, CLUB);
    public static final Card C5 = new Card(FIVE, CLUB);
    public static final Card C6 = new Card(SIX, CLUB);
    public static final Card C7 = new Card(SEVEN, CLUB);
    public static final Card C8 = new Card(EIGHT, CLUB);
    public static final Card C9 = new Card(NINE, CLUB);
    public static final Card C10 = new Card(TEN, CLUB);
    public static final Card CJ = new Card(JACK, CLUB);
    public static final Card CQ = new Card(QUEEN, CLUB);
    public static final Card CK = new Card(KING, CLUB);
    public static final Card CA = new Card(ACE, CLUB);

    public static final Card D2 = new Card(TWO, DIAMOND);
    public static final Card D3 = new Card(THREE, DIAMOND);
    public static final Card D4 = new Card(FOUR, DIAMOND);
    public static final Card D5 = new Card(FIVE, DIAMOND);
    public static final Card D6 = new Card(SIX, DIAMOND);
    public static final Card D7 = new Card(SEVEN, DIAMOND);
    public static final Card D8 = new Card(EIGHT, DIAMOND);
    public static final Card D9 = new Card(NINE, DIAMOND);
    public static final Card D10 = new Card(TEN, DIAMOND);
    public static final Card DJ = new Card(JACK, DIAMOND);
    public static final Card DQ = new Card(QUEEN, DIAMOND);
    public static final Card DK = new Card(KING, DIAMOND);
    public static final Card DA = new Card(ACE, DIAMOND);

    public static final Card S2 = new Card(TWO, SPADE);
    public static final Card S3 = new Card(THREE, SPADE);
    public static final Card S4 = new Card(FOUR, SPADE);
    public static final Card S5 = new Card(FIVE, SPADE);
    public static final Card S6 = new Card(SIX, SPADE);
    public static final Card S7 = new Card(SEVEN, SPADE);
    public static final Card S8 = new Card(EIGHT, SPADE);
    public static final Card S9 = new Card(NINE, SPADE);
    public static final Card S10 = new Card(TEN, SPADE);
    public static final Card SJ = new Card(JACK, SPADE);
    public static final Card SQ = new Card(QUEEN, SPADE);
    public static final Card SK = new Card(KING, SPADE);
    public static final Card SA = new Card(ACE, SPADE);

    public boolean isJoker() {
        return SpecialSuit.JOKER.equals(suit);
    }

    public static Card jokerFor(Rank rank){
        return new Card(rank, null);
    }

    @Override
    public String toString() {
        return this.isJoker()
                ? "JOKER"
                : "" + rank + suit;
    }

    @Override
    public int compareTo(Card other) {
        Objects.requireNonNull(other, "other");

        // 1) Jokers first
        boolean thisJoker = this.isJoker();
        boolean otherJoker = other.isJoker();
        if (thisJoker != otherJoker) return thisJoker ? -1 : 1; //one of them is joker
        if (thisJoker) return 0; // both are jokers

        // 2) compare suits
        int suitComparison = this.getSuitOrder().compareTo(other.getSuitOrder());
        if (suitComparison != 0) return suitComparison;

        // 3) compare ranks
        return this.getRankOrder().compareTo(other.getRankOrder());
    }

    private Integer getRankOrder(){
        return rank == null ? -1 : rank.ordinal();
    }

    private Integer getSuitOrder(){
        return suit.ordinal();
    }

}
