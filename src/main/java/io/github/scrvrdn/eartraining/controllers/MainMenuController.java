package io.github.scrvrdn.eartraining.controllers;

import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.view.SceneManager;
import io.github.scrvrdn.eartraining.view.scenetypes.SceneType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Controller
public class MainMenuController {

  private final SceneManager sceneManager;

    @FXML private Button intervalSectionButton;

    
    public MainMenuController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private void handleIntervalSectionButton() {
        sceneManager.showScene(SceneType.INTERVAL_SECTION);
    }
}
