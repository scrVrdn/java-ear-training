package io.github.scrvrdn.eartraining.view;

import io.github.scrvrdn.eartraining.view.scenetypes.SceneType;
import javafx.stage.Stage;

public interface SceneManager {

    void showScene(SceneType scene);
    void setStage(Stage stage);
}
