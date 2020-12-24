package com.epam.dmitrii_elagin.life.model;


import org.junit.*;

import java.awt.*;
import java.util.Collection;

import static org.junit.Assert.*;

public class ModelStateTest {

    private Model model;

    @Before
    public void init() {
        model = new Model();

        for(int y = 1; y < 4;y++) {
            for(int x = 1; x < 4; x++) {
               model.getColony().add(new Point(x, y));
            }
        }

    }

    @Test
    public void testColonyShouldBeNotNull(){
        Model model = new Model();

        assertNotNull(model.getColony());
    }

    @Test
    public void testWidthAndHeightMustBeGreaterThanOne() {


        Dimension size = model.getFieldSize();

        assertTrue(size.width > 1);

        assertTrue(size.height > 1);
    }

    @Test
    public void testInitialStateShouldBeStopped() {
        assertSame(model.getState(), IModel.State.STOPPED);

    }

    @Test
    public void testCountNeighbors(){
        assertEquals(0, model.countNeighbors(new Point(0,5)));
        assertEquals(1,model.countNeighbors(new Point(0,0)));
        assertEquals(8, model.countNeighbors(new Point(2,2)));
        assertEquals(3,model.countNeighbors(new Point(3,3)));
    }

    @Test
    public void testSwitchCell() {
        Collection<Point> colony = model.getColony();

        Point point=new Point(10,10);

        model.switchCell(point);

        assertTrue(colony.contains(point));

        model.switchCell(point);

        assertFalse(colony.contains(point));

    }

    @Test
    public void testRandomlyFill(){
        model.getColony().clear();

        model.randomlyFill();

        assertFalse(model.getColony().isEmpty());

    }

    @Test
    public void testClearFieldShouldClearColony(){
        model.clearField();

        assertTrue(model.getColony().isEmpty());
    }

    @Test
    public void testStopSimulation(){
        model.setState(IModel.State.RUNNING);

        model.stopSimulation();

        assertEquals(IModel.State.STOPPED, model.getState());


    }



}