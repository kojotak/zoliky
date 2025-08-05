package com.github.kojotak;

public enum Suit {

    HEART("♥"),
    CLUB("♣"),
    DIAMOND("♦"),
    SPADE("♠");

    private final String sign;

    Suit(String sign){
        this.sign = sign;
    }

    @Override
    public String toString(){
        return sign;
    }
}
