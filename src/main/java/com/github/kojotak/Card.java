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

    public boolean isJoker() {
        return suit == null;
    }

    @Override
    public String toString() {
        return this.isJoker()
                ? "JOKER"
                : "" + rank + suit;
    }
}
