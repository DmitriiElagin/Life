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
    private Simulator model;

    private MainController controller;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void setUp() throws Exception {
        controller = new MainController(model);

    }

    @Test
    public void testOnClearAction() {
        controller.onClearAction();

        verify(model).clearField();
    }

    @Test
    public void onSettingsAction() {
    }

    @Test
    public void testOnStartAction() {
        controller.onStartAction();

        verify(model).runSimulation();
    }

    @Test
    public void testOnStopAction() {
        controller.onStopAction();

        verify(model).stopSimulation();
    }

    @Test
    public void testOnCellClick() {
        Point point = new Point(0,0);

        controller.onCellClick(point);

        verify(model).switchCell(point);
    }
}