package com.epam.dmitrii_elagin.life.controller;

public class SettingsController {

    public void onExit() {

    }

    public void onOkAction(int rows, int cols, int maxAge) {
        System.out.printf("Rows - %d, Cols - %d, Max age - %d",rows,cols,maxAge);
    }
}
