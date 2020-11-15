package com.epam.dmitrii_elagin.life.view;



import com.epam.dmitrii_elagin.life.controller.MainController;
import com.epam.dmitrii_elagin.life.model.Model;
import com.epam.dmitrii_elagin.life.model.ModelEvent;
import com.epam.dmitrii_elagin.life.model.ModelListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;


public class MainFrame extends Frame implements ActionListener, ModelListener {

    private static final int WIDTH=800;
    private static final int HEIGHT=800;
    private static final int MIN_WIDTH=600;
    private static final int MIN_HEIGHT=300;

    private MainController controller;

    private Dimension fieldSize;

    private Set<Point> data;


    private Button btnStart;
    private Button btnStop;
    private Button btnClear;

    //Матрица компонентов для отображения бактерий
    private Component[][] matrix;

    //Панель для размещения матрицы
    private Panel pnlMatrix;

    private GridLayout gridLayout;



    public MainFrame(MainController controller) {

        this.controller=controller;

        //Установка размеров поля по-умолчанию
        fieldSize=new Dimension(Model.WIDTH,Model.HEIGHT);


        initUI();
    }

    //Создание и инициализация компонентов интерфейса
    private void initUI() {
        setTitle("Life");

        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH,MIN_HEIGHT));

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

        gridLayout=new GridLayout();

        pnlMatrix=new Panel(gridLayout);
        pnlMatrix.setBackground(Color.GRAY);

        add(pnlMatrix,BorderLayout.CENTER);
        createMatrix();

        createMenu();

        createControlPanel();
    }

    private void createMenu() {
        Font font=new Font(Font.SANS_SERIF,Font.PLAIN,20);

        MenuBar menuBar=new MenuBar();
        Menu menu=new Menu("Menu");
        menu.setFont(font);

        MenuItem miSettings=new MenuItem("Settings");
        miSettings.addActionListener(this);
        MenuItem miExit=new MenuItem("Exit");
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
        pnlControl.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));

        //Создать шрифт для кнопок управления
        Font font = new Font(Font.SANS_SERIF,Font.PLAIN,26);

        pnlControl.setFont(font);

        //Создать размерность конопок управления
        Dimension dimension=new Dimension(150,30);

        btnStart=new Button("Start");
        btnStart.setPreferredSize(dimension);
        btnStart.addActionListener(this);
        pnlControl.add(btnStart);

        btnStop=new Button("Stop");
        btnStop.setPreferredSize(dimension);
        btnStop.addActionListener(this);
        pnlControl.add(btnStop);

        btnClear=new Button("Clear");
        btnClear.setPreferredSize(dimension);
        btnClear.addActionListener(this);
        pnlControl.add(btnClear);
        add(pnlControl,BorderLayout.SOUTH);
    }

    private void createMatrix() {
        matrix=new Button[fieldSize.height][fieldSize.width];

        gridLayout.setColumns(fieldSize.width);
        gridLayout.setRows(fieldSize.height);
        pnlMatrix.removeAll();

        for(int i=0; i<fieldSize.height; i++){
            for(int j=0; j<fieldSize.width; j++){
                Button button=new Button();
                button.setBackground(Color.darkGray);
                matrix[i][j]=new Button();
                pnlMatrix.add(button);
            }

        }

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

        switch (event.getEventType()){
            case STATE_CHANGED:
               setButtonsState(event.getState());
                break;
            case FIELD_SIZE_CHANGED:
               fieldSize = event.getSize();
                System.out.println("height = "+fieldSize.height+" width = "+fieldSize.width);
               createMatrix();

        }
    }

    private void setButtonsState(Model.State state) {
        if(state == Model.State.RUNNING) {
            btnStart.setEnabled(false);
            btnStart.setLabel("Running...");
            btnClear.setEnabled(false);
        } else if(state == Model.State.STOPPED){
            btnStart.setEnabled(true);
            btnStart.setLabel("Start");
            btnClear.setEnabled(true);
        }
    }
}
