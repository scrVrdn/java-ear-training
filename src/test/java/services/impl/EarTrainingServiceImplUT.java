package services.impl;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.scrvrdn.domain.Direction;
import io.github.scrvrdn.dto.TrackRequest;
import io.github.scrvrdn.midi.MidiSequencer;
import io.github.scrvrdn.midi.impl.MidiTrackGenerator;
import io.github.scrvrdn.services.impl.EarTrainingServiceImpl;
import io.github.scrvrdn.services.impl.IntervalService;

@ExtendWith(MockitoExtension.class)
public class EarTrainingServiceImplUT {
    @Mock
    private IntervalService intervalService;

    @Mock
    private MidiSequencer midiSequencer;

    @InjectMocks
    private EarTrainingServiceImpl underTest;

    @Test
    public void testThatCreatesNewIntervalAndPlaysIt() throws Exception {
        TrackRequest request = new TrackRequest(Direction.ASCENDING, new int[]{60, 65});
        List<MidiEvent> events = new ArrayList<>();
        when(intervalService.createRandomRequest()).thenReturn(request);

        try (MockedStatic<MidiTrackGenerator> mockGenerator = mockStatic(MidiTrackGenerator.class)) {
            mockGenerator.when(() -> MidiTrackGenerator.build(request)).thenReturn(events);

            underTest.playNewInterval();

            verify(intervalService).createRandomRequest();
            mockGenerator.verify(() -> MidiTrackGenerator.build(request));
            verify(midiSequencer).addTrack(events);
            verify(midiSequencer).play();

        }
        
    }
    

}
