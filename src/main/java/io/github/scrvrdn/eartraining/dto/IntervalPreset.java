package io.github.scrvrdn.eartraining.dto;

import java.util.HashSet;
import java.util.Set;

import io.github.scrvrdn.eartraining.domain.IntervalType;


public class IntervalPreset extends Preset {
    private Set<IntervalType> intervals = new HashSet<>();

    public Set<IntervalType> getIntervals() {
        return intervals;
    }

    public void setIntervals(Set<IntervalType> intervals) {
        this.intervals = intervals;
    }

    @Override
    public IntervalPreset deepCopy() {
        IntervalPreset copy = new IntervalPreset();
        copy.setId(getId());
        copy.setName(getName());
        copy.intervals = new HashSet<>(intervals);
        copy.setDirections(new HashSet<>(getDirections()));
        copy.setMaxMidiValue(getMaxMidiValue());
        copy.setMinMidiValue(getMinMidiValue());
        copy.setTempoInBPM(getTempoInBPM());
        return copy;
    }

    @Override
    public String toString() {
        return "IntervalPreset [intervals=" + intervals + ", getIntervals()=" + getIntervals() + ", getId()=" + getId()
                + ", getName()=" + getName() + ", getDirections()=" + getDirections() + ", getMaxMidiValue()="
                + getMaxMidiValue() + ", getMinMidiValue()=" + getMinMidiValue() + ", getTempoInBPM()="
                + getTempoInBPM() + "]";
    }

}
