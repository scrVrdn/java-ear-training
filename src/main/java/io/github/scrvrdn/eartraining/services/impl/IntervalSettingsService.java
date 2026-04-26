package io.github.scrvrdn.eartraining.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.scrvrdn.eartraining.containers.RandomBag;
import io.github.scrvrdn.eartraining.containers.RandomDirectionBag;
import io.github.scrvrdn.eartraining.containers.RandomIntervalBag;
import io.github.scrvrdn.eartraining.domain.Direction;
import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.dto.IntervalPreset;
import io.github.scrvrdn.eartraining.midi.MidiPlayerService;
import io.github.scrvrdn.eartraining.model.PresetModel;
import io.github.scrvrdn.eartraining.services.PresetService;
import io.github.scrvrdn.eartraining.services.SettingsService;

@Service
public class IntervalSettingsService implements SettingsService<IntervalType> {
    
    private final RandomBag<Direction> directionBag;
    private final RandomBag<IntervalType> intervalBag;
    private final MidiPlayerService midiService;
    private final PresetService<IntervalPreset> presetService;
    private int maxMidiValue;
    private int minMidiValue;

    private IntervalPreset currentPreset;

    public IntervalSettingsService(RandomDirectionBag directionBag, RandomIntervalBag intervalBag, MidiPlayerService midiSequencer, @Qualifier("intervalPresetService") PresetService<IntervalPreset> presetService) {
        this.directionBag = directionBag;
        this.intervalBag = intervalBag;
        this.midiService = midiSequencer;
        this.presetService = presetService;
    }

    @Override
    public void loadPresetById(int id) {
        currentPreset = presetService.findById(id);
        readFromPreset(currentPreset);
    }

    @Override
    public void loadFallbackPreset() {
        currentPreset = presetService.findCurrentPreset();
        readFromPreset(currentPreset);
    }

   private void readFromPreset(IntervalPreset preset) {
        readIntervalsFromPreset(preset);
        readDirectionsFromPreset(preset);
        maxMidiValue = preset.getMaxMidiValue();
        minMidiValue = preset.getMinMidiValue();
        midiService.setTempo(preset.getTempoInBPM());
    }

    private void readIntervalsFromPreset(IntervalPreset preset) {
        intervalBag.clear();
        preset.getIntervals().forEach(intervalBag::add);
    }

    private void readDirectionsFromPreset(IntervalPreset preset) {
        directionBag.clear();
        preset.getDirections().forEach(directionBag::add);
    }

    @Override
    public void writeToCurrentPreset() {
        writeToPreset(currentPreset);
    }

    @Override
    public void writeToPresetById(int id) {
        IntervalPreset preset = presetService.findById(id);
        writeToPreset(preset);
    }

    private void writeToPreset(IntervalPreset preset) {
        preset.setIntervals(getAll());
        preset.setDirections(getAllDirections());
        preset.setMaxMidiValue(maxMidiValue);
        preset.setMinMidiValue(minMidiValue);
        preset.setTempoInBPM(midiService.getTempo());
    }

    @Override
    public PresetModel writeToNewPreset(String name) {
        IntervalPreset preset = new IntervalPreset();
        preset.setName(name);
        preset.setIntervals(getAll());
        preset.setDirections(getAllDirections());
        preset.setMaxMidiValue(maxMidiValue);
        preset.setMinMidiValue(minMidiValue);
        preset.setTempoInBPM(midiService.getTempo());
        
        int id = presetService.createPreset(preset);
        preset.setId(id);
        currentPreset = preset;
        return new PresetModel(id, name);
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
    public Set<IntervalType> getAll() {
        return intervalBag.getAll();
    }

    @Override
    public boolean isEmpty() {
        return intervalBag.isEmpty();
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
    public Set<Direction> getAllDirections() {
        return directionBag.getAll();
    }

    @Override
    public boolean directionsAreEmpty() {
        return directionBag.isEmpty();
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
