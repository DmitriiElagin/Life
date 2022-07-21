package com.epam.dmitrii_elagin.life.controller;

import com.epam.dmitrii_elagin.life.view.SettingsDialog;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.awt.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SettingsControllerTest {

    @Mock
    private IModel model;

    private SettingsController controller;

    @Mock
    private SettingsDialog dialog;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void setUp() throws Exception {
        controller = new SettingsController(model);
    }

    @Test
    public void testOnCancelAction() {
        controller.onCancelAction(dialog);
        verify(dialog).dispose();
    }

    @Test
    public void functionalTestOnOkAction() {
        Dimension dimension = new Dimension(5,2);

        controller.onOkAction(dimension,10,7,3,dialog);

        verify(model).setFieldSize(dimension);
        verify(model).setLifeSpan(10);
        verify(model).setTightness(7);
        verify(model).setLoneliness(3);
        verify(dialog).dispose();
    }

    @Test
    public void testOnOkActionShouldInvokeShowError(){
        Dimension dimension = new Dimension(5,1);

        controller.onOkAction(dimension,10,7,3,dialog);

        dimension.setSize(0,3);

        controller.onOkAction(dimension,10,7,3,dialog);

        verify(dialog, times(2)).showError(anyString());
    }
}