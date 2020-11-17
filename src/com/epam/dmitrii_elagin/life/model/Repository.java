package com.epam.dmitrii_elagin.life.model;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.jar.JarOutputStream;

public class Repository implements Model {
    //Размер поля
    private Dimension fieldSize;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Возраст колонии
    private int age;

   //Набор координат занятых ячеек
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

    //Заполнить поле произвольно
    private void randomlyFill(){
        Random random=new Random(System.currentTimeMillis());
        Point point=new Point();
        for(int y=0; y<fieldSize.height; y++){
            for(int x=0; x<fieldSize.width; x++){
                point.setLocation(x,y);
                if(random.nextBoolean()){
                    data.add(new Point(point));
                }
            }
        }
    }

    //Считать соседние клетки
    int countNeighbors(Point p){
        int i=0;
        Point point=new Point();
        for(int y=p.y-1;y<=p.y+1;y++){
            for(int x=p.x-1; x<=p.x+1; x++) {
                point.setLocation(x,y);
                if(point.equals(p)){
                    continue;
                }
                if(data.contains(point)) {
                    i++;
                }
            }
        }
        return i;
    }

    //Добаляет Point, если ее нет в наборе, иначе удаляет ее
    @Override
    public void switchCell(Point p) {
       if(!data.add(p)) {
           data.remove(p);
       }
       sendEvent(new ModelEvent());


    }



    @Override
    public void setFieldSize(Dimension dimension) {

        this.fieldSize=dimension;

        data.clear();

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
        if(data.isEmpty()) {
            randomlyFill();
            sendEvent(new ModelEvent());
        }
        sendEvent(new ModelEvent(State.RUNNING));
    }

    @Override
    public void stopSimulation() {
        sendEvent(new ModelEvent(State.STOPPED));

    }

    @Override
    public void clearField() {
            data.clear();
            sendEvent(new ModelEvent());
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
