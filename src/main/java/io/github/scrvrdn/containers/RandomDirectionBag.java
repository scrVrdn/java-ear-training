package io.github.scrvrdn.containers;

import java.util.Random;

import org.springframework.stereotype.Component;

import io.github.scrvrdn.domain.Direction;

@Component
public final class RandomDirectionBag extends RandomBag<Direction> {
    public RandomDirectionBag(Random rng) {
        super(rng);
    }
}
