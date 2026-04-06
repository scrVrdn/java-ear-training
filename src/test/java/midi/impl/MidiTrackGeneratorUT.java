package midi.impl;



import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import org.junit.jupiter.api.Test;

import io.github.scrvrdn.domain.Direction;
import io.github.scrvrdn.dto.TrackRequest;
import io.github.scrvrdn.midi.impl.MidiTrackGenerator;

public class MidiTrackGeneratorUT {
    


    @Test
    public void testThatCreatesTrackFromRequest() throws Exception {
        TrackRequest ascRequest = new TrackRequest(Direction.ASCENDING, new int[]{60, 64});
        TrackRequest descRequest = new TrackRequest(Direction.DESCENDING, new int[]{64, 60});
        TrackRequest simulRequest = new TrackRequest(Direction.SIMULTANEOUS, new int[]{60, 64});

        List<MidiEvent> ascExpected = getExpectedAscendingResult();        
        List<MidiEvent> descExpected = getExpectedDescendingResult();
        List<MidiEvent> simulExpected = getExpectedSimultaneousResult();

        List<MidiEvent> ascResult = MidiTrackGenerator.build(ascRequest);
        List<MidiEvent> descResult = MidiTrackGenerator.build(descRequest);
        List<MidiEvent> simulResult = MidiTrackGenerator.build(simulRequest);

        assertMidiEventsEqual(ascResult, ascExpected);
        assertMidiEventsEqual(descResult, descExpected);
        assertMidiEventsEqual(simulResult, simulExpected);
      
    }

    private List<MidiEvent> getExpectedAscendingResult() throws Exception {
        int quarterNote = 1;
        MidiEvent event1 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 60, MidiTrackGenerator.getVelocity()), 0);
        MidiEvent event2 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 60, 0), quarterNote);
        MidiEvent event3 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 64, MidiTrackGenerator.getVelocity()), quarterNote);
        MidiEvent event4 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 64, 0), 2 * quarterNote);

        return List.of(event1, event2, event3, event4);
    }

    private List<MidiEvent> getExpectedDescendingResult() throws Exception {
        int quarterNote = 1;
        MidiEvent event1 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 64, MidiTrackGenerator.getVelocity()), 0);
        MidiEvent event2 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 64, 0), quarterNote);
        MidiEvent event3 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 60, MidiTrackGenerator.getVelocity()), quarterNote);
        MidiEvent event4 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 60, 0), 2 * quarterNote);
        
        return List.of(event1, event2, event3, event4);
    }

    private List<MidiEvent> getExpectedSimultaneousResult() throws Exception {
        int halfNote = 2;
        MidiEvent event1 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 60, MidiTrackGenerator.getVelocity()), 0);
        MidiEvent event2 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 60, 0), halfNote);
        MidiEvent event3 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 64, MidiTrackGenerator.getVelocity()), 0);
        MidiEvent event4 = new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 64, 0), halfNote);
        
        return List.of(event1, event2, event3, event4);
    }

    private void assertMidiEventsEqual(List<MidiEvent> result, List<MidiEvent> expected) {
        assertEquals(result.size(), expected.size());
          for (int i = 0; i < expected.size(); i++) {
            MidiEvent a = result.get(i);
            MidiEvent b = expected.get(i);
            assertEquals(a.getTick(), b.getTick());
            assertArrayEquals(a.getMessage().getMessage(), b.getMessage().getMessage());
        }
    }
}
