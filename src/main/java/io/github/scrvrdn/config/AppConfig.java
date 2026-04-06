package io.github.scrvrdn.config;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"midi"})
public class AppConfig {

    @Bean
    public Sequence sequence() throws Exception {
        return new Sequence(Sequence.PPQ, 1);
    }

    @Bean
    public Sequencer sequencer() throws Exception {
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.setSequence(sequence());
        return sequencer;
    }
}
