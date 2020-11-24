package com.epam.dmitrii_elagin.life.model;

import com.epam.dmitrii_elagin.life.Main;
import com.epam.dmitrii_elagin.life.view.ModelListener;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Model implements IModel {

    public static final int LONELINESS = 2;

    public static final int TIGHTNESS = 4;

    //Размер поля
    private Dimension fieldSize;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Состояние приложения. Выполняется симуляция или нет.
    private State state;

    //Коллекция координат занятых клеток
    private final Collection<Point> colony;

    private final List<ModelListener> listeners;

    public Model() {
        listeners = new LinkedList<>();

        colony = Collections.synchronizedCollection(new HashSet<>());

        state = State.STOPPED;

        //Установить размер поля по-умолчанию
        int size = Main.getProperty(Main.FIELD_SIZE);
        fieldSize = new Dimension(size, size);

        //Установить продолжительность жизни колонии по-умолчинию
        setLifeSpan(Main.getProperty(Main.LIFE_SPAN));


    }

    State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        sendEvent(new ModelEvent(state));
    }

    //Заполнить поле произвольно
    void randomlyFill() {
        Random random = new Random(System.currentTimeMillis());
        for (int y = 0; y < fieldSize.height; y++) {
            for (int x = 0; x < fieldSize.width; x++) {
                if (random.nextBoolean()) {
                    colony.add(new Point(x, y));
                }
            }
        }
    }

    //Считать колл-во занятых соседних клеток
    int countNeighbors(Point p) {
        int i = 0;

        Point point = new Point();

        for (int y = p.y - 1; y <= p.y + 1; y++) {
            for (int x = p.x - 1; x <= p.x + 1; x++) {

                point.setLocation(x, y);

                if (point.equals(p)) {
                    continue;
                }

                if (colony.contains(point)) {
                    i++;
                }
            }
        }
        return i;
    }

    //Добаляет Point, если ее нет в наборе, иначе удаляет ее
    @Override
    public void switchCell(Point p) {
        if (!colony.add(p)) {
            colony.remove(p);
        }
        sendEvent(new ModelEvent(ModelEvent.ModelEventType.DATA_CHANGED));

    }

    @Override
    public void setFieldSize(Dimension dimension) {

        if (!dimension.equals(fieldSize)) {
            this.fieldSize = dimension;

            colony.clear();

            sendEvent(new ModelEvent(dimension));
        }
    }

    @Override
    public Collection<Point> getColony() {
        return colony;
    }

    @Override
    public Dimension getFieldSize() {
        return fieldSize;
    }

    @Override
    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    @Override
    public int getLifeSpan() {
        return lifeSpan;
    }

    @Override
    public void runSimulation() {
        new ControlThread(this).start();

    }

    @Override
    public void stopSimulation() {
        state = State.STOPPED;
    }

    @Override
    public void clearField() {
        colony.clear();
        sendEvent(new ModelEvent(ModelEvent.ModelEventType.DATA_CHANGED));
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
        for (ModelListener listener : listeners) {
            listener.handleEvent(event);
        }

    }

}
