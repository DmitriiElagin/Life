package com.epam.dmitrii_elagin.life.model;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

//Поток, управляющий процессом моделирования
class ControlThread extends Thread {

    private final Collection<Point> colony;

    private final Model model;

    ControlThread(Model model) {

        this.model = model;

        this.colony = model.getColony();
    }

    @Override
    public void run() {
        boolean isRunning = true;

        //Возраст колонии
        int age = 0;

        //Переключить состояние приложения
        model.setState(IModel.State.RUNNING);

        //Если колония пуста, заполнить поле рандомно
        if (model.getColony().isEmpty()) {
            model.randomlyFill();

            model.sendEvent(new ModelEvent(ModelEvent.ModelEventType.DATA_CHANGED));
        }

        ExecutorService service = Executors.newCachedThreadPool();

        while (isRunning) {

            try {
                //Запустить выполнение потоков "Создания и Смерти" бактерий
                Future<List<Point>> creatorFuture = service.submit(new Creator(model));
                Future<List<Point>> reaperFuture = service.submit(new Reaper(model));

                //Ждать результатов работы потоков
                List<Point> newBacteria = creatorFuture.get();
                List<Point> deadBacteria = reaperFuture.get();

                model.getColony().addAll(newBacteria);

                model.getColony().removeAll(deadBacteria);

                //Уведомить вью об изменении данных
                model.sendEvent(new ModelEvent(ModelEvent.ModelEventType.DATA_CHANGED));

                //Пауза для наглядности симулуяции
                TimeUnit.MILLISECONDS.sleep(50);

                //Увеличить возраст колонии
                age++;

                //условие выполнения цикла
                isRunning = (age <= model.getLifeSpan()) &&
                        (model.getState() == IModel.State.RUNNING) &&
                        (!colony.isEmpty());

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
         }
        //Переключить состояние приложения
        model.setState(IModel.State.STOPPED);

        service.shutdown();
    }
}
