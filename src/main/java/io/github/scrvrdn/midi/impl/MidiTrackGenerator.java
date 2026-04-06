package io.github.scrvrdn.midi.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;

import io.github.scrvrdn.domain.Direction;
import io.github.scrvrdn.dto.TrackRequest;

public final class MidiTrackGenerator {
    private static final int HALF_NOTE = 2;
    private static final int QUARTER_NOTE = 1;

    private static int velocity = 127;

    private MidiTrackGenerator() {}

    
    public static List<MidiEvent> build(TrackRequest request) throws InvalidMidiDataException {
        List<MidiEvent> track = new ArrayList<>();
        long tick = 0L;

        if (request.dir() == Direction.SIMULTANEOUS) {
            
            for (int note : request.midiValues()) {
                track.add(createNoteOnEvent(note, tick));
                track.add(createNoteOffEvent(note, HALF_NOTE));
            }

        } else {
            for (int note : request.midiValues()) {
                track.add(createNoteOnEvent(note, tick));
                tick += QUARTER_NOTE;
                track.add(createNoteOffEvent(note, tick));
            }
        }

        return track;
    }

    private static MidiEvent createNoteOnEvent(int note, long tick) throws InvalidMidiDataException {
        return new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, note, velocity), tick);
    }

    private static MidiEvent createNoteOffEvent(int note, long tick) throws InvalidMidiDataException {
        return new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, note, 0), tick);
    }

    public static int getVelocity() {
        return velocity;
    }

    public static void setVelocity(int newVelocity) {
        velocity = newVelocity;
    }
}
