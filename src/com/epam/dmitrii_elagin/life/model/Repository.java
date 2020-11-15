package com.epam.dmitrii_elagin.life.model;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Repository implements Model {
    //Размер поля
    private Dimension fieldSize;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Возраст колонии
    private int age;

   Set<Point> data;

    private List<ModelListener> listeners;

    public Repository() {
        listeners=new LinkedList<ModelListener>();
        //Установка размера поля по-умолчанию
        fieldSize=new Dimension(Model.WIDTH,Model.HEIGHT);
        //Установка продолжительности жизни по-умолчанию
        lifeSpan=Model.LIFE_SPAN;

        data=new HashSet<>();
    }

    @Override
    public void setFieldSize(Dimension dimension) {

        this.fieldSize=dimension;

        sendEvent(new ModelEvent(dimension));
    }

    @Override
    public Collection<Point> getData() {
        return data;
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
