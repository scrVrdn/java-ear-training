package io.github.scrvrdn.eartraining.containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public abstract class RandomBag<T> {
    private final Random rng;
    private final List<T> bag;

    RandomBag(Random rng, int capacity) {
        this.rng = rng;
        this.bag = new ArrayList<>(capacity);
    }


    public T peekRandom() {
        if (bag.isEmpty()) {
            throw new IllegalStateException();
        }

        return bag.get(rng.nextInt(bag.size()));
    }

    public void add(T bagItem) {
        if (bag.contains(bagItem)) {
            return;
        }

        bag.add(bagItem);
    }

    public void clear() {
        bag.clear();
    }

    public List<T> toList() {
        return Collections.unmodifiableList(bag);
    }

    public void remove(T bagItem) {
        if (bag.isEmpty()) {
            throw new IllegalStateException();
        }

        bag.remove(bagItem);
    }

    public boolean isEmpty() {
        return bag.isEmpty();
    }

}
