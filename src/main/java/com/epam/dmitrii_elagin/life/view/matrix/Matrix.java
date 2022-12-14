package com.epam.dmitrii_elagin.life.view.matrix;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Matrix extends DoubleBuffer {
    private int cellWidth;
    private int cellHeight;
    private int xOffset;
    private int yOffset;
    private Image background;

    private final Queue<CellClickListener> cellClickListeners;

    protected Matrix() {
        cellClickListeners = new LinkedList<>();

        addMouseListener(new MouseClickListener());
    }

    public void addCellClickListener(CellClickListener listener) {
        if (listener != null) {
            cellClickListeners.add(listener);
        }
    }

    private void notifyCellClickListeners(int column, int row) {
        for (CellClickListener listener : cellClickListeners) {
            listener.cellClicked(column, row);
        }
    }

    protected abstract void drawItem(int x, int y, Graphics graphics, Rectangle rect);

    public abstract int getColumns();

    public abstract int getRows();


    @Override
    public final void paintBuffer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        final int width = getWidth();
        final int height = getHeight();

        if (background != null) {
            g2d.drawImage(background, 0, 0, width, height, this);
        } else {
            g2d.setBackground(Color.darkGray);
        }

        cellWidth = width / getColumns();
        cellHeight = height / getRows();

        xOffset = (width - (getColumns() * cellWidth)) / 2;
        yOffset = (height - (getRows() * cellHeight)) / 2;

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                Rectangle rect = new Rectangle(xOffset + (col * cellWidth),
                        yOffset + (row * cellHeight),
                        cellWidth, cellHeight);

                drawItem(col, row, g, rect);

                //Установить цвет сетки
                g.setColor(Color.gray);

                //Нарисовать сетку
                g2d.draw(rect);
            }
        }

    }

    public abstract void setColumns(int columns);

    public abstract void setRows(int rows);

    public void setBackground(Image background) {
        this.background = background;
    }

    private class MouseClickListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            final int column = (e.getX() - xOffset) / cellWidth;
            final int row = (e.getY() - yOffset) / cellHeight;

            notifyCellClickListeners(column, row);

        }
    }

}
