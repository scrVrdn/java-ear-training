package io.github.scrvrdn.eartraining.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.github.scrvrdn.eartraining.domain.Direction;
import io.github.scrvrdn.eartraining.domain.IntervalType;
import io.github.scrvrdn.eartraining.dto.IntervalPreset;

@Configuration
@ComponentScan(basePackages = "io.github.scrvrdn.eartraining")
public class AppConfig {

    
    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public File json() throws IOException {
        Path jsonPath = Paths.get(System.getProperty("user.home"), ".eartraining", "settings.json");
        Files.createDirectories(jsonPath.getParent());
        File file = new File(jsonPath.toString());
        file.createNewFile();
        return file;
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
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

    @Bean
    public IntervalPreset fallbackPreset() {
        IntervalPreset preset = new IntervalPreset();
        preset.setId(-1);
        preset.setName("default");
        
        Set<IntervalType> intervals = new HashSet<>();
        intervals.add(IntervalType.MINOR_SECOND);
        intervals.add(IntervalType.MAJOR_SECOND);
        intervals.add(IntervalType.MINOR_THIRD);
        intervals.add(IntervalType.MAJOR_THIRD);
        intervals.add(IntervalType.PERFECT_FOURTH);
        intervals.add(IntervalType.TRITONE);
        intervals.add(IntervalType.PERFECT_FIFTH);
        intervals.add(IntervalType.MINOR_SIXTH);
        intervals.add(IntervalType.MAJOR_SIXTH);
        intervals.add(IntervalType.MINOR_SEVENTH);
        intervals.add(IntervalType.MAJOR_SEVENTH);
        intervals.add(IntervalType.OCTAVE);
        intervals.add(IntervalType.MINOR_NINTH);
        intervals.add(IntervalType.MAJOR_NINTH);
        intervals.add(IntervalType.MINOR_TENTH);
        intervals.add(IntervalType.MAJOR_TENTH);

        preset.setIntervals(intervals);
        
        Set<Direction> directions = new HashSet<>();
        directions.add(Direction.ASCENDING);

        preset.setDirections(directions);
  
        preset.setMaxMidiValue(86);
        preset.setMinMidiValue(38);
        preset.setTempoInBPM(120);
        return preset;
    }

    @Bean
    public Supplier<String> presetNameSupplier() {
        return () -> "Untitled - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
    }
}
