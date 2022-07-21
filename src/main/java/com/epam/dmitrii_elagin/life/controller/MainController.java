package com.epam.dmitrii_elagin.life.controller;

import com.epam.dmitrii_elagin.life.simulator.Simulator;
import com.epam.dmitrii_elagin.life.view.SettingsDialog;

import java.awt.*;

public class MainController {

    private final Simulator simulator;

    public MainController(Simulator simulator) {
        this.simulator = simulator;
    }


    public void onClearAction() {
        simulator.clearField();
    }

    public void onSettingsAction(Frame parent) {


        simulator.stopSimulation();

        SettingsController settingsController =
                new SettingsController(simulator);

        Dimension fieldSize = simulator.getFieldSize();
        SettingsDialog dialog =
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
