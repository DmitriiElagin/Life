package com.epam.dmitrii_elagin.life.simulator;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.mockito.Mockito.*;

public class ControlThreadTest {

    private Simulator spySimulator;
    private ControlThread controlThread;

    @Before
    public void setUp() {
        spySimulator = spy(new Simulator(new Dimension(30, 30), 5, 2, 4));
        controlThread = new ControlThread(spySimulator);

    }

    @Test
    public void testRunShouldInvokeRandomlyFill() {
        controlThread.run();

        verify(spySimulator).randomlyFill();

    }

    @Test
    public void testRunShouldSwitchState() {
        controlThread.run();

        verify(spySimulator).setState(Simulator.State.RUNNING);
        verify(spySimulator).setState(Simulator.State.STOPPED);
    }

    @Test
    public void testRunShouldSendEvents() {
        controlThread.run();

        verify(spySimulator, atLeast(2)).
                sendEvent(anyObject());
    }

    @Test
    public void testRunShouldInvokeGetLifeSpanFiveTimes() {
        controlThread.run();

        verify(spySimulator, times(5)).getLifeSpan();
    }

    @Test
    public void testRunShouldStopSimulationWhenColonyIsEmpty() {
        spySimulator.getColony().add(new Point(0, 0));

        controlThread.run();

        verify(spySimulator, times(1)).getLifeSpan();
    }


}