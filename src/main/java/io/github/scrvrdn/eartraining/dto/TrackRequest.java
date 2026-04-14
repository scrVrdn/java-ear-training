package io.github.scrvrdn.eartraining.dto;

import io.github.scrvrdn.eartraining.domain.Direction;

public record TrackRequest(Direction dir, int[] midiValues) {}
