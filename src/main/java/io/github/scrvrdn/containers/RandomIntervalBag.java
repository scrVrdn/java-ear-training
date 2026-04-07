package io.github.scrvrdn.containers;

import java.util.Random;

import org.springframework.stereotype.Component;

import io.github.scrvrdn.domain.IntervalType;

@Component
public final class RandomIntervalBag extends RandomBag<IntervalType> {
    public RandomIntervalBag(Random rng) {
        super(rng);
    }
  
}
