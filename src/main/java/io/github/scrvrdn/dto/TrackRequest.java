package io.github.scrvrdn.dto;

import io.github.scrvrdn.domain.Direction;

public record TrackRequest(Direction dir, int[] midiValues) {}
