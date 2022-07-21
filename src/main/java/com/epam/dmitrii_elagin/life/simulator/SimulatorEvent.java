package com.epam.dmitrii_elagin.life.simulator;

import java.awt.*;

public class SimulatorEvent {
    private Simulator.State state;

    private Dimension size;

    private final SimulatorEventType eventType;

    public SimulatorEvent(SimulatorEventType eventType) {
        this.eventType = eventType;
    }

    public SimulatorEvent(Simulator.State state) {
        this.state = state;
        this.eventType = SimulatorEventType.STATE_CHANGED;
    }

    public SimulatorEvent(Dimension size) {
        this.size = size;
        this.eventType = SimulatorEventType.FIELD_SIZE_CHANGED;
    }

    public Simulator.State getState() {
        return state;
    }

    public Dimension getSize() {
        return size;
    }

    public SimulatorEventType getEventType() {
        return eventType;
    }

    public enum SimulatorEventType {
        STATE_CHANGED,
        FIELD_SIZE_CHANGED,
        DATA_CHANGED
    }
}
