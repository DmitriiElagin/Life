package com.epam.dmitrii_elagin.life.model;

import java.awt.*;
import java.util.Collection;

public interface Model {

    //Ширина поля по-умолчанию
    int  WIDTH=10;

    //Высота поля по-умолчанию
    int HEIGHT=10;

    //Продолжительность жизни колонии по-умолчанию
    int LIFE_SPAN=100;

    void switchCell(Point p);

    public static enum State {
        RUNNING, STOPPED
    }

    void setFieldSize(Dimension dimension);

    Collection<Point> getData();

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
