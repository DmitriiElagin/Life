package com.epam.dmitrii_elagin.life.model;

import java.awt.*;

public class Repository implements Model {
    //Размер поля
    private Dimension fieldSize;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Возраст колонии
    private int age;

    @Override
    public void setFieldSize(Dimension dimension) {

    }

    @Override
    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan=lifeSpan;
    }

    @Override
    public void runSimulation() {

    }

    @Override
    public void stopSimulation() {

    }

    @Override
    public void clearField() {

    }
}
