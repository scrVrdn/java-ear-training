package io.github.scrvrdn.eartraining.containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public abstract class RandomBag<T> {
    private final Random rng;
    private final List<T> bag;

    RandomBag(Random rng, int capacity) {
        this.rng = rng;
        this.bag = new ArrayList<>(capacity);
    }


    public T peekRandom() {
        if (bag.isEmpty()) {
            throw new IllegalStateException("Tried to peek at random item but bag is empty.");
        }

        return bag.get(rng.nextInt(bag.size()));
    }

    public void add(T bagItem) {
        if (bag.contains(bagItem)) {
            throw new IllegalStateException("Tried to add item " + bagItem + " but the bag already contains this item.");
        }

        bag.add(bagItem);
    }

    public void clear() {
        bag.clear();
    }

    public List<T> toList() {
        return Collections.unmodifiableList(bag);
    }

    public Set<T> getAll() {
        Set<T> set = new HashSet<>(bag);
        return set;
    }

    public void remove(T bagItem) {
        if (bag.isEmpty()) {
            throw new IllegalStateException("Tried to remove item " + bagItem + " but bag is empty.");
        }

        bag.remove(bagItem);
    }

    public boolean isEmpty() {
        return bag.isEmpty();
    }

}
