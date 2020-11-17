package com.epam.dmitrii_elagin.life.view;

import java.awt.*;

class Cell extends Canvas {

    private Point location;

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public void setLocation(Point location) {
        this.location = location;
    }
}
