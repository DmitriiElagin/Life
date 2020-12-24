package com.epam.dmitrii_elagin.life.model;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

//Вычисляет клетки, в которых надо создать  новые бактерии
public class Creator implements Callable<List<Point>> {

    private final Model model;

    Creator(Model model) {
        this.model = model;
    }

    @Override
    public List<Point> call() {
        List<Point> result = new LinkedList<>();

        Dimension fieldSize = model.getFieldSize();

        Collection<Point> colony = model.getColony();

        for (int y = 0; y < fieldSize.height; y++) {
            for (int x = 0; x < fieldSize.width; x++) {
                Point point = new Point(x, y);

                if (!colony.contains(point)) {
                    //Посчитать колл-во соседних занятых клеток
                    int n = model.countNeighbors(point);

                    if (n > model.getLoneliness() && n < model.getTightness()) {
                        result.add(point);
                    }
                }
            }
        }

        return result;
    }
}
