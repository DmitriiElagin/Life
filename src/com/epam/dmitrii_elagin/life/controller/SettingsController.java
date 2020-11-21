package com.epam.dmitrii_elagin.life.controller;


import com.epam.dmitrii_elagin.life.model.IModel;

import java.awt.*;


public class SettingsController {

    private final IModel model;

    public SettingsController(IModel model) {
        this.model = model;
    }

    public void onCancel() {

    }

    public void onOkAction(Dimension size, int lifeSpan) {
        model.setLifeSpan(lifeSpan);
        model.setFieldSize(size);
    }
}
