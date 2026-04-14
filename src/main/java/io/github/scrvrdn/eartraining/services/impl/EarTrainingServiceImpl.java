package io.github.scrvrdn.eartraining.services.impl;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;

import org.springframework.stereotype.Service;

import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.dto.TrackRequest;
import io.github.scrvrdn.eartraining.midi.MidiPlayerService;
import io.github.scrvrdn.eartraining.midi.impl.MidiTrackGenerator;
import io.github.scrvrdn.eartraining.services.EarTrainingService;
import io.github.scrvrdn.eartraining.services.MusicObjectService;

@Service
public class EarTrainingServiceImpl implements EarTrainingService {

    private MusicObjectService<IntervalType> intervalService;
    private MidiPlayerService midiSequencer;

    public EarTrainingServiceImpl(MusicObjectService<IntervalType> intervalService, MidiPlayerService midiSequencer) {
        this.intervalService = intervalService;
        this.midiSequencer = midiSequencer;
    }
    

    @Override
    public void playNewInterval() throws InvalidMidiDataException {
        TrackRequest request = intervalService.createRandomRequest();
        List<MidiEvent> events = MidiTrackGenerator.build(request);
        midiSequencer.addTrack(events);
        midiSequencer.play();
    }

    @Override
    public void replayInterval() {
        midiSequencer.play();
    }

    @Override
    public boolean isLastInterval(IntervalType interval) {
        return intervalService.isLastType(interval);        
    }
}
