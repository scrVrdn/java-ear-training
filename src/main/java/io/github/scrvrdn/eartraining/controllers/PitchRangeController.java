package io.github.scrvrdn.eartraining.controllers;

import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.controls.PitchRangePanel;
import io.github.scrvrdn.eartraining.services.impl.IntervalSettingsService;

@Controller
public class PitchRangeController {
    private final PitchRangePanel pitchRangePanel;
    private final IntervalSettingsService settingsService;

    public PitchRangeController(PitchRangePanel pitchRangePanel, IntervalSettingsService settingsService) {
        this.pitchRangePanel = pitchRangePanel;
        this.settingsService = settingsService;
        pitchRangePanel.setMaxPitchCallback(this::setMax);
        pitchRangePanel.setMinPitchCallback(this::setMin);

    }

    void loadPitchRange() {
        pitchRangePanel.setMaxPitch(settingsService.getMaxMidiValue());
        pitchRangePanel.setMinPitch(settingsService.getMinMidiValue());
    }

    private void setMax(Integer midiValue) {
        if (midiValue == null) {
            throw new IllegalArgumentException();
        }

        settingsService.setMaxMidiValue(midiValue);
    }

    private void setMin(Integer midiValue) {
        if (midiValue == null) {
            throw new IllegalArgumentException();
        }

        settingsService.setMinMidiValue(midiValue);
    }
}
