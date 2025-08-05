package com.github.kojotak;

import java.util.List;

public interface Meld {

    List<Card> getCards();

    int getPoints();

    int MINIMUM_LENGTH = 3;
}
