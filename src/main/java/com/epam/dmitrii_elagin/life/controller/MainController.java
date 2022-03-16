package com.epam.dmitrii_elagin.life.controller;

import com.epam.dmitrii_elagin.life.model.IModel;
import com.epam.dmitrii_elagin.life.view.SettingsDialog;

import java.awt.*;

public class MainController {

    private final IModel model;

    public MainController(IModel model) {
        this.model = model;
    }


    public void onClearAction() {
        model.clearField();
    }

    public void onSettingsAction(Frame parent) {


        model.stopSimulation();

        SettingsController settingsController =
                new SettingsController(model);

        Dimension fieldSize = model.getFieldSize();
        SettingsDialog dialog =
                new SettingsDialog(parent, settingsController,
                        new Dimension(fieldSize.width, fieldSize.height),
                        model.getLifeSpan(), model.getTightness(),
                        model.getLoneliness());

        dialog.setVisible(true);

    }

    public void onStartAction() {
        model.runSimulation();
    }

    public void onStopAction() {
        model.stopSimulation();
    }

    public void onCellClick(Point p) {
        model.switchCell(p);
    }
}
