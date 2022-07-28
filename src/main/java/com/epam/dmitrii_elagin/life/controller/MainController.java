package com.epam.dmitrii_elagin.life.controller;

import com.epam.dmitrii_elagin.life.simulator.Simulator;
import com.epam.dmitrii_elagin.life.view.SettingsDialog;

import java.awt.*;

public class MainController {

    private final Simulator simulator;
    private final SettingsController settingsController;

    public MainController(Simulator simulator, SettingsController settingsController) {
        this.simulator = simulator;
        this.settingsController = settingsController;
    }


    public void onClearAction() {
        simulator.clearField();
    }

    public void onSettingsAction(Frame parent) {
        simulator.stopSimulation();

        final Dimension fieldSize = simulator.getFieldSize();

        final SettingsDialog dialog =
                new SettingsDialog(parent, settingsController,
                        new Dimension(fieldSize.width, fieldSize.height),
                        simulator.getLifeSpan(), simulator.getTightness(),
                        simulator.getLoneliness());

        dialog.setVisible(true);

    }

    public void onStartAction() {
        simulator.runSimulation();
    }

    public void onStopAction() {
        simulator.stopSimulation();
    }

    public void onCellClick(Point p) {
        simulator.switchCell(p);
    }
}
