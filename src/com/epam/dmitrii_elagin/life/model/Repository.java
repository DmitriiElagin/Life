package com.epam.dmitrii_elagin.life.model;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Repository implements Model {
    //Размер поля
    private Dimension fieldSize;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Возраст колонии
    private int age;

    private List<ModelListener> listeners;

    public Repository() {
        listeners=new LinkedList<ModelListener>();
    }

    @Override
    public void setFieldSize(Dimension dimension) {

        this.fieldSize=dimension;

        sendEvent(new ModelEvent(dimension));
    }

    @Override
    public Dimension getFieldSize() {
        return fieldSize;
    }

    @Override
    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan=lifeSpan;
    }

    @Override
    public int getLifeSpan() {
        return lifeSpan;
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

    @Override
    public void registerListener(ModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ModelListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void sendEvent(ModelEvent event) {
        for(ModelListener listener:listeners) {
            listener.handleEvent(event);
        }

    }


}
