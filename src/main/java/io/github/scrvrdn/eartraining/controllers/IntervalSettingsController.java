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

    public IntervalSettingsController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
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
