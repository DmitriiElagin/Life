package com.epam.dmitrii_elagin.life.view.matrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Collection;

public final class BacteriaMatrix extends Matrix {
    private int rows;
    private int columns;
    private Image icon;
    private Collection<Point> bacteria;

    public BacteriaMatrix(int rows, int columns, Collection<Point> bacteria) {
        super();

        this.columns = columns;
        this.rows = rows;
        this.bacteria = bacteria;

        loadImagesFromResources();
    }

    public BacteriaMatrix() {
        super();
    }

    @Override
    protected void drawItem(int x, int y, Graphics graphics, Rectangle rect) {
        if (bacteria.contains(new Point(x, y))) {
            if (icon != null) {
                graphics.drawImage(icon, rect.x, rect.y, rect.width, rect.height, this);
            } else {
                graphics.setColor(Color.GREEN);
                ((Graphics2D) graphics).fill(rect);
            }
        }
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public void setColumns(int columns) {
        this.columns = columns;
    }

    private void loadImagesFromResources() {
        try {
            icon = ImageIO.read(getClass().getResourceAsStream("/images/bacteria.png"));
            setBackground(ImageIO.read(getClass().getResourceAsStream("/images/red_background.jpg")));
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}
