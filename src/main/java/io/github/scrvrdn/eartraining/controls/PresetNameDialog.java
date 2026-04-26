package io.github.scrvrdn.eartraining.controls;


import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Component
@Scope("prototype")
public class PresetNameDialog extends Dialog<String> {

    @Value("classpath:fxml/presetnamedialog.fxml")
    private Resource fxml;

    @FXML private TextField nameField;
    private final Supplier<String> presetNameSupplier;
    private String name;

    public PresetNameDialog(Supplier<String> presetNameSupplier) throws IOException {
        this.presetNameSupplier = presetNameSupplier;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/presetnamedialog.fxml"));
        loader.setController(this);
        loader.setRoot(getDialogPane());
        loader.load();
    }

    public void initialize() {
        buildUI();
        setResultConverter((button) -> button == ButtonType.OK ? name : null);
    }

    private void buildUI() {
        nameField.setText(presetNameSupplier.get());
        nameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                Platform.runLater(nameField::selectAll);
            }
        });

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);
        button.addEventFilter(ActionEvent.ACTION, this::handleOK);
    }

    private void handleOK(ActionEvent event) {
        if (nameField.getText().isBlank()) {
            event.consume();
            showWarning();

        } else {
            name = nameField.getText();
        }
    }

    private void showWarning() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText("The preset name must contain at least one character");
        alert.showAndWait();
    }
}
