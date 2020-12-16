package com.epam.dmitrii_elagin.life.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ReaperTest {

    @Parameterized.Parameter()
    public java.util.List<Point> points;

    @Parameterized.Parameter(1)
    public List<Point> expected;

    private Reaper reaper;

    @Before
    public void setUp() {
        Model model = new Model();

        model.setTightness(4);
        model.setLoneliness(2);

        model.getColony().addAll(points);

        reaper = new Reaper(model);

    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        List<Object[]> data = new LinkedList<>();
        data.add(new Object[]
                {Arrays.asList(new Point(0, 0),
                        new Point(1, 0), new Point(2, 0)),
                        Arrays.asList(new Point(0, 0), new Point(2, 0))});

        data.add(new Object[]
                {Arrays.asList(new Point(0, 0), new Point(1, 0), new Point(2, 0),
                        new Point(0, 1), new Point(1, 1), new Point(2, 1)),
                        Arrays.asList(new Point(1, 0), new Point(1, 1))});

        data.add(new Object[]
                {Arrays.asList(new Point(0, 0), new Point(0, 1), new Point(1, 1)),
                        Collections.EMPTY_LIST});

        return data;
    }

    @Test
    public void testCall() {
        assertEquals(expected, reaper.call());
    }
}