package com.epam.dmitrii_elagin.life.view;

import com.epam.dmitrii_elagin.life.controller.MainController;
import com.epam.dmitrii_elagin.life.model.Model;
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

    private MainFrame mainFrame;

    @Mock
    private ActionEvent event;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        Model model =new Model();
        mainFrame = spy(new MainFrame(controller, model.getColony()));
    }

    @Test
    public void testClearActionPerformed() {
        when(event.getActionCommand()).thenReturn("Clear");

        mainFrame.actionPerformed(event);

        verify(controller).onClearAction();
    }

    @Test
    public void testSettingsActionPerformed() {
        when(event.getActionCommand()).thenReturn("Settings");

        mainFrame.actionPerformed(event);

        verify(controller).onSettingsAction(anyObject());

    }

    @Test
    public void testStartActionPerformed() {
        when(event.getActionCommand()).thenReturn("Start");

        mainFrame.actionPerformed(event);

        verify(controller).onStartAction();
    }

    @Test
    public void testStopActionPerformed() {
        when(event.getActionCommand()).thenReturn("Stop");

        mainFrame.actionPerformed(event);

        verify(controller).onStopAction();

    }

    @Test
    public void testExitActionPerformed() {
        when(event.getActionCommand()).thenReturn("Exit");

        mainFrame.actionPerformed(event);

        verify(controller).onStopAction();
        verify(mainFrame).dispose();

    }



    @Test
    public void cellClicked() {
        int w = 7, h = 13;

        mainFrame.cellClicked(w,h);

        verify(controller).onCellClick(new Point(w,h));

    }
}