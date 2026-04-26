package io.github.scrvrdn.eartraining.services;

import java.util.Set;

import io.github.scrvrdn.eartraining.model.PresetModel;

public interface PresetService<P> 
{
    boolean containsName(String name);
    int createPreset(P preset);
    int createPresetWithReplacing(P preset);
    void deleteById(int id);
    P findById(int id);
    P findCurrentPreset();
    PresetModel getCurrentPresetModel();
    Set<PresetModel> getPresetModels();
    PresetModel initFallback();
    void renamePreset(int id, String newName);
    int renamePresetWithReplacing(int id, String newName);
    
    
}
