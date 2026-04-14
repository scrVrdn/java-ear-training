package io.github.scrvrdn.eartraining.containers;

import java.util.Random;

import org.springframework.stereotype.Component;

import io.github.scrvrdn.eartraining.domain.Direction;

@Component
public final class RandomDirectionBag extends RandomBag<Direction> {
    public RandomDirectionBag(Random rng) {
        super(rng);
    }
}
