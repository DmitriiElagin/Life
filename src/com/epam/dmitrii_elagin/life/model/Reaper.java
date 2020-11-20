package com.epam.dmitrii_elagin.life.model;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

//Callable, возвращающий список мертвых бактерий
public class Reaper implements Callable<List<Point>> {

    private final Model model;

    Reaper(Model model) {
        this.model=model;
    }

    @Override
    public List<Point> call() {

        System.out.println("Жнец начал работу...");
        Collection<Point> colony = model.getColony();

        List<Point> result=new LinkedList<>();

       for(Point point:colony) {
           int n = model.countNeighbors(point);

           if(n<Model.LONELINESS || n>Model.TIGHTNESS) {
               result.add(point);
           }
       }
        System.out.println("Жнец завершил работу...");
        return result;
    }
}
