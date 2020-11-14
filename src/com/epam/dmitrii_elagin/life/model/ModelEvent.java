package com.epam.dmitrii_elagin.life.model;

import java.awt.*;

public class ModelEvent {

    private Model.State state;

    private Dimension size;

    private ModelEventType eventType;

    public ModelEvent() {
        eventType=ModelEventType.DATA_CHANGED;
    }

    public ModelEvent(Model.State state) {
        this.state = state;
        this.eventType=ModelEventType.STATE_CHANGED;
    }

    public ModelEvent(Dimension size) {
        this.size = size;
        this.eventType=ModelEventType.FIELD_SIZE_CHANGED;
    }

    public Model.State getState() {
        return state;
    }

    public Dimension getSize() {
        return size;
    }

    public ModelEventType getEventType() {
        return eventType;
    }

    public static enum ModelEventType {
        STATE_CHANGED,
        FIELD_SIZE_CHANGED,
        DATA_CHANGED
    }
}
