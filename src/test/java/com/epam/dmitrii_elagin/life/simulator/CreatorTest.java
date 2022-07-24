package com.epam.dmitrii_elagin.life.simulator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.List;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreatorTest {

    @Parameterized.Parameter()
    public List<Point> points;

    @Parameterized.Parameter(1)
    public List<Point> expected;

    private Creator creator;


    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        List<Object[]> data = new LinkedList<>();
        data.add(new Object[]
                {Arrays.asList(new Point(0, 0),
                        new Point(1, 0), new Point(2, 0)),
                        Collections.singletonList(new Point(1, 1))});

        data.add(new Object[]
                {Arrays.asList(new Point(1, 0), new Point(0, 1),
                        new Point(1, 1), new Point(2, 1),
                        new Point(1, 2)),
                        Arrays.asList(new Point(0, 0), new Point(2, 0),
                                new Point(0, 2), new Point(2, 2))});

        data.add(new Object[]
                {Collections.singletonList(new Point(0, 0)),
                        Collections.EMPTY_LIST});

        return data;
    }

    @Before
    public void setUp() {
        Simulator simulator = new Simulator(new Dimension(30, 30), 100, 2, 4);

        simulator.getColony().addAll(points);

        creator = new Creator(simulator);
    }

    @Test
    public void testCall() {
        assertEquals(expected, creator.call());
    }
}