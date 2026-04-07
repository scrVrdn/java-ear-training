package io.github.scrvrdn.services;

import io.github.scrvrdn.domain.IntervalType;
import io.github.scrvrdn.dto.TrackRequest;

public interface MusicObjectService {

    TrackRequest createRandomRequest();
    boolean isLastInterval(IntervalType interval);
}
