package io.github.scrvrdn.eartraining.services;

import javax.sound.midi.InvalidMidiDataException;

import io.github.scrvrdn.eartraining.domain.IntervalType;

public interface EarTrainingService {

    void playNewInterval() throws InvalidMidiDataException;
    void replayInterval();
    boolean isLastInterval(IntervalType interval);
}
