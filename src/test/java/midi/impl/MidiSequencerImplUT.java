package midi.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.scrvrdn.midi.impl.MidiSequencerImpl;

@ExtendWith(MockitoExtension.class)
public class MidiSequencerImplUT {

    @Mock
    private Sequencer sequencer;

    @InjectMocks
    private MidiSequencerImpl underTest;


    @Test
    public void testThatPlaysTrack() throws Exception {
        when(sequencer.isOpen()).thenReturn(true);
        when(sequencer.isRunning()).thenReturn(false);

        underTest.play();
        verify(sequencer).setTickPosition(0);
        verify(sequencer).start();
    }

    @Test
    public void testThatPlayOpensSequencer() throws Exception {
        when(sequencer.isOpen()).thenReturn(false);
        when(sequencer.isRunning()).thenReturn(false);

        underTest.play();
        
        verify(sequencer).open();
    }

    @Test
    public void testThatStopsSequencerBeforePlaying() throws Exception {
        when(sequencer.isOpen()).thenReturn(true);
        when(sequencer.isRunning()).thenReturn(true);

        underTest.play();

        verify(sequencer).stop();
    }


    @Test
    public void testThatThrowsExceptionWhenAddTrackArgNullOrEmpty() {
        List<MidiEvent> events = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> underTest.addTrack(null));
        assertThrows(IllegalArgumentException.class, () -> underTest.addTrack(events));
    }

    @Test
    public void testThatAddsMidiEventsToTrack() {
        Sequence mockSequence = mock(Sequence.class);
        Track mockTrack = mock(Track.class);

        when(sequencer.getSequence()).thenReturn(mockSequence);
        when(mockSequence.createTrack()).thenReturn(mockTrack);

        List<MidiEvent> events = List.of(
            new MidiEvent(null, 0),
            new MidiEvent(null, 0),
            new MidiEvent(null, 0),
            new MidiEvent(null, 0)
            );

        underTest.addTrack(events);

        verify(mockTrack).add(events.get(0));
        verify(mockTrack).add(events.get(1));
        verify(mockTrack).add(events.get(2));
        verify(mockTrack).add(events.get(3));
    }

    @Test
    public void testThatThrowsExceptionWhenNoSequencerFound() {
        assertThrows(MidiUnavailableException.class, () -> underTest.open());
    }
}
