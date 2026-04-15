package io.github.scrvrdn.eartraining.domain;

import java.util.NoSuchElementException;

public enum PitchClass {
    C("C"),
    C_SHARP("C\\u266F"),
    D("D"),
    E_FLAT("E\\u266D"),
    E("E"),
    F("F"),
    F_SHARP("F\u266F"),
    G("G"),
    G_SHARP("G\u266F"),
    A("A"),
    B_FLAT("B\u266D"),
    B("B");

    private static final PitchClass[] VALUES = PitchClass.values();
    private final String name;

    PitchClass(String name) {
        this.name = name;
    }

    public static PitchClass getPitchClassOfMidiValue(int midiValue) {
        int i = midiValue % VALUES.length;
        return VALUES[i];
    }

    @Override
    public String toString() {
        return name;
    }
}
