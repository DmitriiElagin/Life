package com.epam.dmitrii_elagin.life.simulator;

import java.awt.*;
import java.util.List;
import java.util.concurrent.*;

//Поток, управляющий процессом моделирования
class ControlThread extends Thread {

    private final Simulator simulator;

    ControlThread(Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public void run() {
        boolean isRunning = true;

        //Возраст колонии
        int age = 0;

        //Переключить состояние приложения
        simulator.setState(IModel.State.RUNNING);

        //Если колония пуста, заполнить поле рандомно
        if (simulator.getColony().isEmpty()) {
            simulator.randomlyFill();

            simulator.sendEvent(new SimulatorEvent(SimulatorEvent.SimulatorEventType.DATA_CHANGED));
        }

        ExecutorService service = Executors.newCachedThreadPool();

        while (isRunning) {

            try {
                //Запустить выполнение потоков "Создания и Смерти" бактерий
                Future<List<Point>> creatorFuture = service.submit(new Creator(simulator));
                Future<List<Point>> reaperFuture = service.submit(new Reaper(simulator));

                //Ждать результатов работы потоков
                List<Point> newBacteria = creatorFuture.get();
                List<Point> deadBacteria = reaperFuture.get();

                simulator.getColony().addAll(newBacteria);

                simulator.getColony().removeAll(deadBacteria);

                //Уведомить вью об изменении данных
                simulator.sendEvent(new SimulatorEvent(SimulatorEvent.SimulatorEventType.DATA_CHANGED));

                //Пауза для наглядности симулуяции
                TimeUnit.MILLISECONDS.sleep(50);

                //Увеличить возраст колонии
                age++;

                //условие выполнения цикла
                isRunning = (age < simulator.getLifeSpan()) &&
                        (simulator.getState() == IModel.State.RUNNING) &&
                        (!simulator.getColony().isEmpty());

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
         }
        //Переключить состояние приложения
        simulator.setState(IModel.State.STOPPED);

        service.shutdown();
    }
}
