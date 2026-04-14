package io.github.scrvrdn.eartraining.services;

import io.github.scrvrdn.eartraining.domain.Direction;
import io.github.scrvrdn.eartraining.dto.Preset;

public interface SettingsService<T> {

    int getMaxMidiValue();
    int getMinMidiValue();
    void setMaxMidiValue(int newValue);
    void setMinMidiValue(int newValue);

    void add(T type);
    void addAll();
    void remove(T type);
    void removeAll();

    void addDirection(Direction direction);
    void addAllDirecions();
    void removeDirection(Direction direction);
    void removeAllDirections();

    void setTempo(float bmp);

    void readFromPreset(Preset<T> preset);
    void writeToPreset();
}
