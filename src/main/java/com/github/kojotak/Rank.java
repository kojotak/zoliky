package com.github.kojotak;

public enum Rank {

    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE;

    public int getPoints(){
        return switch (this){
            case ACE, KING, QUEEN, JACK, TEN -> 10;
            default -> this.ordinal() + 2;
        };
    }

    @Override
    public String toString(){
        return switch (this){
            case ACE, KING, QUEEN, JACK -> name().substring(0,1);
            default -> "" + getPoints();
        };
    }
}
