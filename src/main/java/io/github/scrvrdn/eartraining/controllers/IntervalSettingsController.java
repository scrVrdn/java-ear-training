package io.github.scrvrdn.eartraining.controllers;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.events.TriggerRefreshEvent;
import io.github.scrvrdn.eartraining.services.ValidationService;
import io.github.scrvrdn.eartraining.view.SceneManager;
import io.github.scrvrdn.eartraining.view.scenetypes.SceneType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

@Controller
public class IntervalSettingsController implements ApplicationListener<TriggerRefreshEvent> {
    @FXML private Button backToExerciseSectionButton;
    @FXML private Button backToMainMenuButton;
    
    private final SceneManager sceneManager;
    private final DirectionsSettingsController directionsSettings;
    private final IntervalTypeSettingsController intervalSettings;
    private final PitchRangeController pitchRangeController;
    private final TempoSliderController tempoController;
    private final ValidationService<IntervalType> validationService;

    public IntervalSettingsController(
        SceneManager sceneManager,
        DirectionsSettingsController directionsSettings,
        IntervalTypeSettingsController intervalSettings,
        PitchRangeController pitchRangeController,
        TempoSliderController tempoController,
        ValidationService<IntervalType> validationService
    ) {
        this.sceneManager = sceneManager;
        this.directionsSettings = directionsSettings;
        this.intervalSettings = intervalSettings;
        this.pitchRangeController = pitchRangeController;
        this.tempoController = tempoController;
        this.validationService = validationService;
    }


    @Override
    public void onApplicationEvent(TriggerRefreshEvent event) {
        directionsSettings.loadDirections();
        intervalSettings.loadIntervals();
        pitchRangeController.loadPitchRange();
        tempoController.loadTempo();
    }

    @FXML
    private void handleBackToExerciseSectionButton(ActionEvent event) {
        if (!validateSettings()) {
            return;
        }

        sceneManager.showScene(SceneType.INTERVAL_SECTION);
    }

    private boolean validateSettings() {
        if (!validationService.validateMusicObjects()) {
            alertUser("At least one interval must be selected.");
            return false;
        }

        if (!validationService.validateDirections()) {
            alertUser("At least one direction must be selected.");
            return false;
        }

        return true;
    }

    private void alertUser(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(message);
        alert.showAndWait();
    }



    @FXML
    private void handleBackToMainMenuButton() {
        sceneManager.showScene(SceneType.MAIN_MENU);
    }
}
