package com.epam.dmitrii_elagin.life;

import com.epam.dmitrii_elagin.life.controller.MainController;
import com.epam.dmitrii_elagin.life.controller.SettingsController;
import com.epam.dmitrii_elagin.life.model.IModel;
import com.epam.dmitrii_elagin.life.model.Model;
import com.epam.dmitrii_elagin.life.view.MainFrame;
import com.epam.dmitrii_elagin.life.view.SettingsDialog;

import java.awt.*;
import java.util.ResourceBundle;


public class Main {

    public static final String SETTINGS_WIDTH = "settings_frame.width";
    public static final String SETTINGS_HEIGHT = "settings_frame.height";
    public static final String MAIN_WIDTH = "main_frame.width";
    public static final String MAIN_HEIGHT = "main_frame.height";
    public static final String MIN_SIZE = "field.min_size";
    public static final String MAX_SIZE = "field.max_size";
    public static final String FIELD_SIZE = "field.size";
    public static final String LIFE_SPAN = "field.life_span";
    public static final String TIGHTNESS = "tightness";
    public static final String LONELINESS = "loneliness";


    public static void main(String[] args) {

        IModel model = new Model();

        MainFrame frame = new MainFrame(new MainController(model), model.getColony());

        model.registerListener(frame);

        int size = getProperty(FIELD_SIZE);

        SettingsDialog settingsFrame =
                new SettingsDialog(frame, new SettingsController(model),
                        new Dimension(size, size), getProperty(LIFE_SPAN),
                        getProperty(Main.TIGHTNESS), getProperty(Main.LONELINESS));

        settingsFrame.setVisible(true);

        frame.setVisible(true);

    }


    public static int getProperty(String key) {
        String s = ResourceBundle.getBundle("settings").getString(key);
        return Integer.parseInt(s);
    }

}
