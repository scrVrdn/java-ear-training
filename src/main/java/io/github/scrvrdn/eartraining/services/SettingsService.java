package io.github.scrvrdn.eartraining.services;

import java.util.Set;

import io.github.scrvrdn.eartraining.domain.Direction;
import io.github.scrvrdn.eartraining.model.PresetModel;

public interface SettingsService<T> {

    int getMaxMidiValue();
    int getMinMidiValue();
    void setMaxMidiValue(int newValue);
    void setMinMidiValue(int newValue);

    void add(T type);
    void addAll();
    Set<T> getAll();
    void remove(T type);
    void removeAll();
    boolean isEmpty();

    void addDirection(Direction direction);
    void addAllDirecions();
    Set<Direction> getAllDirections();
    void removeDirection(Direction direction);
    void removeAllDirections();
    boolean directionsAreEmpty();

    float getTempo();
    void setTempo(float bmp);

    void loadPresetById(int id);
    void loadFallbackPreset();
    void writeToCurrentPreset();
    void writeToPresetById(int id);
    PresetModel writeToNewPreset(String name);
}
