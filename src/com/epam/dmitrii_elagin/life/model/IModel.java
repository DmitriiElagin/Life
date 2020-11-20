package com.epam.dmitrii_elagin.life.model;

import java.awt.*;
import java.util.Collection;

public interface IModel {

    void switchCell(Point p);

    enum State {
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
