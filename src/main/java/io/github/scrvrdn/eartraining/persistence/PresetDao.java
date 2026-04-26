package io.github.scrvrdn.eartraining.persistence;

import java.util.Collection;

public interface PresetDao<T> {

    int addPreset(T preset);
    T findById(int id);
    T findCurrentPreset();
    Collection<T> findAll();
    T initFallback();
    void deleteById(int id);
    int deleteByName(String name);
    void rename(int id, String newName);
    boolean contains(String name);
    
}
