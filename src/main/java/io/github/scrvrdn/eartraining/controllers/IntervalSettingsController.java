package io.github.scrvrdn.eartraining.controllers;

import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.view.SceneManager;
import io.github.scrvrdn.eartraining.view.scenetypes.SceneType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Controller
public class IntervalSettingsController {
    @FXML private Button backToExerciseSectionButton;
    @FXML private Button backToMainMenuButton;
    
    private final SceneManager sceneManager;
    private final DirectionsSettingsController directionsSettings;
    private final IntervalTypeSettingsController intervalSettings;
    private final PitchRangeController pitchRangeController;
    private final TempoSliderController tempoController;

    public IntervalSettingsController(
        SceneManager sceneManager,
        DirectionsSettingsController directionsSettings,
        IntervalTypeSettingsController intervalSettings,
        PitchRangeController pitchRangeController,
        TempoSliderController tempoController
    ) {
        this.sceneManager = sceneManager;
        this.directionsSettings = directionsSettings;
        this.intervalSettings = intervalSettings;
        this.pitchRangeController = pitchRangeController;
        this.tempoController = tempoController;
    }

    @FXML
    private void initialize() {
        directionsSettings.loadDirections();
        intervalSettings.loadIntervals();
        pitchRangeController.load();
        tempoController.loadTempo();
    }

    @FXML
    private void handleBackToExerciseSectionButton() {
        sceneManager.showScene(SceneType.INTERVAL_SECTION);
    }

    @FXML
    private void handleBackToMainMenuButton() {
        sceneManager.showScene(SceneType.MAIN_MENU);
    }
}
