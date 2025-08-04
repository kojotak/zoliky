package com.github.kojotak;

public enum Rank implements Signed, Valuable {

    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    String sign;

    @Override
    public String getSign() {
        return sign;
    }

    @Override
    public int getPoints(){
        return switch (this){
            case ACE, KING, QUEEN, JACK, TEN -> 10;
            default -> this.ordinal() + 2;
        };
    }

    Rank(String sign){
        this.sign = sign;
    }
}
