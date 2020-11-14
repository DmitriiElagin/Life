package com.epam.dmitrii_elagin.life.view;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainFrame extends Frame implements ActionListener {

    private static final int WIDTH=800;
    private static final int HEIGHT=800;
    private static final int MIN_WIDTH=600;
    private static final int MIN_HEIGHT=300;


    private Button btnStart;
    private Button btnStop;
    private Button btnClear;
    private Button btnSettings;

    private Canvas canvas;



    public MainFrame() {
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

        canvas=new Canvas();
        canvas.setBackground(Color.GRAY);
        add(canvas,BorderLayout.CENTER);

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


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Clear":
                System.out.println("Очистить");
                break;
            case "Settings":
                System.out.println("Настройки");
                break;
            case "Start":
                System.out.println("Старт");
                break;
            case "Stop":
                System.out.println("Стоп");
                break;
            case "Exit":
                dispose();
                break;

        }
    }
}
