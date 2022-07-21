package com.epam.dmitrii_elagin.life.simulator;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

//Вычисляет клетки с мертвыми бактериями
public class Reaper implements Callable<List<Point>> {

    private final Simulator simulator;

    Reaper(Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public List<Point> call() {

        Collection<Point> colony = simulator.getColony();

        List<Point> result = new LinkedList<>();

        for (Point point : colony) {
            int n = simulator.countNeighbors(point);

            if (n < simulator.getLoneliness() || n > simulator.getTightness()) {
                result.add(point);
            }
        }

        return result;
    }
}
