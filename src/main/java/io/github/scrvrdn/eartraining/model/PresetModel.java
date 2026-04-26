package io.github.scrvrdn.eartraining.model;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PresetModel implements Comparable<PresetModel> {

    private int id;
    private final StringProperty name = new SimpleStringProperty();

    public PresetModel(int id, String name) {
        this.id = id;
        this.name.setValue(name);;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    @Override
    public int compareTo(PresetModel that) {
        return this.name.get().compareTo(that.name.get());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (this == o) {
            return true;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        PresetModel that = (PresetModel) o;
        return this.id == that.id && this.name.get().equals(that.name.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name.get());
    }

    @Override
    public String toString() {
        return name.get();
    }
    
}
