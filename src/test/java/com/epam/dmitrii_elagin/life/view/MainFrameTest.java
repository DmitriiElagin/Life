package com.epam.dmitrii_elagin.life.view;

import com.epam.dmitrii_elagin.life.controller.MainController;
import com.epam.dmitrii_elagin.life.simulator.Simulator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.awt.*;
import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

public class MainFrameTest {
    @Mock
    private MainController controller;

    private MainFrame spyFrame;

    @Mock
    private ActionEvent event;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        final Simulator simulator = new Simulator(new Dimension(30, 30), 100, 2, 4);
        spyFrame = spy(new MainFrame(controller, simulator.getBacteriaCollection(), new Dimension(30, 30)));

    }

    @Test
    public void testClearActionPerformed() {
        when(event.getActionCommand()).thenReturn("Clear");

        spyFrame.actionPerformed(event);

        verify(controller).onClearAction();
    }

    @Test
    public void testSettingsActionPerformed() {
        when(event.getActionCommand()).thenReturn("Settings");

        spyFrame.actionPerformed(event);

        verify(controller).onSettingsAction(anyObject());

    }

    @Test
    public void testStartActionPerformed() {
        when(event.getActionCommand()).thenReturn("Start");

        spyFrame.actionPerformed(event);

        verify(controller).onStartAction();
    }

    @Test
    public void testStopActionPerformed() {
        when(event.getActionCommand()).thenReturn("Stop");

        spyFrame.actionPerformed(event);

        verify(controller).onStopAction();

    }

    @Test
    public void testExitActionPerformed() {
        when(event.getActionCommand()).thenReturn("Exit");

        spyFrame.actionPerformed(event);

        verify(controller).onStopAction();
        verify(spyFrame).dispose();

    }



    @Test
    public void cellClicked() {
        final int w = 7, h = 13;

        spyFrame.cellClicked(w,h);

        verify(controller).onCellClick(new Point(w,h));

    }
}