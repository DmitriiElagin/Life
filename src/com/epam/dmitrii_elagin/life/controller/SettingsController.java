package com.epam.dmitrii_elagin.life.controller;

import com.epam.dmitrii_elagin.life.Main;
import com.epam.dmitrii_elagin.life.model.Model;

import java.awt.*;


public class SettingsController {

    private Model model;

    public SettingsController(Model model) {
        this.model=model;
    }

    public void onCancel() {
        //Установить размер поля по-умолчанию
        int size=Main.getProperty(Main.FIELD_SIZE);
        model.setFieldSize(new Dimension(size,size));

        model.setLifeSpan(Main.getProperty(Main.LIFE_SPAN));
    }

    public void onOkAction(Dimension size,int lifeSpan) {
        model.setLifeSpan(lifeSpan);
        model.setFieldSize(size);
    }
}
