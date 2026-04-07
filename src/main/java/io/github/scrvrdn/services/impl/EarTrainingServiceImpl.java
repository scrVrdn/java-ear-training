package io.github.scrvrdn.services.impl;

import java.util.List;

import javax.sound.midi.MidiEvent;

import io.github.scrvrdn.domain.IntervalType;
import io.github.scrvrdn.dto.TrackRequest;
import io.github.scrvrdn.midi.MidiSequencer;
import io.github.scrvrdn.midi.impl.MidiTrackGenerator;
import io.github.scrvrdn.services.EarTrainingService;

public class EarTrainingServiceImpl implements EarTrainingService {
    private IntervalService intervalService;
    private MidiSequencer midiSequencer;

    public EarTrainingServiceImpl(IntervalService intervalService, MidiSequencer midiSequencer) {
        this.intervalService = intervalService;
        this.midiSequencer = midiSequencer;
    }
    

    @Override
    public void playNewInterval() throws Exception {
        TrackRequest request = intervalService.createRandomRequest();
        List<MidiEvent> events = MidiTrackGenerator.build(request);
        midiSequencer.addTrack(events);
        midiSequencer.play();
    }

    @Override
    public boolean isLastInterval(IntervalType interval) {
        return intervalService.isLastInterval(interval);        
    }
}
