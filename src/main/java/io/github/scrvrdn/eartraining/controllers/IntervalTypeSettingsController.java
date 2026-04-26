package io.github.scrvrdn.eartraining.controllers;

import java.util.Set;

import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.controls.IntervalButtonPanel;
import io.github.scrvrdn.eartraining.controls.IntervalTypeSettingsPanel;
import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.services.impl.IntervalSettingsService;

@Controller
public class IntervalTypeSettingsController {
    
    private final IntervalTypeSettingsPanel intervalPanel;
    private final IntervalButtonPanel intervalButtons;
    private final IntervalSettingsService settingsService;

    public IntervalTypeSettingsController(IntervalTypeSettingsPanel intervalPanel, IntervalButtonPanel intervalButtons, IntervalSettingsService settingsService) {
        this.intervalPanel = intervalPanel;
        this.intervalButtons = intervalButtons;
        this.settingsService = settingsService;

        intervalPanel.setAddIntervalCallback(this::addInterval);
        intervalPanel.setRemoveIntervalCallback(this::removeInterval);
    }

    private void addInterval(IntervalType interval) {
        settingsService.add(interval);
        intervalButtons.enable(interval);
    }

    private void removeInterval(IntervalType interval) {
        settingsService.remove(interval);
        intervalButtons.disable(interval);
    }

    void loadIntervals() {
        Set<IntervalType> intervals = settingsService.getAll();
        intervalPanel.toggleCheckboxes(intervals::contains);
        intervalButtons.toggleButtons(interval -> !intervals.contains(interval));
    }
}
