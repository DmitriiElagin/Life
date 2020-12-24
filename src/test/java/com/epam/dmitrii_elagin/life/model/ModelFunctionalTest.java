package com.epam.dmitrii_elagin.life.model;

import com.epam.dmitrii_elagin.life.view.ModelListener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.*;
import java.util.EventListener;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class ModelFunctionalTest {

    private Model model;

    @Mock
    private ModelListener modelListener;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        model = new Model();
        model.registerListener(modelListener);
    }

    @Test
    public void testSendEvent() {
        model.sendEvent(new ModelEvent(ModelEvent.ModelEventType.FIELD_SIZE_CHANGED));

        verify(modelListener).handleEvent(any());
    }

    @Test
    public void testSetStateShouldSendEvent() {
        model.setState(IModel.State.RUNNING);
        model.setState(IModel.State.STOPPED);

        verify(modelListener,times(2)).handleEvent(any());
    }

    @Test
    public void testSetFieldSizeShouldNotDoAnything() {
        model.getColony().add(new Point(0,0));
        model.setFieldSize(new Dimension(30,30));

        assertFalse(model.getColony().isEmpty());

        verify(modelListener, never()).handleEvent(any());
    }

    @Test
    public void testSetFieldSizeShouldClearColony() {
        model.getColony().add(new Point(0,0));
        model.setFieldSize(new Dimension(10,10));

        assertTrue(model.getColony().isEmpty());
    }

    @Test
    public void testSetFieldSizeShouldSendEvent() {
        model.setFieldSize(new Dimension(10,10));

        verify(modelListener).handleEvent(any());
    }

}