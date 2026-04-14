package io.github.scrvrdn.eartraining.config;

import java.util.Random;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "io.github.scrvrdn.eartraining")
public class AppConfig {


    @Bean
    public Random random() {
        return new Random();
    }

    @Bean(destroyMethod = "close")
    public Synthesizer synthesizer() throws MidiUnavailableException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        return synth;
    }

    @Bean
    public Sequence sequence() throws InvalidMidiDataException {
        return new Sequence(Sequence.PPQ, 1);
    }

    @Bean(destroyMethod = "close")
    public Sequencer sequencer(Synthesizer synth, Sequence sequence) throws MidiUnavailableException, InvalidMidiDataException {
        Sequencer sequencer = MidiSystem.getSequencer(false);
        sequencer.open();
        sequencer.getTransmitter().setReceiver(synth.getReceiver());
        sequencer.setSequence(sequence);
        
        return sequencer;
    }
}
