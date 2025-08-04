package com.github.kojotak;

public record Config(
        boolean useJokers,
        int packs,
        int cards,
        int points
) {

    public static final Config STANDARD = new Config(true, 2, 12, 42);
    public static final Config EXTENDED = new Config(true, 2, 15, 51);
}
