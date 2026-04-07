package io.github.scrvrdn.services.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import io.github.scrvrdn.containers.RandomDirectionBag;
import io.github.scrvrdn.containers.RandomIntervalBag;
import io.github.scrvrdn.domain.Direction;
import io.github.scrvrdn.domain.IntervalType;
import io.github.scrvrdn.dto.TrackRequest;
import io.github.scrvrdn.services.MusicObjectService;

@Service
public class IntervalService implements MusicObjectService {
    private final Random rng;
    private final RandomDirectionBag directionBag;
    private final RandomIntervalBag intervalBag;

    private IntervalType cachedInterval;

    public IntervalService(Random rng, RandomDirectionBag directionBag, RandomIntervalBag intervalBag) {
        this.rng = rng;
        this.directionBag = directionBag;
        this.intervalBag = intervalBag;
    }

    @Override
    public TrackRequest createRandomRequest() {
        Direction dir = directionBag.peekRandom();
        IntervalType type = intervalBag.peekRandom();
        int midiValue = 30 + rng.nextInt(127);
        cachedInterval = type;
        return createRequest(dir, type, midiValue);
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
    public boolean isLastInterval(IntervalType interval) {
        if (cachedInterval == null) {
            throw new IllegalStateException();
        }

        return cachedInterval == interval;
    }
    
}
