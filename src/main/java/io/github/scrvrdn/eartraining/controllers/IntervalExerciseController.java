package io.github.scrvrdn.eartraining.controllers;

import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.controls.IntervalButtonPanel;
import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.services.EarTrainingService;
import io.github.scrvrdn.eartraining.view.SceneManager;
import io.github.scrvrdn.eartraining.view.scenetypes.SceneType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Controller
public class IntervalExerciseController {

    @FXML Button backToMainManuButton;
    @FXML Button goToSettingsButton;
    @FXML Button startButton;
    @FXML Button nextIntervalButton;
    @FXML Button replayIntervalButton;

    private final EarTrainingService earTrainingService;
    private final SceneManager sceneManager;
    private final IntervalButtonPanel answerButtons;

    public IntervalExerciseController(SceneManager sceneManager, EarTrainingService earTrainingService, IntervalButtonPanel answerButtons) {
        this.earTrainingService = earTrainingService;
        this.sceneManager = sceneManager;
        this.answerButtons = answerButtons;
    }

    @FXML
    private void initialize() {
        answerButtons.setPredicate(this::handleAnswerButton);
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
    private void handleStartButton(ActionEvent event) {
        nextIntervalButton.setDisable(false);
        replayIntervalButton.setDisable(false);
        startButton.setDisable(true);
        answerButtons.setRunning(true);
        handleNextIntervalButton();
    }

    @FXML
    private void handleNextIntervalButton() {
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

    private boolean handleAnswerButton(IntervalType interval) {

        boolean isCorrect = earTrainingService.isLastInterval(interval);
        if (isCorrect) {
            handleNextIntervalButton();
            return true;
        }

        return false;
    }
}
