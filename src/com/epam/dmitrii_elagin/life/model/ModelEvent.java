package com.epam.dmitrii_elagin.life.model;

import java.awt.*;

public class ModelEvent {

    private IModel.State state;

    private Dimension size;

    private ModelEventType eventType;

    public ModelEvent(ModelEventType eventType) {
        this.eventType=eventType;
    }

    public ModelEvent(IModel.State state) {
        this.state = state;
        this.eventType=ModelEventType.STATE_CHANGED;
    }

    public ModelEvent(Dimension size) {
        this.size = size;
        this.eventType=ModelEventType.FIELD_SIZE_CHANGED;
    }

    public IModel.State getState() {
        return state;
    }

    public Dimension getSize() {
        return size;
    }

    public ModelEventType getEventType() {
        return eventType;
    }

    public enum ModelEventType {
        STATE_CHANGED,
        FIELD_SIZE_CHANGED,
        DATA_CHANGED
    }
}
