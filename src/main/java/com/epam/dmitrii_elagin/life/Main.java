package com.epam.dmitrii_elagin.life;

import com.epam.dmitrii_elagin.life.controller.MainController;
import com.epam.dmitrii_elagin.life.controller.SettingsController;
import com.epam.dmitrii_elagin.life.simulator.Simulator;
import com.epam.dmitrii_elagin.life.view.MainFrame;

import java.awt.*;
import java.util.ResourceBundle;


public class Main {
    public static final String MIN_SIZE = "field.min_size";
    public static final String MAX_SIZE = "field.max_size";
    public static final String FIELD_SIZE = "field.size";
    public static final String LIFE_SPAN = "field.life_span";
    public static final String TIGHTNESS = "tightness";
    public static final String LONELINESS = "loneliness";


    public static void main(String[] args) {
        final int size = getProperty(FIELD_SIZE);

        final Simulator simulator = new Simulator(
                new Dimension(size, size),
                getProperty(LIFE_SPAN),
                getProperty(LONELINESS),
                getProperty(TIGHTNESS));

        final MainFrame frame = new MainFrame(new MainController(simulator, new SettingsController(simulator)),
                simulator.getBacteriaCollection(), new Dimension(size, size));

        simulator.registerListener(frame);

        frame.setVisible(true);

    }

    public static int getProperty(String key) {
        String s = ResourceBundle.getBundle("settings").getString(key);
        return Integer.parseInt(s);
    }

}
