package io.github.scrvrdn.eartraining.persistence.Impl;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.scrvrdn.eartraining.data.PresetIdGenerator;
import io.github.scrvrdn.eartraining.data.SettingsFile;
import io.github.scrvrdn.eartraining.dto.IntervalPreset;
import io.github.scrvrdn.eartraining.exceptions.DuplicateNameException;
import io.github.scrvrdn.eartraining.persistence.PresetDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Repository("intervalPresets")
public class IntervalPresetDao implements PresetDao<IntervalPreset> {

    private final ObjectMapper mapper;
    private final File file;
    private SettingsFile settings;
    private final Map<String, Integer> presetNamesToId = new HashMap<>();
    private final IntervalPreset fallbackPreset;

    public IntervalPresetDao(ObjectMapper mapper, File file, IntervalPreset fallbackPreset) {
        this.mapper = mapper;
        this.file = file;
        this.fallbackPreset = fallbackPreset;
    }

    @Override
    public IntervalPreset findById(int id) {
        Map<Integer, IntervalPreset> presets = settings.getIntervalPresets();
        if (!presets.containsKey(id)) {
            throw new NoSuchElementException("A preset with the id " + id + " does not exist.");
        }

        settings.setCurrentIntervalPresetId(id);
        return presets.get(id);
    }

    @Override
    public IntervalPreset findCurrentPreset() {
        var presets = settings.getIntervalPresets();
        Optional<Integer> id = settings.getCurrentIdAsOptional();
        return presets.get(id.orElseGet(this::loadFallback));
    }

    private int loadFallback() {
        IntervalPreset current = fallbackPreset.deepCopy();
        int newId = addPreset(current);
        return newId;
    }

    @Override
    public IntervalPreset initFallback() {
        IntervalPreset fallback = fallbackPreset.deepCopy();
        addPreset(fallback);
        return fallback;
    }

    @Override
    public Collection<IntervalPreset> findAll() {
        return settings.getIntervalPresets().values();        
    }

    @Override
    public int addPreset(IntervalPreset preset) {
        Map<Integer, IntervalPreset> presets = settings.getIntervalPresets();
        int id = settings.getIdGenerator().next();
        preset.setId(id);
        presets.put(id, preset);
        presetNamesToId.put(preset.getName(), id);
        settings.setCurrentIntervalPresetId(id);
        return id;
    }

    @Override
    public boolean contains(String name) {
        return presetNamesToId.containsKey(name);
    }

    private boolean containsId(int id) {
        return settings.getIntervalPresets().containsKey(id);
    }

    @Override
    public void deleteById(int id) {
        if (!containsId(id)) {
            throw new NoSuchElementException("A \"" + IntervalPreset.class + "\" with id \"" + id + "\" does not exist.");
        }
        
        Map<Integer, IntervalPreset> presets = settings.getIntervalPresets();
        String name = presets.get(id).getName();
        presetNamesToId.remove(name);
        presets.remove(id);
        settings.setCurrentIntervalPresetId(null);
    }

    @Override
    public int deleteByName(String name) {
        if (!contains(name)) {
            throw new NoSuchElementException("A \"" + IntervalPreset.class + "\" with the name \"" + name + "\" does not exist.");
        }

        int id = presetNamesToId.get(name);
        presetNamesToId.remove(name);
        settings.getIntervalPresets().remove(id);
        return id;
    }

    @Override
    public void rename(int id, String newName) {
        if (!containsId(id)) {
            throw new NoSuchElementException("A \"" + IntervalPreset.class + "\" with id \"" + id + "\" does not exist.");
        }

        if (contains(newName)) {
            throw new DuplicateNameException(newName);
        }

        Map<Integer, IntervalPreset> presets = settings.getIntervalPresets();
        IntervalPreset pre = presets.get(id);
        presetNamesToId.remove(pre.getName());
        presetNamesToId.put(newName, id);
        pre.setName(newName);
    }
   
    @PostConstruct
    private void load() {
        if (!file.exists() || file.length() == 0) {
            IntervalPreset defaultPreset = fallbackPreset.deepCopy();

            settings = new SettingsFile(new HashMap<>(), defaultPreset.getId(), new PresetIdGenerator());

            settings.getIntervalPresets().put(defaultPreset.getId(), defaultPreset);
            presetNamesToId.put(defaultPreset.getName(), defaultPreset.getId());
        } else {
            try {
                settings = mapper.readValue(file, new TypeReference<SettingsFile>() {});
                settings.getIntervalPresets().values().forEach(p -> presetNamesToId.put(p.getName(), p.getId()));
            } catch (Exception e) {
                throw new RuntimeException("Failed to load presets.", e);
            }
        }
    }

    @PreDestroy
    private void save() throws Exception {
        mapper.writeValue(file, settings);
    }
    
}
