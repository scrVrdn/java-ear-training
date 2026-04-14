package io.github.scrvrdn.eartraining.midi;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;


public interface MidiPlayerService {

    void play();
    void addTrack(List<MidiEvent> events) throws InvalidMidiDataException;
    void open() throws Exception;
    boolean isOpen();
    void close();
    float getTempo();
    void setTempo(float bmp);
}
