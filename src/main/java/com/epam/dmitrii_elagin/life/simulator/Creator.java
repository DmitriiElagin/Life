package com.epam.dmitrii_elagin.life.simulator;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

//Вычисляет клетки, в которых надо создать  новые бактерии
public class Creator implements Callable<List<Point>> {
    private final Simulator simulator;

    Creator(Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public List<Point> call() {
       final List<Point> result = new LinkedList<>();

        final Dimension fieldSize = simulator.getFieldSize();

        final Collection<Point> colony = simulator.getColony();

        for (int y = 0; y < fieldSize.height; y++) {
            for (int x = 0; x < fieldSize.width; x++) {
                final Point point = new Point(x, y);

                if (!colony.contains(point)) {
                    //Посчитать колл-во соседних занятых клеток
                    final int n = simulator.countNeighbors(point);

                    if (n > simulator.getLoneliness() && n < simulator.getTightness()) {
                        result.add(point);
                    }
                }
            }
        }

        return result;
    }
}
