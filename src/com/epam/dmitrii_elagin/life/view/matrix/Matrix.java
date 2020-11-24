package com.epam.dmitrii_elagin.life.view.matrix;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Matrix extends DoubleBuffer {

    private int rows;
    private int columns;

    private final Collection<Point> filledCells;

    private int cellWidth;
    private int cellHeight;

    private int xOffset;
    private int yOffset;

    private List<CellClickListener> cellClickListeners;

    public Matrix(int rows, int columns, Collection<Point> filledCells) {
        this.rows = rows;
        this.columns = columns;
        this.filledCells = filledCells;

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

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public void paintBuffer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setBackground(Color.lightGray);

        //Установить толщину линий сетки в 2 px
        g2d.setStroke(new BasicStroke(2));

        int width = getWidth();
        int height = getHeight();

        cellWidth = width / columns;
        cellHeight = height / rows;

        xOffset = (width - (columns * cellWidth)) / 2;
        yOffset = (height - (rows * cellHeight)) / 2;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {

                Point point = new Point(col, row);

                Rectangle rect = new Rectangle(xOffset + (col * cellWidth),
                        yOffset + (row * cellHeight),
                        cellWidth, cellHeight);

                if (filledCells.contains(point)) {
                    g.setColor(Color.GREEN);
                    g2d.fill(rect);
                }

                g.setColor(Color.white);
                g2d.draw(rect);
            }
        }

    }

    private class MouseClickListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            int column = (e.getX() - xOffset) / cellWidth;
            int row = (e.getY() - yOffset) / cellHeight;

            notifyCellClickListeners(column, row);

            System.out.println(column + " " + row);
        }
    }

}
