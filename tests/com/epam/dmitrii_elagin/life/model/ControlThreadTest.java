package com.epam.dmitrii_elagin.life.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.mockito.Mockito.*;

public class ControlThreadTest {

    private Model spyModel;
    private ControlThread controlThread;

    @Before
    public void setUp() {
        spyModel = spy(new Model());
        spyModel.setLifeSpan(5);
        controlThread = new ControlThread(spyModel);

    }

    @Test
    public void testRunShouldCallRandomlyFill() {
        controlThread.run();

        verify(spyModel).randomlyFill();

    }

    @Test
    public void testRunShouldSwitchState() {
        controlThread.run();

        verify(spyModel).setState(IModel.State.RUNNING);
        verify(spyModel).setState(IModel.State.STOPPED);
    }

    @Test
    public void testRunShouldSendEvents() {
        controlThread.run();

        verify(spyModel, atLeast(2)).
                sendEvent(anyObject());
    }

    @Test
    public void testRunShouldCallGetLifeSpanFiveTimes() {
        controlThread.run();

        verify(spyModel, times(5)).getLifeSpan();
    }

    @Test
    public void testRunShouldStopSimulationWhenColonyIsEmpty() {
        spyModel.getColony().add(new Point(0, 0));

        controlThread.run();

        verify(spyModel, times(1)).getLifeSpan();
    }


}