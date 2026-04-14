package io.github.scrvrdn.eartraining.dto;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.scrvrdn.eartraining.domain.Direction;

public class Preset<T> {
    private final Set<T> musicObjects = new HashSet<>();
    private final Set<Direction> directions = new HashSet<>();
    private int maxMidiValue;
    private int minMidiValue;
    private float tempoInBPM;

    
    public Set<T> getAll() {
        System.out.println("before getting: " + musicObjects.size());
        return Collections.unmodifiableSet(musicObjects);
    }

    public void addAll(List<T> list) {
        musicObjects.clear();
        for (T musicObject : list) {
            System.out.println("adding intervals: " + musicObject);
            musicObjects.add(musicObject);
        }
        System.out.println("after adding " + musicObjects.size());
    }

    public Set<Direction> getAllDirections() {
        return Collections.unmodifiableSet(directions);
    }

    public void addAllDirections(List<Direction> list) {
        musicObjects.clear();
        for (Direction dir : list) {
            directions.add(dir);
        }
    }

    public int getMaxMidiValue() {
        return maxMidiValue;
    }

    public void setMaxMidiValue(int maxMidiValue) {
        this.maxMidiValue = maxMidiValue;
    }

    public int getMinMidiValue() {
        return minMidiValue;
    }

    public void setMinMidiValue(int minMidiValue) {
        this.minMidiValue = minMidiValue;
    }

    public float getTempoInBPM() {
        return tempoInBPM;
    }

    public void setTempoInBPM(float tempoInBPM) {
        this.tempoInBPM = tempoInBPM;
    }
    
}
