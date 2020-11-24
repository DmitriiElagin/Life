package com.epam.dmitrii_elagin.life.view;

import java.awt.*;

public abstract class DoubleBuffer extends Canvas{

    private int bufferWidth;
    private int bufferHeight;

    //Внеэкранный буфер
    private Image buffer;

    //Графика внеэкранного буфера
    private Graphics bufferGraphics;


    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        //Создать новый буфер, если он не создан, или его размеры не совпадаютс канвой
        if (bufferWidth != getSize().width||
                bufferHeight != getSize().height ||
                buffer == null || bufferGraphics == null) {

            resetBuffer();
        }


        if(bufferGraphics != null) {
            bufferGraphics.clearRect(0,0,bufferWidth,bufferHeight);
        }

        //Рисовать на графике внеэкранного буфера
        paintBuffer(bufferGraphics);

        //Нарисовать изображение буфера на канве
        g.drawImage(buffer,0,0,this);

    }

    //Рисование на буфере реализуется наследуемым классом
    public abstract void paintBuffer(Graphics bufferGraphics);

    //Создает новый внеэкранный буфер
    private void resetBuffer() {
        bufferWidth = getSize().width;
        bufferHeight = getSize().height;

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

        buffer = createImage(bufferWidth,bufferHeight);
        bufferGraphics = buffer.getGraphics();
    }




}
