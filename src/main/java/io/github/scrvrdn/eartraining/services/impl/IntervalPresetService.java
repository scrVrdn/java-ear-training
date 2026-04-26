package io.github.scrvrdn.eartraining.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.scrvrdn.eartraining.dto.IntervalPreset;
import io.github.scrvrdn.eartraining.exceptions.DuplicateNameException;
import io.github.scrvrdn.eartraining.model.PresetModel;
import io.github.scrvrdn.eartraining.persistence.PresetDao;
import io.github.scrvrdn.eartraining.services.PresetService;

@Service("intervalPresetService")
public class IntervalPresetService implements PresetService<IntervalPreset> {

    private PresetDao<IntervalPreset> presetDao;

    public IntervalPresetService(@Qualifier("intervalPresets") PresetDao<IntervalPreset> presetDao) {
        this.presetDao = presetDao;
    }

    @Override
    public boolean containsName(String name) {
        return presetDao.contains(name);
    }

    @Override
    public int createPreset(IntervalPreset preset) {
        if (presetDao.contains(preset.getName())) {
            throw new DuplicateNameException(preset.getName());
        }

        int id = presetDao.addPreset(preset);
        return id;
    }

    @Override
    public int createPresetWithReplacing(IntervalPreset preset) {
        if (presetDao.contains(preset.getName())) {
            presetDao.deleteByName(preset.getName());
        }

        int id = presetDao.addPreset(preset);
        return id;
    }

    @Override
    public IntervalPreset findById(int id) {
        return presetDao.findById(id);
    }

    @Override
    public IntervalPreset findCurrentPreset() {
        return presetDao.findCurrentPreset();
    }

    @Override
    public void renamePreset(int id, String newName) {
        presetDao.rename(id, newName);
    }

    @Override
    public int renamePresetWithReplacing(int id, String newName) {
        int deletedId = presetDao.deleteByName(newName);
        presetDao.rename(id, newName);
        return deletedId;       
    }

    @Override
    public void deleteById(int id) {
        presetDao.deleteById(id);
    }

    @Override
    public Set<PresetModel> getPresetModels() {
        Set<PresetModel> set = new HashSet<>();
        for (IntervalPreset preset : presetDao.findAll()) {
            PresetModel model = new PresetModel(preset.getId(), preset.getName());
            set.add(model);
        }

        return set;
    }

    @Override
    public PresetModel getCurrentPresetModel() {
        IntervalPreset p = findCurrentPreset();
        return new PresetModel(p.getId(), p.getName());
    }

    @Override
    public PresetModel initFallback() {
        IntervalPreset p = presetDao.initFallback();
        return new PresetModel(p.getId(), p.getName());
    }

}
