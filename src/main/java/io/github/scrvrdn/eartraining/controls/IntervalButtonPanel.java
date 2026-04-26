package io.github.scrvrdn.eartraining.controls;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import io.github.scrvrdn.eartraining.domain.IntervalType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Component
public class IntervalButtonPanel {

    @FXML private Button unison;
    @FXML private Button minSecond;
    @FXML private Button majSecond;
    @FXML private Button minThird;
    @FXML private Button majThird;
    @FXML private Button fourth;
    @FXML private Button tritone;
    @FXML private Button fifth;
    @FXML private Button minSixth;
    @FXML private Button majSixth;
    @FXML private Button minSeventh;
    @FXML private Button majSeventh;
    @FXML private Button octave;
    @FXML private Button minNinth;
    @FXML private Button majNinth;
    @FXML private Button minTenth;
    @FXML private Button majTenth;
    @FXML private Button eleventh;
    @FXML private Button augEleventh;
    @FXML private Button twelfth;

    //private Button[] buttonArray;
    private final Map<IntervalType, Button> intervalMap = new HashMap<>();

    private Predicate<IntervalType> predicate;
    private boolean isRunning = false;

  

    @FXML
    private void initialize() {
        addIntervalTypeToButtons();
        initIntervalMap();

        // buttonArray = new Button[]{unison, minSecond, majSecond, minThird, majThird, fourth, tritone, fifth, minSixth, majSixth, minSeventh, majSeventh, octave, minNinth, majNinth, minTenth, majTenth, eleventh, augEleventh, twelfth};
    }

    private void addIntervalTypeToButtons() {
            unison.setUserData(IntervalType.UNISON);
            minSecond.setUserData(IntervalType.MINOR_SECOND);
            majSecond.setUserData(IntervalType.MAJOR_SECOND);
            minThird.setUserData(IntervalType.MINOR_THIRD);
            majThird.setUserData(IntervalType.MAJOR_THIRD);
            fourth.setUserData(IntervalType.PERFECT_FOURTH);
            tritone.setUserData(IntervalType.TRITONE);
            fifth.setUserData(IntervalType.PERFECT_FIFTH);
            minSixth.setUserData(IntervalType.MINOR_SIXTH);
            majSixth.setUserData(IntervalType.MAJOR_SIXTH);
            minSeventh.setUserData(IntervalType.MINOR_SEVENTH);
            majSeventh.setUserData(IntervalType.MAJOR_SEVENTH);
            octave.setUserData(IntervalType.OCTAVE);
            minNinth.setUserData(IntervalType.MINOR_NINTH);
            majNinth.setUserData(IntervalType.MAJOR_NINTH);
            minTenth.setUserData(IntervalType.MINOR_TENTH);
            majTenth.setUserData(IntervalType.MAJOR_TENTH);
            eleventh.setUserData(IntervalType.PERFECT_ELEVENTH);
            augEleventh.setUserData(IntervalType.AUGMENTED_ELEVENTH);
            twelfth.setUserData(IntervalType.PERFECT_TWELFTH);
    }

     private void initIntervalMap() {
        intervalMap.put(IntervalType.UNISON, unison);
        intervalMap.put(IntervalType.MINOR_SECOND, minSecond);
        intervalMap.put(IntervalType.MAJOR_SECOND, majSecond);
        intervalMap.put(IntervalType.MINOR_THIRD, minThird);
        intervalMap.put(IntervalType.MAJOR_THIRD, majThird);
        intervalMap.put(IntervalType.PERFECT_FOURTH, fourth);
        intervalMap.put(IntervalType.TRITONE, tritone);
        intervalMap.put(IntervalType.PERFECT_FIFTH, fifth);    
        intervalMap.put(IntervalType.MINOR_SIXTH, minSixth);
        intervalMap.put(IntervalType.MAJOR_SIXTH, majSixth);
        intervalMap.put(IntervalType.MINOR_SEVENTH, minSeventh);
        intervalMap.put(IntervalType.MAJOR_SEVENTH, majSeventh);
        intervalMap.put(IntervalType.OCTAVE, octave);
        intervalMap.put(IntervalType.MINOR_NINTH, minNinth);
        intervalMap.put(IntervalType.MAJOR_NINTH, majNinth);
        intervalMap.put(IntervalType.MINOR_TENTH, minTenth);
        intervalMap.put(IntervalType.MAJOR_TENTH, majTenth);
        intervalMap.put(IntervalType.PERFECT_ELEVENTH, eleventh);
        intervalMap.put(IntervalType.AUGMENTED_ELEVENTH, augEleventh);
        intervalMap.put(IntervalType.PERFECT_TWELFTH, twelfth);
    }

    public void setPredicate(Predicate<IntervalType> predicate) {
        this.predicate = predicate;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void toggleButtons(Predicate<IntervalType> disabled) {
        for (Button button : intervalMap.values()) {
            button.setDisable(disabled.test((IntervalType) button.getUserData()));
        }        
    }

    public void enable(IntervalType interval) {
        Button button = intervalMap.get(interval);
        button.setDisable(false);
    }

    public void disable(IntervalType interval) {
        Button button = intervalMap.get(interval);
        button.setDisable(true);
    }
    @FXML
    private void handleAnswerButton(ActionEvent event) {
        if (!isRunning) {
            return;
        }

        Button button = (Button) event.getSource();
        boolean isCorrect = predicate.test((IntervalType) button.getUserData());
        if (!isCorrect) {
            setColorToRed(button);
        } else {
            resetButtonColors();
        }
    }

    private void setColorToRed(Button button) {
        if (!button.getStyleClass().contains("wrong-answer")) {
            button.getStyleClass().add("wrong-answer");
        } 
    }

    private void resetButtonColors() {
        for (Button button : intervalMap.values()) {
            button.getStyleClass().remove("wrong-answer");
        }
    }

   
}
