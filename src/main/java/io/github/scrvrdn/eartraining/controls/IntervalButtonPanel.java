package io.github.scrvrdn.eartraining.controls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

    private final Map<IntervalType, Button> buttons = new HashMap<>();
    private Consumer<IntervalType> callback;


    @FXML
    private void initialize() {
        addIntervalTypeToButtons();
        initButtonMap();
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

    private void initButtonMap() {
        buttons.put(IntervalType.UNISON, unison);
        buttons.put(IntervalType.MINOR_SECOND, minSecond);
        buttons.put(IntervalType.MAJOR_SECOND, majSecond);
        buttons.put(IntervalType.MINOR_THIRD, minThird);
        buttons.put(IntervalType.MAJOR_THIRD, majThird);
        buttons.put(IntervalType.PERFECT_FOURTH, fourth);
        buttons.put(IntervalType.TRITONE, tritone);
        buttons.put(IntervalType.PERFECT_FIFTH, fifth);    
        buttons.put(IntervalType.MINOR_SIXTH, minSixth);
        buttons.put(IntervalType.MAJOR_SIXTH, majSixth);
        buttons.put(IntervalType.MINOR_SEVENTH, minSeventh);
        buttons.put(IntervalType.MAJOR_SEVENTH, majSeventh);
        buttons.put(IntervalType.OCTAVE, octave);
        buttons.put(IntervalType.MINOR_NINTH, minNinth);
        buttons.put(IntervalType.MAJOR_NINTH, majNinth);
        buttons.put(IntervalType.MINOR_TENTH, minTenth);
        buttons.put(IntervalType.MAJOR_TENTH, majTenth);
        buttons.put(IntervalType.PERFECT_ELEVENTH, eleventh);
        buttons.put(IntervalType.AUGMENTED_ELEVENTH, augEleventh);
        buttons.put(IntervalType.PERFECT_TWELFTH, twelfth);
    }

    

    public void setCallback(Consumer<IntervalType> callback) {
        this.callback = callback;
    }

    public void resetButtonColors() {
        for (Button button : buttons.values()) {
            button.getStyleClass().remove("wrong-answer");
        }
    }

    public void setColorToRed(IntervalType interval) {
        Button button = buttons.get(interval);
        if (!button.getStyleClass().contains("wrong-answer")) {
            button.getStyleClass().add("wrong-answer");
        }
        
    }

    @FXML
    private void handleAnswerButton(ActionEvent event) {
        Button button = (Button) event.getSource();
        callback.accept((IntervalType) button.getUserData());
    }

    
}
