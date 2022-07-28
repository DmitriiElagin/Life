package com.epam.dmitrii_elagin.life.controller;


import com.epam.dmitrii_elagin.life.Main;
import com.epam.dmitrii_elagin.life.simulator.Simulator;
import com.epam.dmitrii_elagin.life.view.SettingsDialog;

import java.awt.*;


public class SettingsController {
    private final Simulator simulator;

    public SettingsController(Simulator simulator) {
        this.simulator = simulator;
    }

    public void onCancelAction(SettingsDialog dialog) {
        dialog.dispose();
    }

    public void onOkAction(Dimension size, int lifeSpan, int tightness, int loneliness, SettingsDialog dialog) {
        final int minSize = Main.getProperty(Main.MIN_SIZE);

        if (size.width < minSize || size.height < minSize) {
            dialog.showError("Width or height < " + minSize);
        }
        else {
            simulator.setFieldSize(size);
            simulator.setLifeSpan(lifeSpan);
            simulator.setTightness(tightness);
            simulator.setLoneliness(loneliness);
            dialog.dispose();
        }
    }
}
