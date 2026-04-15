package io.github.scrvrdn.eartraining.controllers;

import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.services.impl.IntervalSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

@Controller
public class TempoSliderController {

    private static final int MAX_TEMPO = 300;
    private static final int MIN_TEMPO = 60;

    @FXML private Label tempoSectionLabel;
    @FXML private Label tempoLabel;
    @FXML private Slider tempoSlider;

    private final IntervalSettingsService settingsService;

    public TempoSliderController(IntervalSettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @FXML
    private void initialize() {
        tempoSlider.setMax(MAX_TEMPO);
        tempoSlider.setMin(MIN_TEMPO);

        tempoSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            float tempo = (float) this.tempoSlider.getValue();
            settingsService.setTempo(tempo);
            tempoLabel.setText("\u2669 = " + (int) tempo);
        });
    }
    
    void loadTempo() {
        float tempo = settingsService.getTempo();
        tempoSlider.setValue(tempo);
        tempoLabel.setText("\u2669 = " + (int) tempo);
    }

}
