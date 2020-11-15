package com.epam.dmitrii_elagin.life;

import com.epam.dmitrii_elagin.life.controller.MainController;
import com.epam.dmitrii_elagin.life.controller.SettingsController;
import com.epam.dmitrii_elagin.life.model.Model;
import com.epam.dmitrii_elagin.life.model.Repository;
import com.epam.dmitrii_elagin.life.view.MainFrame;
import com.epam.dmitrii_elagin.life.view.SettingsDialog;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Model model=new Repository();


        MainFrame frame=new MainFrame(new MainController(model));

        model.registerListener(frame);

        SettingsDialog settingsFrame=
                new SettingsDialog(frame, new SettingsController(model),
                        Model.WIDTH, Model.HEIGHT,Model.LIFE_SPAN);
        settingsFrame.setVisible(true);
        frame.setVisible(true);

    }




}
