package io.github.scrvrdn.services;

import io.github.scrvrdn.domain.IntervalType;

public interface EarTrainingService {

    void playNewInterval() throws Exception;
    boolean isLastInterval(IntervalType interval);
}
