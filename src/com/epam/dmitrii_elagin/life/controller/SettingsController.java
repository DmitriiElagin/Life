package com.epam.dmitrii_elagin.life.controller;

import com.epam.dmitrii_elagin.life.model.Model;

import java.awt.*;

public class SettingsController {

    private Model model;

    public SettingsController(Model model) {
        this.model=model;
    }

    public void onExit() {

    }

    public void onOkAction(Dimension size,int lifeSpan) {
        model.setLifeSpan(lifeSpan);
        model.setFieldSize(size);
    }
}
