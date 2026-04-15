package io.github.scrvrdn.eartraining.controllers;

import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.controls.DirectionsPanel;
import io.github.scrvrdn.eartraining.domain.Direction;
import io.github.scrvrdn.eartraining.services.impl.IntervalSettingsService;

@Controller
public class DirectionsSettingsController {

    private final DirectionsPanel directions;
    private final IntervalSettingsService settingsService;

    public DirectionsSettingsController(DirectionsPanel directions, IntervalSettingsService settingsService) {
        this.directions = directions;
        this.settingsService = settingsService;
        directions.setAddDirectionCallback(this::addDirection);
        directions.setRemoveDirectionCallback(this::removeDirection);
        
    }

    private void addDirection(Direction dir) {
        settingsService.addDirection(dir);
    }

    private void removeDirection(Direction dir) {
        settingsService.removeDirection(dir);
    }

    void loadDirections() {
        for (Direction dir : settingsService.getAllDirections()) {
            directions.toggleCheckBox(dir);
        }
    }
}
