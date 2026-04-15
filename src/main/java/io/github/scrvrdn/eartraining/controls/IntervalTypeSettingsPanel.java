package io.github.scrvrdn.eartraining.controls;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import io.github.scrvrdn.eartraining.domain.IntervalType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

@Component
public class IntervalTypeSettingsPanel {

    @FXML private CheckBox unison;
    @FXML private CheckBox minSecond;
    @FXML private CheckBox majSecond;
    @FXML private CheckBox minThird;
    @FXML private CheckBox majThird;
    @FXML private CheckBox fourth;
    @FXML private CheckBox tritone;
    @FXML private CheckBox fifth;
    @FXML private CheckBox minSixth;
    @FXML private CheckBox majSixth;
    @FXML private CheckBox minSeventh;
    @FXML private CheckBox majSeventh;
    @FXML private CheckBox octave;
    @FXML private CheckBox minNinth;
    @FXML private CheckBox majNinth;
    @FXML private CheckBox minTenth;
    @FXML private CheckBox majTenth;
    @FXML private CheckBox eleventh;
    @FXML private CheckBox augEleventh;
    @FXML private CheckBox twelfth;

    @FXML private CheckBox deselectAll;
    @FXML private CheckBox selectAll;

    private final Map<IntervalType, CheckBox> intervalMap = new HashMap<>();
    private Consumer<IntervalType> addIntervalCallback;
    private Consumer<IntervalType> removeIntervalCallback;

    @FXML
    private void initialize() {
        addIntervalTypeToCheckboxes();
        initIntervalMap();
    }

    private void addIntervalTypeToCheckboxes() {
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

    public void setAddIntervalCallback(Consumer<IntervalType> callback) {
        this.addIntervalCallback = callback;
    }

    public void setRemoveIntervalCallback(Consumer<IntervalType> callback) {
        this.removeIntervalCallback = callback;
    }

    public void toggleCheckbox(IntervalType interval) {
        CheckBox cb = intervalMap.get(interval);
        cb.setSelected(!cb.isSelected());
    }

    @FXML
    private void setIntervalType(ActionEvent event) {
        if (deselectAll.isSelected()) {
            deselectAll.setSelected(false);
        }

        if (selectAll.isSelected()) {
            selectAll.setSelected(false);
        }

        CheckBox cb = (CheckBox) event.getSource();
        if (cb.isSelected()) {
            addIntervalCallback.accept((IntervalType) cb.getUserData());
        } else {
            removeIntervalCallback.accept((IntervalType) cb.getUserData());
        }
    }

    @FXML
    private void deselectAll() {
        for (IntervalType interval : intervalMap.keySet()) {
            removeIntervalCallback.accept(interval);
        }

        for (CheckBox cb : intervalMap.values()) {
            cb.setSelected(false);
        }
    }

    @FXML
    private void selectAll() {
        for (IntervalType interval : intervalMap.keySet()) {
            addIntervalCallback.accept(interval);
        }

        for (CheckBox cb : intervalMap.values()) {
            cb.setSelected(true);
        }
    }
}
