package com.epam.dmitrii_elagin.life.simulator;


import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Collection;

import static org.junit.Assert.*;

public class SimulatorStateTest {

    private Simulator simulator;

    @Before
    public void init() {
        simulator = new Simulator(new Dimension(30, 30), 100, 2, 4);

        for (int y = 1; y < 4; y++) {
            for (int x = 1; x < 4; x++) {
                simulator.getColony().add(new Point(x, y));
            }
        }

    }
    @Test
    public void testWidthAndHeightMustBeGreaterThanOne() {


        Dimension size = simulator.getFieldSize();

        assertTrue(size.width > 1);

        assertTrue(size.height > 1);
    }

    @Test
    public void testInitialStateShouldBeStopped() {
        assertSame(simulator.getState(), Simulator.State.STOPPED);

    }

    @Test
    public void testCountNeighbors() {
        assertEquals(0, simulator.countNeighbors(new Point(0, 5)));
        assertEquals(1, simulator.countNeighbors(new Point(0, 0)));
        assertEquals(8, simulator.countNeighbors(new Point(2, 2)));
        assertEquals(3, simulator.countNeighbors(new Point(3, 3)));
    }

    @Test
    public void testSwitchCell() {
        Collection<Point> colony = simulator.getColony();

        Point point = new Point(10, 10);

        simulator.switchCell(point);

        assertTrue(colony.contains(point));

        simulator.switchCell(point);

        assertFalse(colony.contains(point));

    }

    @Test
    public void testRandomlyFill() {
        simulator.getColony().clear();

        simulator.randomlyFill();

        assertFalse(simulator.getColony().isEmpty());

    }

    @Test
    public void testClearFieldShouldClearColony() {
        simulator.clearField();

        assertTrue(simulator.getColony().isEmpty());
    }

    @Test
    public void testStopSimulation() {
        simulator.setState(Simulator.State.RUNNING);

        simulator.stopSimulation();

        assertEquals(Simulator.State.STOPPED, simulator.getState());


    }


}