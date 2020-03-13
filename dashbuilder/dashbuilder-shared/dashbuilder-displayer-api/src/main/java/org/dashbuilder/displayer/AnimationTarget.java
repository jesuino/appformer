package org.dashbuilder.displayer;

import java.util.stream.Stream;

public enum AnimationTarget {

    SERIES,
    VALUES;

    public static AnimationTarget from(String value) {
        return Stream.of(values())
                     .filter(v -> v.toString().equalsIgnoreCase(value))
                     .findFirst().orElseGet(() -> SERIES);
    }

}
