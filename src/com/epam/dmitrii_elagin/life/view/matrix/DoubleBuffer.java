package com.epam.dmitrii_elagin.life.view.matrix;

import java.awt.*;

abstract class DoubleBuffer extends Canvas {

    private final Dimension bufferSize;

    //Внеэкранный буфер
    private Image buffer;

    //Графика внеэкранного буфера
    private Graphics bufferGraphics;

    public DoubleBuffer() {

        bufferSize=new Dimension();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        //Создать новый буфер, если он не создан, или его размеры не совпадаютс канвой
        if (!bufferSize.equals(getSize())||
                buffer == null || bufferGraphics == null) {

            resetBuffer();
        }

        if (bufferGraphics != null) {
            bufferGraphics.clearRect(0, 0, bufferSize.width, bufferSize.height);
        }

        //Рисовать на графике внеэкранного буфера
        paintBuffer(bufferGraphics);

        //Нарисовать изображение буфера на канве
        g.drawImage(buffer, 0, 0, this);

    }

    //Рисование на буфере реализуется наследуемым классом
    public abstract void paintBuffer(Graphics bufferGraphics);

    //Создает новый внеэкранный буфер
    private void resetBuffer() {
        getSize(bufferSize);

        if (bufferGraphics != null) {
            //Очистка предыдущего изобразения
            bufferGraphics.dispose();
            bufferGraphics = null;
        }

        if (buffer != null) {
            buffer.flush();
            buffer = null;
        }

        //Вызвать сборщик мусора
        System.gc();

        buffer = createImage(bufferSize.width, bufferSize.height);
        bufferGraphics = buffer.getGraphics();
    }

}
