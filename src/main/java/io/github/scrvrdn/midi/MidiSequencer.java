package io.github.scrvrdn.midi;

import java.util.List;

import javax.sound.midi.MidiEvent;

import io.github.scrvrdn.dto.TrackRequest;

public interface MidiSequencer {

    void play() throws Exception;
    void addTrack(List<MidiEvent> events);
    void open() throws Exception;
    void close();
    void setTempo(float bmp);
}
