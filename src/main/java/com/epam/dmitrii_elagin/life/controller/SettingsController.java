package com.epam.dmitrii_elagin.life.controller;


import com.epam.dmitrii_elagin.life.Main;
import com.epam.dmitrii_elagin.life.view.SettingsDialog;

import java.awt.*;


public class SettingsController {

    private final IModel model;

    public SettingsController(IModel model) {
        this.model = model;
    }

    public void onCancelAction(SettingsDialog dialog) {
        dialog.dispose();
    }

    public void onOkAction(Dimension size, int lifeSpan, int tightness, int loneliness, SettingsDialog dialog) {
        int minSize = Main.getProperty(Main.MIN_SIZE);

        if(size.width<minSize || size.height<minSize) {
            dialog.showError("Высота или ширина < " + minSize);
        }
        else {
            model.setFieldSize(size);
            model.setLifeSpan(lifeSpan);
            model.setTightness(tightness);
            model.setLoneliness(loneliness);
            dialog.dispose();
        }
    }
}
