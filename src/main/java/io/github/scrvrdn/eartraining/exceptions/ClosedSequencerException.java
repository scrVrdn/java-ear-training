package io.github.scrvrdn.eartraining.exceptions;

public class ClosedSequencerException extends RuntimeException {

    public ClosedSequencerException() {
        super("The sequencer is closed.");
    }

}
