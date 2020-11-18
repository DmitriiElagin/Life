package com.epam.dmitrii_elagin.life.model;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.jar.JarOutputStream;

public class Repository implements Model {

    public static final int LONELINESS=2;

    public static final int TIGHTNESS=4;



    //Размер поля
    private Dimension fieldSize;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Возраст колонии
    private int age;

    private State state;

   //Набор координат занятых ячеек
    Set<Point> data;

    private List<ModelListener> listeners;

    public Repository() {
        listeners=new LinkedList<ModelListener>();
        data=new HashSet<>();
        age=0;
        state=State.STOPPED;
    }

    //Заполнить поле произвольно
    private void randomlyFill(){
        Random random=new Random(System.currentTimeMillis());
        Point point=new Point();
        for(int y=0; y<fieldSize.height; y++){
            for(int x=0; x<fieldSize.width; x++){
                point.setLocation(x,y);
                if(random.nextBoolean()){
                    data.add(new Point(point));
                }
            }
        }
    }

    //Считать соседние клетки
    int countNeighbors(Point p){
        int i=0;
        Point point=new Point();
        for(int y=p.y-1;y<=p.y+1;y++){
            for(int x=p.x-1; x<=p.x+1; x++) {
                point.setLocation(x,y);
                if(point.equals(p)){
                    continue;
                }
                if(data.contains(point)) {
                    i++;
                }
            }
        }
        return i;
    }

    //Добаляет Point, если ее нет в наборе, иначе удаляет ее
    @Override
    public void switchCell(Point p) {
       if(!data.add(p)) {
           data.remove(p);
       }
       sendEvent(new ModelEvent());


    }



    @Override
    public void setFieldSize(Dimension dimension) {

        this.fieldSize=dimension;

        data.clear();

        sendEvent(new ModelEvent(dimension));
    }

    @Override
    public Collection<Point> getData() {
        return data;
    }

    @Override
    public Dimension getFieldSize() {
        return fieldSize;
    }

    @Override
    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan=lifeSpan;
    }

    @Override
    public int getLifeSpan() {
        return lifeSpan;
    }

    @Override
    public void runSimulation() {
        if(data.isEmpty()) {
            randomlyFill();
            sendEvent(new ModelEvent());
        }

        new MasterThread().start();

    }

    @Override
    public void stopSimulation() {
        state=State.STOPPED;
        sendEvent(new ModelEvent(State.STOPPED));

    }

    @Override
    public void clearField() {
            data.clear();
            sendEvent(new ModelEvent());
    }

    @Override
    public void registerListener(ModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ModelListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void sendEvent(ModelEvent event) {
        for(ModelListener listener:listeners) {
            listener.handleEvent(event);
        }

    }


    class MasterThread extends Thread {

        @Override
        public void run() {

            boolean isRunning=true;
            age=0;
           
            state= Model.State.RUNNING;
            sendEvent(new ModelEvent(state));

            ExecutorService service = Executors.newFixedThreadPool(4);

            List<Point> newBorns;

            List<Point> dead;

            while (isRunning) {

                try {

                  newBorns = service.submit(new Checker(true)).get();

                  dead=service.submit(new Checker(false)).get();

                  data.addAll(newBorns);

                  data.removeAll(dead);

                  age++;

                  isRunning=(age<=lifeSpan)&&
                          (state == Model.State.RUNNING)&&
                          (!data.isEmpty());

                  sendEvent(new ModelEvent());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
            sendEvent(new ModelEvent(Model.State.STOPPED));

            service.shutdown();


        }
    }

    class Checker implements Callable<List<Point>> {

        private boolean isCreator;

        List<Point> result;

        private

        Checker(boolean isCreator) {
            this.isCreator=isCreator;

            result=new LinkedList<>();
        }

        @Override
        public List<Point> call() throws Exception {

            for(int y=0; y<fieldSize.height; y++) {
                    for(int x=0; x<fieldSize.width; x++) {
                        Point point=new Point(x,y);

                        TimeUnit.MILLISECONDS.sleep(125);

                        synchronized (data) {
                            checkCell(point);
                        }


                    }
                }

            return result;
        }

        private void checkCell(Point point) {

            if(isCreator) {
                System.out.println("Создатель проверяет клетку");
            }

            else {
                System.out.println("Жнец проверяет клетку");
            }

            boolean condition=false;

            int neighbors = countNeighbors(point);

            //Установить условие добавления точки в зависимости от роли потока
            if(isCreator) {

                if(!data.contains(point)) {
                    condition = ((neighbors>LONELINESS)&&(neighbors<TIGHTNESS));

                }
            }

            else {
                if(data.contains(point)) {
                    condition= ((neighbors<LONELINESS)||(neighbors>TIGHTNESS));
                }
            }

            if(condition) {
                result.add(point);
            }

        }
    }
}
