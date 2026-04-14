package io.github.scrvrdn.eartraining.services.impl;

import org.springframework.stereotype.Service;

import io.github.scrvrdn.eartraining.containers.RandomDirectionBag;
import io.github.scrvrdn.eartraining.containers.RandomIntervalBag;
import io.github.scrvrdn.eartraining.domain.Direction;
import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.dto.Preset;
import io.github.scrvrdn.eartraining.midi.MidiPlayerService;
import io.github.scrvrdn.eartraining.services.SettingsService;

@Service
public class IntervalSettingsService implements SettingsService<IntervalType> {

    private final RandomDirectionBag directionBag;
    private final RandomIntervalBag intervalBag;
    private final MidiPlayerService midiSequencer;
    private int maxMidiValue;
    private int minMidiValue;

    private Preset<IntervalType> currentPreset;

    public IntervalSettingsService(RandomDirectionBag directionBag, RandomIntervalBag intervalBag, MidiPlayerService midiSequencer) {
        this.directionBag = directionBag;
        this.intervalBag = intervalBag;
        this.midiSequencer = midiSequencer;
        
        this.currentPreset = new Preset<>();

        addAll();
        addAllDirecions();
        maxMidiValue = 108;
        minMidiValue = 30;
        midiSequencer.setTempo(120);
        
    }

    public void init() {
        readFromPreset(currentPreset);
    }

    public void readFromPreset(Preset<IntervalType> preset) {
        readIntervalsFromPreset(preset);
        readDirectionsFromPreset(preset);
        maxMidiValue = preset.getMaxMidiValue();
        minMidiValue = preset.getMinMidiValue();
        midiSequencer.setTempo(preset.getTempoInBPM());
    }

    private void readIntervalsFromPreset(Preset<IntervalType> preset) {
        intervalBag.clear();
        for (IntervalType interval : preset.getAll()) {
            intervalBag.add(interval);
        }
    }

    public void writeToPreset() {
        currentPreset.addAll(intervalBag.toList());
        currentPreset.addAllDirections(directionBag.toList());
        currentPreset.setMaxMidiValue(maxMidiValue);
        currentPreset.setMinMidiValue(minMidiValue);
        currentPreset.setTempoInBPM(midiSequencer.getTempo());
    }

    private void readDirectionsFromPreset(Preset<IntervalType> preset) {
        directionBag.clear();
        for (Direction dir : preset.getAllDirections()) {
            directionBag.add(dir);
        }
    }

    @Override
    public void add(IntervalType interval) {
        intervalBag.add(interval);
    }

    @Override
    public void addAll() {
        intervalBag.clear();
        for (IntervalType interval : IntervalType.values()) {
            intervalBag.add(interval);
        }
    }

    @Override
    public void addDirection(Direction direction) {
        directionBag.add(direction);
    }

    @Override
    public void addAllDirecions() {
        directionBag.clear();
        for (Direction dir : Direction.values()) {
            directionBag.add(dir);
        }
    }

    @Override
    public int getMaxMidiValue() {
        return maxMidiValue;
    }

    @Override
    public int getMinMidiValue() {
        return minMidiValue;
    }

    @Override
    public void remove(IntervalType interval) {
        intervalBag.remove(interval);
    }

    @Override
    public void removeAll() {
        intervalBag.clear();
    }

    @Override
    public void removeDirection(Direction direction) {
        directionBag.remove(direction);
    }

    @Override
    public void removeAllDirections() {
        directionBag.clear();
    }

    @Override
    public void setMaxMidiValue(int newValue) {
        this.maxMidiValue = newValue;
    }

    @Override
    public void setMinMidiValue(int newValue) {
        this.minMidiValue = newValue;        
    }

    @Override
    public void setTempo(float bmp) {
        midiSequencer.setTempo(bmp);
    }
    
}
