package io.github.scrvrdn.eartraining.persistence.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.scrvrdn.eartraining.dto.IntervalPreset;
import io.github.scrvrdn.eartraining.persistence.Impl.IntervalPresetDao;

@ExtendWith(MockitoExtension.class)
public class IntervalPresetDaoUT {

    @Autowired
    private IntervalPresetDao underTest;

    @Test
    public void testThatCanMapPresetToJson() {
        IntervalPreset testPreset = new IntervalPreset();
        testPreset.setName("myPreset");
        testPreset.setMaxMidiValue(100);
        testPreset.setMinMidiValue(40);
        testPreset.setTempoInBPM(240);
        

      //  String result = IntervalPresetDao.write();
    }
}
