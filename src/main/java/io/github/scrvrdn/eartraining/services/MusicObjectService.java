package io.github.scrvrdn.eartraining.services;

import io.github.scrvrdn.eartraining.dto.TrackRequest;

public interface MusicObjectService<T> {

    TrackRequest createRandomRequest();
    boolean isLastType(T type);
}
