package com.epam.dmitrii_elagin.life.model;

import java.awt.*;

public interface Model {

    void setFieldSize(Dimension dimension);

    void setLifeSpan(int lifeSpan);

    void runSimulation();

    void stopSimulation();

    void clearField();




}
