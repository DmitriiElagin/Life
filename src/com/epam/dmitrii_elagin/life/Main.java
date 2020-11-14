package com.epam.dmitrii_elagin.life;

import com.epam.dmitrii_elagin.life.controller.SettingsController;
import com.epam.dmitrii_elagin.life.view.MainFrame;
import com.epam.dmitrii_elagin.life.view.SettingsDialog;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        MainFrame frame=new MainFrame();
        SettingsController settingsController=new SettingsController();
        SettingsDialog settingsFrame=new SettingsDialog(frame, settingsController,10,10,50);
        settingsFrame.setVisible(true);
        frame.setVisible(true);

    }




}
