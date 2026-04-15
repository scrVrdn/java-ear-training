package io.github.scrvrdn.eartraining.services.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import io.github.scrvrdn.eartraining.containers.RandomBag;
import io.github.scrvrdn.eartraining.containers.RandomDirectionBag;
import io.github.scrvrdn.eartraining.containers.RandomIntervalBag;
import io.github.scrvrdn.eartraining.domain.Direction;
import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.dto.TrackRequest;
import io.github.scrvrdn.eartraining.services.MusicObjectService;
import io.github.scrvrdn.eartraining.services.SettingsService;

@Service
public class IntervalService implements MusicObjectService<IntervalType> {
    private final Random rng;
    private final RandomBag<Direction> directionBag;
    private final RandomBag<IntervalType> intervalBag;
    private final SettingsService<IntervalType> settings;

    private IntervalType cachedInterval;

    public IntervalService(Random rng, RandomDirectionBag directionBag, RandomIntervalBag intervalBag, SettingsService<IntervalType> settings) {
        this.rng = rng;
        this.directionBag = directionBag;
        this.intervalBag = intervalBag;
        this.settings = settings;
    }

    @Override
    public TrackRequest createRandomRequest() {
        Direction dir = directionBag.peekRandom();
        IntervalType type = intervalBag.peekRandom();
        int rootMidiValue = getRootMidiValue(type);
        cachedInterval = type;
        return createRequest(dir, type, rootMidiValue);
    }

    private int getRootMidiValue(IntervalType type) {
        int midiValue = settings.getMinMidiValue() + rng.nextInt(settings.getMaxMidiValue());
        if (midiValue + type.getSemitones() > settings.getMaxMidiValue()) {
            midiValue = settings.getMaxMidiValue() - type.getSemitones();
        }

        return midiValue;
    }

    public TrackRequest createRequest(Direction dir, IntervalType type, int midiValue) {
        if (dir == null || type == null || midiValue < 0) {
            throw new IllegalArgumentException();
        }

        if (dir == Direction.DESCENDING) {
            return new TrackRequest(dir, new int[]{midiValue + type.getSemitones(), midiValue});
        }
        
        return new TrackRequest(dir, new int[]{midiValue, midiValue + type.getSemitones()});
    }

    @Override
    public boolean isLastType(IntervalType interval) {
        if (cachedInterval == null) {
            throw new IllegalStateException();
        }

        return cachedInterval == interval;
    }
    
}
