package io.github.scrvrdn.eartraining.controllers;

import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.controls.IntervalTypeSettingsPanel;
import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.services.impl.IntervalSettingsService;

@Controller
public class IntervalTypeSettingsController {
    
    private final IntervalTypeSettingsPanel intervalPanel;
    private final IntervalSettingsService settingsService;

    public IntervalTypeSettingsController(IntervalTypeSettingsPanel intervalPanel, IntervalSettingsService settingsService) {
        this.intervalPanel = intervalPanel;
        this.settingsService = settingsService;

        intervalPanel.setAddIntervalCallback(this::addInterval);
        intervalPanel.setRemoveIntervalCallback(this::removeInterval);
    }

    private void addInterval(IntervalType interval) {
        settingsService.add(interval);
    }

    private void removeInterval(IntervalType interval) {
        settingsService.remove(interval);
    }

    void loadIntervals() {
        for (IntervalType interval : settingsService.getAll()) {
            intervalPanel.toggleCheckbox(interval);
        }
    }
}
