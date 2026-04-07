package io.github.scrvrdn.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class RandomBag<T> {
    private final Random rng;
    private final List<T> bag = new ArrayList<>();

    RandomBag(Random rng) {
        this.rng = rng;
    }


    public T peekRandom() {
        if (bag.isEmpty()) {
            throw new IllegalStateException();
        }

        return bag.get(rng.nextInt(bag.size()));
    }

    public void add(T bagItem) {
       bag.add(bagItem);
    }

    public void remove(T bagItem) {
        if (bag.isEmpty()) {
            throw new IllegalStateException();
        }

        bag.remove(bagItem);
    }
}
