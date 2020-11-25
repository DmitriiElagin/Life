package com.epam.dmitrii_elagin.life.view.matrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
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

    private Image icon;
    private Image background;

    private final List<CellClickListener> cellClickListeners;

    public Matrix(int rows, int columns, Collection<Point> filledCells) {
        this.rows = rows;
        this.columns = columns;
        this.filledCells = filledCells;

        cellClickListeners = new LinkedList<>();

        addMouseListener(new MouseClickListener());

        loadImagesFromResources();

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

    private void loadImagesFromResources() {
        try {
            icon= ImageIO.read(getClass().getResourceAsStream("/resources/images/bacteria.png"));
            background=ImageIO.read(getClass().getResourceAsStream("/resources/images/background.jpg"));
        } catch (IOException e) {
            System.err.println("IOException: "+e.getMessage());
        }

    }

    @Override
    public void paintBuffer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        if(background != null) {
            g2d.drawImage(background,0,0,width,height,this);
        }else {
            g2d.setBackground(Color.darkGray);
        }

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
                    if(icon!=null) {
                        g.drawImage(icon,rect.x,rect.y,rect.width,rect.height,this);
                    }
                    else {
                        g.setColor(Color.GREEN);
                        g2d.fill(rect);
                    }

                }

                g.setColor(Color.gray);
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
