package com.github.kojotak;


import org.jspecify.annotations.Nullable;

public record Card (
        @Nullable Rank rank, @Nullable Suit suit
) {

    public static final Card JOKER = new Card(null, null);

    public boolean isJoker(){
        return suit == null;
    }

    @Override
    public String toString() {
        return this.isJoker()
                ? "JOKER"
                : rank.getSign()+suit.getSign();
    }
}
