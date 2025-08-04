package com.github.kojotak;

public enum Suit implements Signed {
    HEART("♥"),
    CLUB("♣"),
    DIAMOND("♦"),
    SPADE("♠");

    String sign;

    public String getSign() {
        return sign;
    }

    Suit(String sign){
        this.sign = sign;
    }
}
