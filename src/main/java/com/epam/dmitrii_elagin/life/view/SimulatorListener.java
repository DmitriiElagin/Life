package com.epam.dmitrii_elagin.life.view;

import com.epam.dmitrii_elagin.life.simulator.SimulatorEvent;

public interface SimulatorListener {
    void handleEvent(SimulatorEvent event);

}
