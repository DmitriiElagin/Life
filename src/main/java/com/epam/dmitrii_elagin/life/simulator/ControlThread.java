package com.epam.dmitrii_elagin.life.simulator;

import java.awt.*;
import java.util.List;
import java.util.concurrent.*;

//Поток, управляющий процессом моделирования
class ControlThread extends Thread {
    private static final int PRE_SIMULATION_DELAY = 2000;
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
        simulator.setState(Simulator.State.RUNNING);

        //Если колония пуста, заполнить поле рандомно
        if (simulator.getColony().isEmpty()) {
            simulator.randomlyFill();

            //Пауза переда началом симуляции
            try {
                TimeUnit.MILLISECONDS.sleep(PRE_SIMULATION_DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        final ExecutorService service = Executors.newCachedThreadPool();

        while (isRunning) {

            try {
                //Запустить выполнение потоков "Создания и Смерти" бактерий
                final Future<List<Point>> creatorFuture = service.submit(new Creator(simulator));
                final Future<List<Point>> reaperFuture = service.submit(new Reaper(simulator));

                //Ждать результатов работы потоков
                final List<Point> newBacteria = creatorFuture.get();
                final List<Point> deadBacteria = reaperFuture.get();

                simulator.getColony().addAll(newBacteria);

                deadBacteria.forEach(simulator.getColony()::remove);

                //Уведомить вью об изменении данных
                simulator.sendEvent(new SimulatorEvent(SimulatorEvent.SimulatorEventType.DATA_CHANGED));

                //Пауза для наглядности симулуяции
                TimeUnit.MILLISECONDS.sleep(50);

                //Увеличить возраст колонии
                age++;

                //условие выполнения цикла
                isRunning = (age < simulator.getLifeSpan()) &&
                        (simulator.getState() == Simulator.State.RUNNING) &&
                        (!simulator.getColony().isEmpty());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
         }
        //Переключить состояние приложения
        simulator.setState(Simulator.State.STOPPED);

        service.shutdown();
    }
}
