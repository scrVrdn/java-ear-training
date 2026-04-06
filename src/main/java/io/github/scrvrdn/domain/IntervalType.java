package io.github.scrvrdn.domain;

public enum IntervalType {
    UNISON(0),
    MINOR_SECOND(1),
    MAJOR_SECOND(2),
    MINOR_THIRD(3),
    MAJOR_THIRD(4),
    PERFECT_FOURTH(5),
    TRITONE(6),
    PERFECT_FIFTH(7),
    MINOR_SIXTH(8),
    MAJOR_SIXTH(9),
    MINOR_SEVENTH(10),
    MAJOR_SEVENTH(11),
    OCTAVE(12),
    MINOR_NINTH(13),
    MAJOR_NINTH(14),
    MINOR_TENTH(15),
    MAJOR_TENTH(16),
    PERFECT_ELEVENTH(17),
    AUGMENTED_ELEVENTH(18),
    PERFECT_TWELFTH(19);

    private final int semitones;

    IntervalType(int semitones) {
        this.semitones = semitones;
    }

    public int getSemitones() {
        return this.semitones;
    }
}
