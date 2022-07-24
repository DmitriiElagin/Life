package com.epam.dmitrii_elagin.life.simulator;

import com.epam.dmitrii_elagin.life.view.SimulatorListener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SimulatorFunctionalTest {

    private Simulator simulator;

    @Mock
    private SimulatorListener modelListener;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        simulator = new Simulator(new Dimension(30, 30), 100, 2, 4);
        simulator.registerListener(modelListener);
    }

    @Test
    public void testSendEvent() {
        simulator.sendEvent(new SimulatorEvent(SimulatorEvent.SimulatorEventType.FIELD_SIZE_CHANGED));

        verify(modelListener).handleEvent(any());
    }

    @Test
    public void testSetStateShouldSendEvent() {
        simulator.setState(Simulator.State.RUNNING);
        simulator.setState(Simulator.State.STOPPED);

        verify(modelListener, times(2)).handleEvent(any());
    }

    @Test
    public void testSetFieldSizeShouldNotDoAnything() {
        simulator.getColony().add(new Point(0, 0));
        simulator.setFieldSize(new Dimension(30, 30));

        assertFalse(simulator.getColony().isEmpty());

        verify(modelListener, never()).handleEvent(any());
    }

    @Test
    public void testSetFieldSizeShouldClearColony() {
        simulator.getColony().add(new Point(0, 0));
        simulator.setFieldSize(new Dimension(10, 10));

        assertTrue(simulator.getColony().isEmpty());
    }

    @Test
    public void testSetFieldSizeShouldSendEvent() {
        simulator.setFieldSize(new Dimension(10, 10));

        verify(modelListener).handleEvent(any());
    }

}