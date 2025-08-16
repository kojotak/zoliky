package com.github.kojotak;

public enum SpecialSuit implements Suit{
    JOKER("*");

    private final String sign;

    SpecialSuit(String sign){
        this.sign = sign;
    }

    @Override
    public String toString(){
        return sign;
    }
}
