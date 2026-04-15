package io.github.scrvrdn.eartraining.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.scrvrdn.eartraining.containers.RandomBag;
import io.github.scrvrdn.eartraining.containers.RandomDirectionBag;
import io.github.scrvrdn.eartraining.containers.RandomIntervalBag;
import io.github.scrvrdn.eartraining.domain.Direction;
import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.dto.Preset;
import io.github.scrvrdn.eartraining.midi.MidiPlayerService;
import io.github.scrvrdn.eartraining.services.SettingsService;

@Service
public class IntervalSettingsService implements SettingsService<IntervalType> {

    private final RandomBag<Direction> directionBag;
    private final RandomBag<IntervalType> intervalBag;
    private final MidiPlayerService midiService;
    private int maxMidiValue;
    private int minMidiValue;

    private Preset<IntervalType> currentPreset;

    public IntervalSettingsService(RandomDirectionBag directionBag, RandomIntervalBag intervalBag, MidiPlayerService midiSequencer) {
        this.directionBag = directionBag;
        this.intervalBag = intervalBag;
        this.midiService = midiSequencer;
        
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
        midiService.setTempo(preset.getTempoInBPM());
    }

    private void readIntervalsFromPreset(Preset<IntervalType> preset) {
        for (IntervalType interval : preset.getAll()) {
            intervalBag.add(interval);
        }
    }

    public void writeToPreset() {
        currentPreset.addAll(intervalBag.toList());
        currentPreset.addAllDirections(directionBag.toList());
        currentPreset.setMaxMidiValue(maxMidiValue);
        currentPreset.setMinMidiValue(minMidiValue);
        currentPreset.setTempoInBPM(midiService.getTempo());
    }

    private void readDirectionsFromPreset(Preset<IntervalType> preset) {
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
        for (IntervalType interval : IntervalType.VALUES) {
            intervalBag.add(interval);
        }
    }

    @Override
    public List<IntervalType> getAll() {
        return intervalBag.toList();
    }

    @Override
    public void addDirection(Direction direction) {
        directionBag.add(direction);
    }

    @Override
    public void addAllDirecions() {
        for (Direction dir : Direction.VALUES) {
            directionBag.add(dir);
        }
    }

    @Override
    public List<Direction> getAllDirections() {
        return directionBag.toList();
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
    public float getTempo() {
        return midiService.getTempo();
    }

    @Override
    public void setTempo(float bmp) {
        midiService.setTempo(bmp);
    }
}
