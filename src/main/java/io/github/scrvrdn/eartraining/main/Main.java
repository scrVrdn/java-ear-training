package io.github.scrvrdn.eartraining.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.github.scrvrdn.eartraining.config.AppConfig;
import io.github.scrvrdn.eartraining.events.StageReadyEvent;
import io.github.scrvrdn.eartraining.midi.MidiPlayerService;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
    private AnnotationConfigApplicationContext context;

    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public void start(Stage stage)  {
        context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        MidiPlayerService midiSequencer = context.getBean(MidiPlayerService.class);
        if (midiSequencer.isOpen()) {
            midiSequencer.close();
        }
        context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}