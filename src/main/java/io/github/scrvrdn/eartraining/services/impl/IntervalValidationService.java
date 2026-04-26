package io.github.scrvrdn.eartraining.services.impl;

import org.springframework.stereotype.Service;

import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.services.SettingsService;
import io.github.scrvrdn.eartraining.services.ValidationService;

@Service
public class IntervalValidationService implements ValidationService<IntervalType> {

    private final SettingsService<IntervalType> settingsService;

    public IntervalValidationService(SettingsService<IntervalType> settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public boolean validateDirections() {
        return !settingsService.directionsAreEmpty();
    }

    @Override
    public boolean validateMusicObjects() {
        return !settingsService.isEmpty();
    }
    
}
