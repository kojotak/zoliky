package com.github.kojotak;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {

    @ParameterizedTest
    @MethodSource("supportedConfigs")
    public void deckHas54Cards(Config config) {
        assertEquals(108, new Deck(config).getCards().size());
    }

    @ParameterizedTest
    @MethodSource("supportedConfigs")
    public void deckHas4Jokers(Config config) {
        assertEquals(4, new Deck(config).getCards().stream().filter(Card::isJoker).count());
    }

    @ParameterizedTest
    @MethodSource("supportedConfigs")
    public void drawnHandHasCorrectNumberOfCards(Config config) {
        var deck = new Deck(config);
        var deckSize = deck.getCards().size();
        var hand = deck.drawHand();
        var handSize = hand.getCards().size();
        assertEquals(config.cards(), handSize);
        assertEquals(deckSize - handSize, deck.getCards().size());
    }

    public static Stream<Arguments> supportedConfigs() {
        return Stream.of(
                Config.STANDARD,
                Config.EXTENDED
        ).map(Arguments::of);
    }
}