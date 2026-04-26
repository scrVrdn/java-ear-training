package io.github.scrvrdn.eartraining.controllers;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import io.github.scrvrdn.eartraining.controls.PresetNameDialog;
import io.github.scrvrdn.eartraining.dto.IntervalPreset;
import io.github.scrvrdn.eartraining.events.TriggerRefreshEvent;
import io.github.scrvrdn.eartraining.model.PresetModel;
import io.github.scrvrdn.eartraining.services.PresetService;
import io.github.scrvrdn.eartraining.services.impl.IntervalSettingsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class PresetController {

    @FXML ComboBox<PresetModel> presetComboBox;
    @FXML Button saveButton;
    @FXML Button saveAsButton;
    @FXML Button discardButton;
    @FXML Button deleteButton;
    @FXML Button editButton;

    @Value("classpath:icons/pen-to-square-solid.png")
    private Resource editIconResource;

    private final ApplicationContext context;
    private final IntervalSettingsService settingsService;
    private final PresetService<IntervalPreset> presetService;
    private ObservableList<PresetModel> presets;

    public PresetController(ApplicationContext context, IntervalSettingsService settingsService, @Qualifier("intervalPresetService") PresetService<IntervalPreset> presetService) {
        this.context = context;
        this.settingsService = settingsService;
        this.presetService = presetService;
    }

    @FXML
    private void initialize() {
        setupEditButton();
        
        presetComboBox.setCellFactory(list -> getCell());
        presetComboBox.setButtonCell(getCell());
        
        presets = FXCollections.observableArrayList(presetService.getPresetModels());
        SortedList<PresetModel> sortedPresets = new SortedList<>(presets, Comparator.comparing(PresetModel::getName, String.CASE_INSENSITIVE_ORDER));
        
        presetComboBox.setItems(sortedPresets);
       
        PresetModel current = presetService.getCurrentPresetModel();
        presetComboBox.setValue(current);
        
        loadPresetWithRefresh();
        
    }

    private void setupEditButton() {
        try {
            Image img = new Image(editIconResource.getInputStream());
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(16);
            imgView.setPreserveRatio(true);
            editButton.setGraphic(imgView);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load edit icon.", e);
        }       
    }

    private ListCell<PresetModel> getCell() {
        return new ListCell<>() {
             @Override
            protected void updateItem(PresetModel preset, boolean empty) {
                super.updateItem(preset, empty);

                textProperty().unbind();
                if (empty || preset == null) {
                    setText(null);
                } else {
                    textProperty().bind(preset.nameProperty());
                }
            }
        };
    }

    @FXML
    private void loadPresetWithRefresh() {
        settingsService.loadPresetById(presetComboBox.getValue().getId());
        context.publishEvent(new TriggerRefreshEvent(this));
    }

    @FXML
    private void delete() {
        PresetModel toDelete = presetComboBox.getValue();
        presetService.deleteById(presetComboBox.getValue().getId());        

        if (presetComboBox.getSelectionModel().getSelectedIndex() < presets.size() - 1) {
            presetComboBox.getSelectionModel().selectNext();
            
        } else {
             presetComboBox.getSelectionModel().selectPrevious();
        }
        
        if (presets.size() == 1) {
            loadFallback();
        }

        presetComboBox.setValue(presetComboBox.getSelectionModel().getSelectedItem());
        presets.remove(toDelete);

        loadPresetWithRefresh();
        
    }

    private void loadFallback() {
        PresetModel fallback = presetService.initFallback();
        presets.add(fallback);
        presetComboBox.setValue(fallback);
    }


    @FXML
    private void save() {
        settingsService.writeToCurrentPreset();
    }

    @FXML
    private void saveAs() {
        Optional<String> result = getName();
        result.ifPresent(this::addNewPreset);
    }

    private Optional<String> getName() {
        Dialog<String> presetDialog = context.getBean(PresetNameDialog.class);
        Optional<String> result = presetDialog.showAndWait();
        return result;
    }

    private void addNewPreset(String name) {
        if (!presetService.containsName(name)) {
            PresetModel preset = settingsService.writeToNewPreset(name);
            presets.add(preset);
            presetComboBox.setValue(preset);

        } else if (confirmReplacement(name)) {
            Optional<PresetModel> preset = presets.stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst();
            
            preset.ifPresent(p -> {
                settingsService.writeToPresetById(p.getId());
                presetComboBox.setValue(p);
            });
        }
       
    }

    @FXML
    private void edit() {
        String currentName = presetComboBox.getValue().getName();
        Optional<String> result = getNameForEdit()
                                        .filter(e -> !e.equals(currentName));
        result.ifPresent(this::renamePreset);
    }

    private Optional<String> getNameForEdit() {
        Supplier<String> s = () -> presetComboBox.getValue().getName();
        Dialog<String> presetDialog = context.getBean(PresetNameDialog.class, s);
        Optional<String> result = presetDialog.showAndWait();
        return result;
    }

    private void renamePreset(String newName) {
        if (!presetService.containsName(newName)) {
            presetService.renamePreset(presetComboBox.getValue().getId(), newName);
            presetComboBox.getValue().setName(newName);

        } else if (confirmReplacement(newName)) {
            int replacedId = presetService.renamePresetWithReplacing(presetComboBox.getValue().getId(), newName);
            Optional<PresetModel> replacedPreset = presets.stream()
                    .filter(p -> p.getId() == replacedId)
                    .findFirst();

            replacedPreset.ifPresent(presets::remove);
            presetComboBox.getValue().setName(newName);
        }
    }

    private boolean confirmReplacement(String name) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("A preset with the name \"" + name + "\" already exists. Do you want to replace it?");
        Optional<ButtonType> buttonPressed = alert.showAndWait();
        return buttonPressed.isPresent() && buttonPressed.get() == ButtonType.OK;
    }

}
