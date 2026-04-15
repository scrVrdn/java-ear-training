package io.github.scrvrdn.eartraining.controls;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import io.github.scrvrdn.eartraining.domain.Direction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

@Component
public class DirectionsPanel {

    @FXML private CheckBox ascendingCb;
    @FXML private CheckBox descendingCb;
    @FXML private CheckBox simultaneousCb;

    private final Map<Direction, CheckBox> directionMap = new HashMap<>();

    private Consumer<Direction> addDirectionCallback;
    private Consumer<Direction> removeDirectionCallback;

    @FXML
    private void initialize() {
        ascendingCb.setUserData(Direction.ASCENDING);
        directionMap.put(Direction.ASCENDING, ascendingCb);

        descendingCb.setUserData(Direction.DESCENDING);
        directionMap.put(Direction.DESCENDING, descendingCb);

        simultaneousCb.setUserData(Direction.SIMULTANEOUS);
        directionMap.put(Direction.SIMULTANEOUS, simultaneousCb);
    }

    public void setAddDirectionCallback(Consumer<Direction> callback) {
        addDirectionCallback = callback;
    }

    public void setRemoveDirectionCallback(Consumer<Direction> callback) {
        removeDirectionCallback = callback;
    }

    public void toggleCheckBox(Direction dir) {
        CheckBox cb = directionMap.get(dir);
        cb.setSelected(!cb.isSelected());
    }

    @FXML
    private void setDirection(ActionEvent event) {
        CheckBox cb = (CheckBox) event.getSource();
        if (cb.isSelected()) {
            addDirectionCallback.accept((Direction) cb.getUserData());
        } else {
            removeDirectionCallback.accept((Direction) cb.getUserData());
        }
    }

}
