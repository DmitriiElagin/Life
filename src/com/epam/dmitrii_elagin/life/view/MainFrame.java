package com.epam.dmitrii_elagin.life.view;


import com.epam.dmitrii_elagin.life.Main;
import com.epam.dmitrii_elagin.life.controller.MainController;
import com.epam.dmitrii_elagin.life.model.IModel;
import com.epam.dmitrii_elagin.life.model.ModelEvent;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;


public class MainFrame extends Frame implements ActionListener, ModelListener {


    private final MainController controller;

    private Dimension fieldSize;

    private Collection<Point> data;

    private Button btnStart;
    private Button btnClear;

    //Панель для размещения матрицы
    private Panel pnlMatrix;

    private GridLayout gridLayout;

    public MainFrame(MainController controller) {
        this.controller = controller;

        initUI();
    }

    public void setData(Collection<Point> data) {
        this.data = data;
    }

    //Создание и инициализация компонентов интерфейса
    private void initUI() {
        setTitle("Life");

        setSize(Main.getProperty(Main.MAIN_WIDTH), Main.getProperty(Main.MAIN_HEIGHT));

        //Установить размер поля по-умолчанию
        int size = Main.getProperty(Main.FIELD_SIZE);
        fieldSize = new Dimension(size, size);

        setResizable(false);


        //Расположить окно по центру
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                dispose();
            }
        });

        gridLayout = new GridLayout();

        pnlMatrix = new Panel(gridLayout);

        createMatrix();

        add(pnlMatrix, BorderLayout.CENTER);


        createMenu();

        createControlPanel();
    }

    private void createMenu() {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        menu.setFont(font);

        MenuItem miSettings = new MenuItem("Settings");
        miSettings.addActionListener(this);
        MenuItem miExit = new MenuItem("Exit");
        miExit.addActionListener(this);

        menu.add(miSettings);
        menu.add(miExit);
        menuBar.add(menu);
        setMenuBar(menuBar);
    }

    //Создать панель с кнопками управления
    private void createControlPanel() {
        //Создать панель для кнопок управления
        Panel pnlControl = new Panel();
        pnlControl.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        //Создать шрифт для кнопок управления
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 26);

        pnlControl.setFont(font);

        //Создать размерность конопок управления
        Dimension dimension = new Dimension(150, 50);

        btnStart = new Button("Start");
        btnStart.setPreferredSize(dimension);

        btnStart.addActionListener(this);
        pnlControl.add(btnStart);

        Button btnStop = new Button("Stop");
        btnStop.setPreferredSize(dimension);
        btnStop.addActionListener(this);
        pnlControl.add(btnStop);

        btnClear = new Button("Clear");
        btnClear.setPreferredSize(dimension);
        btnClear.addActionListener(this);
        pnlControl.add(btnClear);
        add(pnlControl, BorderLayout.SOUTH);
    }

    //Создает и настраивает матрицу компонентов с размерностью поля
    private void createMatrix() {

        gridLayout.setColumns(fieldSize.width);
        gridLayout.setRows(fieldSize.height);

        gridLayout.setVgap(1);
        gridLayout.setHgap(1);

        pnlMatrix.removeAll();

        Cell cell;

        for (int y = 0, i = 0; y < fieldSize.height; y++) {
            for (int x = 0; x < fieldSize.width; x++, i++) {
                cell = new Cell();
                cell.setBackground(Color.LIGHT_GRAY);
                cell.setLocation(new Point(x, y));

                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Cell c = (Cell) e.getSource();

                        controller.onCellClick(c.getLocation());
                    }
                });
                pnlMatrix.add(cell);
            }
        }
        //Перерисовать панель
        pnlMatrix.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Clear":
                controller.onClearAction();

                break;
            case "Settings":
                controller.onSettingsAction(this);

                break;
            case "Start":
                controller.onStartAction();

                break;
            case "Stop":
                controller.onStopAction();

                break;
            case "Exit":
                controller.onStopAction();
                dispose();
                break;
        }
    }

    @Override
    public void handleEvent(ModelEvent event) {

        switch (event.getEventType()) {
            case STATE_CHANGED:
                setButtonsState(event.getState());
                break;
            case FIELD_SIZE_CHANGED:
                fieldSize = event.getSize();
                createMatrix();
                break;
            case DATA_CHANGED:
                updateCells();
                break;
        }
    }

    //Обновляет отображение ячеек в соответствии с данными
    private void updateCells() {

        Component[] cells = pnlMatrix.getComponents();
        Point point = new Point();
        for (int y = 0, i = 0; y < fieldSize.height; y++) {
            for (int x = 0; x < fieldSize.width; x++, i++) {
                point.setLocation(x, y);

                if (data.contains(point)) {
                    cells[i].setBackground(Color.green);
                } else {
                    cells[i].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    private void setButtonsState(IModel.State state) {
        if (state == IModel.State.RUNNING) {
            btnStart.setEnabled(false);
            btnStart.setLabel("Running...");
            btnClear.setEnabled(false);
        } else if (state == IModel.State.STOPPED) {
            btnStart.setEnabled(true);
            btnStart.setLabel("Start");
            btnClear.setEnabled(true);
        }
    }
}
