package io.github.scrvrdn.eartraining.dto;

import java.util.HashSet;
import java.util.Set;

import io.github.scrvrdn.eartraining.domain.Direction;

public abstract class Preset {
    private int id;
    private String name;
    private Set<Direction> directions = new HashSet<>();
    private int maxMidiValue;
    private int minMidiValue;
    private float tempoInBPM;

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    public void setDirections(Set<Direction> directions) {
        this.directions = directions;
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

    public abstract Preset deepCopy();

 
    
}
