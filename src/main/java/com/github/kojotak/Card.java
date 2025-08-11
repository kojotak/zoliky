package com.github.kojotak;


import org.jspecify.annotations.Nullable;

/**
 * Represents a card.
 * <p/>
 * Joker is always card without suit (it matches all suits).
 * Joker in hand is without a rank (it matches all ranks), but when used in {@link Meld} it needs rank to be set.
 */
public record Card(
        @Nullable Rank rank, @Nullable Suit suit
) {

    public static final Card JOKER = new Card(null, null);

    public static final Card H2 = new Card(Rank.TWO, Suit.HEART);
    public static final Card H3 = new Card(Rank.THREE, Suit.HEART);
    public static final Card H4 = new Card(Rank.FOUR, Suit.HEART);
    public static final Card H5 = new Card(Rank.FIVE, Suit.HEART);
    public static final Card H6 = new Card(Rank.SIX, Suit.HEART);
    public static final Card H7 = new Card(Rank.SEVEN, Suit.HEART);
    public static final Card H8 = new Card(Rank.EIGHT, Suit.HEART);
    public static final Card H9 = new Card(Rank.NINE, Suit.HEART);
    public static final Card H10 = new Card(Rank.TEN, Suit.HEART);
    public static final Card HJ = new Card(Rank.JACK, Suit.HEART);
    public static final Card HQ = new Card(Rank.QUEEN, Suit.HEART);
    public static final Card HK = new Card(Rank.KING, Suit.HEART);
    public static final Card HA = new Card(Rank.ACE, Suit.HEART);

    public static final Card C2 = new Card(Rank.TWO, Suit.CLUB);
    public static final Card C3 = new Card(Rank.THREE, Suit.CLUB);
    public static final Card C4 = new Card(Rank.FOUR, Suit.CLUB);
    public static final Card C5 = new Card(Rank.FIVE, Suit.CLUB);
    public static final Card C6 = new Card(Rank.SIX, Suit.CLUB);
    public static final Card C7 = new Card(Rank.SEVEN, Suit.CLUB);
    public static final Card C8 = new Card(Rank.EIGHT, Suit.CLUB);
    public static final Card C9 = new Card(Rank.NINE, Suit.CLUB);
    public static final Card C10 = new Card(Rank.TEN, Suit.CLUB);
    public static final Card CJ = new Card(Rank.JACK, Suit.CLUB);
    public static final Card CQ = new Card(Rank.QUEEN, Suit.CLUB);
    public static final Card CK = new Card(Rank.KING, Suit.CLUB);
    public static final Card CA = new Card(Rank.ACE, Suit.CLUB);

    public static final Card D2 = new Card(Rank.TWO, Suit.DIAMOND);
    public static final Card D3 = new Card(Rank.THREE, Suit.DIAMOND);
    public static final Card D4 = new Card(Rank.FOUR, Suit.DIAMOND);
    public static final Card D5 = new Card(Rank.FIVE, Suit.DIAMOND);
    public static final Card D6 = new Card(Rank.SIX, Suit.DIAMOND);
    public static final Card D7 = new Card(Rank.SEVEN, Suit.DIAMOND);
    public static final Card D8 = new Card(Rank.EIGHT, Suit.DIAMOND);
    public static final Card D9 = new Card(Rank.NINE, Suit.DIAMOND);
    public static final Card D10 = new Card(Rank.TEN, Suit.DIAMOND);
    public static final Card DJ = new Card(Rank.JACK, Suit.DIAMOND);
    public static final Card DQ = new Card(Rank.QUEEN, Suit.DIAMOND);
    public static final Card DK = new Card(Rank.KING, Suit.DIAMOND);
    public static final Card DA = new Card(Rank.ACE, Suit.DIAMOND);

    public static final Card S2 = new Card(Rank.TWO, Suit.SPADE);
    public static final Card S3 = new Card(Rank.THREE, Suit.SPADE);
    public static final Card S4 = new Card(Rank.FOUR, Suit.SPADE);
    public static final Card S5 = new Card(Rank.FIVE, Suit.SPADE);
    public static final Card S6 = new Card(Rank.SIX, Suit.SPADE);
    public static final Card S7 = new Card(Rank.SEVEN, Suit.SPADE);
    public static final Card S8 = new Card(Rank.EIGHT, Suit.SPADE);
    public static final Card S9 = new Card(Rank.NINE, Suit.SPADE);
    public static final Card S10 = new Card(Rank.TEN, Suit.SPADE);
    public static final Card SJ = new Card(Rank.JACK, Suit.SPADE);
    public static final Card SQ = new Card(Rank.QUEEN, Suit.SPADE);
    public static final Card SK = new Card(Rank.KING, Suit.SPADE);
    public static final Card SA = new Card(Rank.ACE, Suit.SPADE);

    public boolean isJoker() {
        return suit == null;
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
}
