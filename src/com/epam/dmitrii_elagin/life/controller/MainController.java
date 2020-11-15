package com.epam.dmitrii_elagin.life.controller;

import com.epam.dmitrii_elagin.life.model.Model;
import com.epam.dmitrii_elagin.life.view.MainFrame;
import com.epam.dmitrii_elagin.life.view.SettingsDialog;

import java.awt.*;

public class MainController {

    private Model model;

    public MainController(Model model){
        this.model=model;
    }
    

    public void onClearAction() {
        model.clearField();
    }

    public void onSettingsAction(Frame parent) {


        model.stopSimulation();

        SettingsController settingsController=
                new SettingsController(model);

        Dimension fieldSize = model.getFieldSize();
        SettingsDialog dialog =
                new SettingsDialog(parent,settingsController,
                        fieldSize.height, fieldSize.width,
                        model.getLifeSpan());

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
