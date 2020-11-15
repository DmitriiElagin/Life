package com.epam.dmitrii_elagin.life.model;

import java.awt.*;

public interface Model {

    //Ширина поля по-умолчанию
    int  WIDTH=10;

    //Высота поля по-умолчанию
    int HEIGHT=10;

    //Продолжительность жизни колонии по-умолчанию
    int LIFE_SPAN=100;

    public static enum State {
        RUNNING, STOPPED
    }

    void setFieldSize(Dimension dimension);

    Dimension getFieldSize();

    void setLifeSpan(int lifeSpan);

    int getLifeSpan();

    void runSimulation();

    void stopSimulation();

    void clearField();

    void registerListener(ModelListener listener);

    void removeListener(ModelListener listener);

    void sendEvent(ModelEvent event);




}
