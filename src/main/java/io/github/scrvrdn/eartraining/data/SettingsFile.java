package io.github.scrvrdn.eartraining.data;

import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.scrvrdn.eartraining.dto.IntervalPreset;

public class SettingsFile {
    private Map<Integer, IntervalPreset> intervalPresets;
    private Integer currentIntervalPresetId;
    private PresetIdGenerator idGenerator;

    public SettingsFile() {}

    public SettingsFile(Map<Integer, IntervalPreset> intervalPresets, Integer currentIntervalPresetId, PresetIdGenerator idGenerator) {
        this.intervalPresets = intervalPresets;
        this.currentIntervalPresetId = currentIntervalPresetId;
        this.idGenerator = idGenerator;
    }

    public Map<Integer, IntervalPreset> getIntervalPresets() {
        return intervalPresets;
    }

    public void setIntervalPresets(Map<Integer, IntervalPreset> intervalPresets) {
        this.intervalPresets = intervalPresets;
    }

    public Integer getCurrentIntervalPresetId() {
        return currentIntervalPresetId;
    }

    @JsonIgnore
    public Optional<Integer> getCurrentIdAsOptional() {
        return Optional.ofNullable(currentIntervalPresetId);
    }

    public void setCurrentIntervalPresetId(Integer id) {
        this.currentIntervalPresetId = id;
    }

    public PresetIdGenerator getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(PresetIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }
    
}
