package io.github.scrvrdn.eartraining.controls;


import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.domain.PitchClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

@Component
public class PitchRangePanel {
    private static final int MAX_MIDI_VALUE = 108;
    private static final int MIN_MIDI_VALUE = 21;
    private static final int MIN_SPAN = IntervalType.VALUES.length;

    @FXML private Spinner<Integer> maxPitchSpinner;
    @FXML private Spinner<Integer> minPitchSpinner;
    @FXML private Label maxPitchLabel;
    @FXML private Label minPitchLabel;

    private Consumer<Integer> maxPitchCallback;
    private Consumer<Integer> minPitchCallback;

    @FXML
    private void initialize() {
        initMaxPitchSpinner();
        initMinPitchSpinner();
    }

    private void initMaxPitchSpinner() {
        SpinnerValueFactory<Integer> maxPitchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_MIDI_VALUE + MIN_SPAN, MAX_MIDI_VALUE);
        maxPitchSpinner.setValueFactory(maxPitchValueFactory);

        this.maxPitchSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            maxPitchCallback.accept(newValue);
            maxPitchLabel.setText(getPitchName(newValue));
            int minValue = newValue - MIN_SPAN;
            if (minPitchSpinner.getValue() > minValue) {
                this.minPitchSpinner.getValueFactory().setValue(minValue);
                minPitchCallback.accept(minValue);
                minPitchLabel.setText(getPitchName(minValue));
            }
        });
    }

    private void initMinPitchSpinner() {
        SpinnerValueFactory<Integer> minPitchValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_MIDI_VALUE, MAX_MIDI_VALUE - MIN_SPAN);
        minPitchSpinner.setValueFactory(minPitchValueFactory);
        
        minPitchSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            
            minPitchCallback.accept(newValue);
            minPitchLabel.setText(getPitchName(newValue));
            int maxValue = newValue + MIN_SPAN;
            if (maxPitchSpinner.getValue() < maxValue) {
                maxPitchSpinner.getValueFactory().setValue(maxValue);
                maxPitchCallback.accept(maxValue);
                maxPitchLabel.setText(getPitchName(maxValue));
            }
        });
    }

    public void setMaxPitchCallback(Consumer<Integer> callback) {
        maxPitchCallback = callback;
    }

    public void setMinPitchCallback(Consumer<Integer> callback) {
        minPitchCallback = callback;
    }

    public void setMaxPitch(int midiValue) {
        maxPitchSpinner.getValueFactory().setValue(midiValue);
        maxPitchLabel.setText(getPitchName(midiValue));
    }

    public void setMinPitch(int midiValue) {
        minPitchSpinner.getValueFactory().setValue(midiValue);
        minPitchLabel.setText(getPitchName(midiValue));
    }

     private String getPitchName(int midiValue) {
        PitchClass p = PitchClass.getPitchClassOfMidiValue(midiValue);
        int octave = (midiValue - 12) / 12;
        return p.toString() + octave;
    }

}
