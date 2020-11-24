package com.epam.dmitrii_elagin.life.view;


import com.epam.dmitrii_elagin.life.Main;
import com.epam.dmitrii_elagin.life.controller.MainController;
import com.epam.dmitrii_elagin.life.model.IModel;
import com.epam.dmitrii_elagin.life.model.ModelEvent;
import com.epam.dmitrii_elagin.life.view.matrix.CellClickListener;
import com.epam.dmitrii_elagin.life.view.matrix.Matrix;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;


public class MainFrame extends Frame implements ActionListener, ModelListener, CellClickListener {

    private final MainController controller;

    //Размер игрового поля
    private Dimension fieldSize;

    //Данные из модели
    private Collection<Point> data;

    private Matrix matrix;

    private Button btnStart;
    private Button btnClear;


    public MainFrame(MainController controller, Collection<Point> data) {
        this.controller = controller;
        this.data = data;

        initUI();
    }

    public void setData(Collection<Point> data) {
        this.data = data;
    }

    //Создание и инициализация компонентов интерфейса
    private void initUI() {
        setTitle("Life");

        setSize(Main.getProperty(Main.MAIN_WIDTH), Main.getProperty(Main.MAIN_HEIGHT));

        setMinimumSize(new Dimension(300, 300));

        //Установить размер поля по-умолчанию
        int size = Main.getProperty(Main.FIELD_SIZE);
        fieldSize = new Dimension(size, size);

        setResizable(true);

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

        matrix = new Matrix(fieldSize.width, fieldSize.height, data);
        matrix.addCellClickListener(this);

        add(matrix, BorderLayout.CENTER);

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
                matrix.setColumns(fieldSize.width);
                matrix.setRows(fieldSize.height);
                matrix.repaint();
                break;
            case DATA_CHANGED:
                matrix.repaint();
                break;
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

    @Override
    public void cellClicked(int column, int row) {
        controller.onCellClick(new Point(column, row));
    }
}
