package services.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.scrvrdn.containers.RandomDirectionBag;
import io.github.scrvrdn.containers.RandomIntervalBag;
import io.github.scrvrdn.domain.Direction;
import io.github.scrvrdn.domain.IntervalType;
import io.github.scrvrdn.dto.TrackRequest;
import io.github.scrvrdn.services.impl.IntervalService;

@ExtendWith(MockitoExtension.class)
public class IntervalServiceUT {
    @Mock
    private Random rng;

    @Mock
    private RandomDirectionBag directionBag;

    @Mock
    private RandomIntervalBag intervalBag;

    @InjectMocks
    private IntervalService underTest;

    @Test
    public void testThatCreatesRandomRequest() {
        when(directionBag.peekRandom()).thenReturn(Direction.ASCENDING);
        when(intervalBag.peekRandom()).thenReturn(IntervalType.PERFECT_ELEVENTH);
        when(rng.nextInt(anyInt())).thenReturn(60);

        int[] expectedMidiValues = {90, 107};

        TrackRequest result = underTest.createRandomRequest();

        assertEquals(result.dir(), Direction.ASCENDING);
        assertArrayEquals(result.midiValues(), expectedMidiValues);
    }

    @Test
    public void testThatCreatesRequest() {
        int[] expectedAsc = {60, 73};
        int[] expectedDesc = {76, 60};
        int[] expectedSimul = {60, 78};

        TrackRequest resultAsc = underTest.createRequest(Direction.ASCENDING, IntervalType.MINOR_NINTH, 60);
        TrackRequest resultDesc = underTest.createRequest(Direction.DESCENDING, IntervalType.MAJOR_TENTH, 60);
        TrackRequest resultSimul = underTest.createRequest(Direction.SIMULTANEOUS, IntervalType.AUGMENTED_ELEVENTH, 60);

        assertEquals(resultAsc.dir(), Direction.ASCENDING);
        assertArrayEquals(resultAsc.midiValues(), expectedAsc);

        assertEquals(resultDesc.dir(), Direction.DESCENDING);
        assertArrayEquals(resultDesc.midiValues(), expectedDesc);

        assertEquals(resultSimul.dir(), Direction.SIMULTANEOUS);
        assertArrayEquals(resultSimul.midiValues(), expectedSimul);
    }

    @Test
    public void testThatCreateRequestThrowsIllegalArgException() {
        assertThrows(IllegalArgumentException.class, () -> underTest.createRequest(null, IntervalType.MINOR_SECOND, 60));
        assertThrows(IllegalArgumentException.class, () -> underTest.createRequest(Direction.DESCENDING, null, 60));
        assertThrows(IllegalArgumentException.class, () -> underTest.createRequest(Direction.DESCENDING, IntervalType.MINOR_SECOND, -1));
    }

    @Test
    public void testThatThrowsExceptionWhenNoCachedInterval() {
        IntervalType mockInterval = mock(IntervalType.class);
        assertThrows(IllegalStateException.class, () -> underTest.isLastInterval(mockInterval));
    }
}
