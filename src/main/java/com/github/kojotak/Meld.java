package com.github.kojotak;

import java.util.List;

public interface Meld extends Valuable, CardCarrier {

    List<Card> getCards();

    int MINIMUM_LENGTH = 3;

    default int getActualLength(){
        return getCards().size();
    }
}
