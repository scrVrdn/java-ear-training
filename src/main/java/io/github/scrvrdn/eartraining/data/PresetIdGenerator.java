package io.github.scrvrdn.eartraining.data;


public final class PresetIdGenerator {
    
    public int nextId = 1;

    public synchronized int next() {
        return nextId++;
    }
}
