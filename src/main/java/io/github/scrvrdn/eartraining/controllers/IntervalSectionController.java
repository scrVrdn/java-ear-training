package io.github.scrvrdn.eartraining.controllers;

import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.controls.IntervalButtonPanel;
import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.services.EarTrainingService;
import io.github.scrvrdn.eartraining.view.SceneManager;
import io.github.scrvrdn.eartraining.view.scenetypes.SceneType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Controller
public class IntervalSectionController {

    @FXML Button backToMainManuButton;
    @FXML Button goToSettingsButton;
    @FXML Button nextIntervalButton;
    @FXML Button replayIntervalButton;

    private final EarTrainingService earTrainingService;
    private final SceneManager sceneManager;
    private final IntervalButtonPanel answerButtons;

    public IntervalSectionController(SceneManager sceneManager, EarTrainingService earTrainingService, IntervalButtonPanel answerButtons) {
        this.earTrainingService = earTrainingService;
        this.sceneManager = sceneManager;
        this.answerButtons = answerButtons;
    }

    @FXML
    private void initialize() {
        answerButtons.setCallback(this::handleAnswerButton);
    }

    @FXML
    private void handleBackToMainMenuButton() {
        sceneManager.showScene(SceneType.MAIN_MENU);
    }

    @FXML
    private void handleGoToSettingsButton() {
        sceneManager.showScene(SceneType.INTERVAL_SETTINGS);
    }

    @FXML
    private void handleNextIntervalButton() {
        answerButtons.resetButtonColors();

        try {
          earTrainingService.playNewInterval();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void handleReplayIntervalButton() {
        try {
            earTrainingService.replayInterval();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void handleAnswerButton(IntervalType interval) {
        if (earTrainingService.isLastInterval(interval)) {
            handleNextIntervalButton();
        } else {
            answerButtons.setColorToRed(interval);
        }
    }
}
