package io.github.scrvrdn.eartraining.midi.impl;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import org.springframework.stereotype.Component;

import io.github.scrvrdn.eartraining.exceptions.ClosedSequencerException;
import io.github.scrvrdn.eartraining.midi.MidiPlayerService;

@Component
public class MidiPlayerServiceImpl implements MidiPlayerService {


    private Track cachedTrack;
    private Sequencer sequencer;
    private float tempoInBPM;

    public MidiPlayerServiceImpl(Sequencer sequencer) {
        this.sequencer = sequencer;
    }

    @Override
    public void play() {
        if (!sequencer.isOpen()) {
            throw new ClosedSequencerException();
        }

        if (sequencer.isRunning()) {            
            sequencer.stop();
        }
        
        sequencer.setTickPosition(0);
        sequencer.setTempoInBPM(tempoInBPM);
        sequencer.start();
    }

    @Override
    public void addTrack(List<MidiEvent> events) throws InvalidMidiDataException {
        if (events == null || events.isEmpty()) {
            throw new IllegalArgumentException("Supplied list of MidiEvents was null or empty.");
        }
        
        if (cachedTrack != null) {
            sequencer.getSequence().deleteTrack(cachedTrack);
        }

        Sequence sequence = sequencer.getSequence();        
        Track track = sequence.createTrack();    
        events.forEach(track::add);        
        sequencer.setSequence(sequence);       
        cachedTrack = track;
    }

    @Override
    public void open() throws MidiUnavailableException {
        if (sequencer == null) {
            throw new MidiUnavailableException("No sequencer found.");
        }

        sequencer.open();
    }

    @Override
    public boolean isOpen() {
        return sequencer.isOpen();
    }
    
    @Override
    public void close() {
        if (sequencer.isRunning()) {
            sequencer.stop();
        }
        
        sequencer.close();
    }

    @Override
    public float getTempo() {
        return tempoInBPM;
    }

    @Override
    public void setTempo(float bpm) {
        tempoInBPM = bpm;
    }
}
