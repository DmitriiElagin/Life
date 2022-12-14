package com.epam.dmitrii_elagin.life.simulator;

import com.epam.dmitrii_elagin.life.view.SimulatorListener;

import java.awt.*;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Simulator {
    private static final int RANDOM_SQUARE_AREA = 4;
    private static final int DENSITY = 5;

    public enum State {
        RUNNING, STOPPED
    }

    //Параметр одиночества бактерий
    private int loneliness;

    //Параметр тесноты бактерий
    private int tightness;

    //Размер поля
    private Dimension fieldSize;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Состояние приложения. Выполняется симуляция или нет.
    private State state;

    //Карта координат занятых клеток
    private final Set<Point> colony;

    private final Queue<SimulatorListener> listeners;

    public Simulator(Dimension fieldSize, int lifeSpan, int loneliness, int tightness) {
        this.loneliness = loneliness;
        this.tightness = tightness;
        this.fieldSize = fieldSize;
        this.lifeSpan = lifeSpan;

        listeners = new LinkedList<>();

        //Создать Set на основе ConcurrentHashMap
        colony = ConcurrentHashMap.newKeySet();

        state = State.STOPPED;
    }

    Set<Point> getColony() {
        return colony;
    }

    public Collection<Point> getBacteriaCollection() {
        return Collections.unmodifiableSet(colony);
    }

    public int getLoneliness() {
        return loneliness;
    }


    public void setLoneliness(int loneliness) {
        this.loneliness = loneliness;
    }


    public int getTightness() {
        return tightness;
    }


    public void setTightness(int tightness) {
        this.tightness = tightness;
    }

    State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        sendEvent(new SimulatorEvent(state));
    }

    //Заполнить поле произвольно
    public void randomlyFill() {
        int count = 0;
        int x0 = (int) (Math.random() * (fieldSize.width - RANDOM_SQUARE_AREA));
        int y0 = (int) (Math.random() * (fieldSize.height - RANDOM_SQUARE_AREA));

        // Добавлять бактерии с рандомным расположением, пока не будет достигнута нужная плотность области
        while (count < DENSITY) {
            int x = (int) (Math.random() * RANDOM_SQUARE_AREA) + x0;
            int y = (int) (Math.random() * RANDOM_SQUARE_AREA) + y0;

            if (colony.add(new Point(x, y))) {
                count++;
            }
        }
        sendEvent(new SimulatorEvent(SimulatorEvent.SimulatorEventType.DATA_CHANGED));
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
    public void switchCell(Point p) {
        if (!colony.add(p)) {
            colony.remove(p);
        }
        sendEvent(new SimulatorEvent(SimulatorEvent.SimulatorEventType.DATA_CHANGED));
    }

    public void setFieldSize(Dimension dimension) {

        if (!dimension.equals(fieldSize)) {
            this.fieldSize = dimension;

            colony.clear();

            sendEvent(new SimulatorEvent(dimension));
        }
    }


    public Dimension getFieldSize() {
        return fieldSize;
    }


    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }


    public int getLifeSpan() {
        return lifeSpan;
    }


    public void runSimulation() {
        new ControlThread(this).start();
    }


    public void stopSimulation() {
        state = State.STOPPED;
    }


    public void clearField() {
        colony.clear();
        sendEvent(new SimulatorEvent(SimulatorEvent.SimulatorEventType.DATA_CHANGED));
    }


    public void registerListener(SimulatorListener listener) {
        listeners.add(listener);
    }


    public void removeListener(SimulatorListener listener) {
        listeners.remove(listener);
    }


    public void sendEvent(SimulatorEvent event) {
        for (SimulatorListener listener : listeners) {
            listener.handleEvent(event);
        }

    }

}
