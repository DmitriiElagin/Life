package com.epam.dmitrii_elagin.life.controller;

import com.epam.dmitrii_elagin.life.simulator.Simulator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.awt.*;

import static org.mockito.Mockito.verify;

public class MainControllerTest {
    @Mock
    private Simulator simulator;

    private MainController controller;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void setUp() throws Exception {
        controller = new MainController(simulator, new SettingsController(simulator));

    }

    @Test
    public void testOnClearAction() {
        controller.onClearAction();

        verify(simulator).clearField();
    }

    @Test
    public void onSettingsAction() {
    }

    @Test
    public void testOnStartAction() {
        controller.onStartAction();

        verify(simulator).runSimulation();
    }

    @Test
    public void testOnStopAction() {
        controller.onStopAction();

        verify(simulator).stopSimulation();
    }

    @Test
    public void testOnCellClick() {
        Point point = new Point(0,0);

        controller.onCellClick(point);

        verify(simulator).switchCell(point);
    }
}