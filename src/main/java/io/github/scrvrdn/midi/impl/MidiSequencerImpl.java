package io.github.scrvrdn.midi.impl;

import java.util.List;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import org.springframework.stereotype.Component;

import io.github.scrvrdn.midi.MidiSequencer;

@Component
public class MidiSequencerImpl implements MidiSequencer {


    private Track cachedTrack;
    private final Sequencer sequencer;

    public MidiSequencerImpl(Sequencer sequencer) {
        this.sequencer = sequencer;
    }

    @Override
    public void play() throws Exception {
        if (!sequencer.isOpen()) {
            open();
        }

        if (sequencer.isRunning()) {
            sequencer.stop();
        }

        sequencer.setTickPosition(0);
        sequencer.start();
    }

    @Override
    public void addTrack(List<MidiEvent> events) {
        if (events == null || events.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (cachedTrack != null) {
            sequencer.getSequence().deleteTrack(cachedTrack);
        }

        Track track = sequencer.getSequence().createTrack();
        for (MidiEvent event : events) {
            track.add(event);
        }

        cachedTrack = track;
    }

    @Override
    public void open() throws Exception {
        if (sequencer == null) {
            throw new MidiUnavailableException("No sequencer found.");
        }

        sequencer.open();
        Thread.sleep(1000);
    }

    @Override
    public void close() {
        if (sequencer.isRunning()) {
            sequencer.stop();
        }
        
        sequencer.close();
    }

    @Override
    public void setTempo(float bmp) {
        sequencer.setTempoInBPM(bmp);
    }
}
