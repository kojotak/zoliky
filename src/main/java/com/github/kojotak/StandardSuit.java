package com.github.kojotak;

public enum StandardSuit implements Suit {

    HEART("♥"),
    CLUB("♣"),
    DIAMOND("♦"),
    SPADE("♠");

    private final String sign;

    StandardSuit(String sign){
        this.sign = sign;
    }

    @Override
    public String toString(){
        return sign;
    }
}
