package com.epam.dmitrii_elagin.life.view;

import com.epam.dmitrii_elagin.life.controller.MainController;
import com.epam.dmitrii_elagin.life.simulator.Simulator;
import com.epam.dmitrii_elagin.life.simulator.SimulatorEvent;
import com.epam.dmitrii_elagin.life.view.matrix.BacteriaMatrix;
import com.epam.dmitrii_elagin.life.view.matrix.CellClickListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;


public class MainFrame extends Frame implements ActionListener, SimulatorListener, CellClickListener {

    private final MainController controller;
    private BacteriaMatrix matrix;

    private Button btnStart;
    private Button btnClear;

    private final int resolution;


    public MainFrame(MainController controller, Collection<Point> data, Dimension fieldSize) {
        this.controller = controller;

        resolution = Toolkit.getDefaultToolkit().getScreenResolution();

        initUI(data, fieldSize);
    }


    //Создание и инициализация компонентов интерфейса
    private void initUI(Collection<Point> data, Dimension fieldSize) {
        setTitle("Life");

        setSize(resolution * 10, resolution * 10);

        setMinimumSize(new Dimension(resolution * 5, resolution * 5));

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

        matrix = new BacteriaMatrix(fieldSize.width, fieldSize.height, data);
        matrix.addCellClickListener(this);

        add(matrix, BorderLayout.CENTER);

        createMenu();

        createControlPanel();
    }

    private void createMenu() {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, resolution /7);

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
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, resolution /5);

        pnlControl.setFont(font);

        //Создать размерность конопок управления
        Dimension dimension = new Dimension(resolution, resolution/4);

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
    public void handleEvent(SimulatorEvent event) {

        switch (event.getEventType()) {
            case STATE_CHANGED:
                setButtonsState(event.getState());
                break;
            case FIELD_SIZE_CHANGED:
                matrix.setColumns(event.getSize().width);
                matrix.setRows(event.getSize().height);
                matrix.repaint();
                break;
            case DATA_CHANGED:
                matrix.repaint();
                break;
        }
    }

    private void setButtonsState(Simulator.State state) {
        if (state == Simulator.State.RUNNING) {
            btnStart.setEnabled(false);
            btnStart.setLabel("Simulation...");
            btnClear.setEnabled(false);
        } else if (state == Simulator.State.STOPPED) {
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
