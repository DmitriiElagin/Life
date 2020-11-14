package com.epam.dmitrii_elagin.life.view;



import com.epam.dmitrii_elagin.life.controller.SettingsController;

import java.awt.*;
import java.awt.event.*;

//Окно настроек приложения
public class SettingsDialog extends Dialog implements ActionListener, TextListener, KeyListener {

    //Ширина фрейма
    private static final int WIDTH=400;

    //Высота фрейма
    private static final int HEIGHT=300;

    //Колл-во колонок текстовых полей
    private static final int TF_COLUMNS=3;

    //Минимальная ширина и высота поля
    private static final int MIN_SIZE=3;
    private final SettingsController controller;


    //Ширина поля
    private int columns;

    //Высота поля
    private int rows;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Поле ввода ширины игрового поля
    private TextField tfColumns;

    //Поле ввода высоты игрового поля
    private TextField tfRows;

    //Поле ввода максимального возраста колонии
    private TextField tfLifeSpan;

    private Button btnOk;


    public SettingsDialog(Frame parent, SettingsController controller, int columns, int rows, int lifeSpan) {
        super(parent,true);
        this.columns=columns;
        this.rows=rows;
        this.lifeSpan=lifeSpan;
        this.controller=controller;

        initUI();
    }

    //Инициализация и настройка компонентов интерфейса
    private void initUI() {
        setTitle("Settings");

        //Установить окно по центру экрана
        setLocationRelativeTo(null);

        setSize(WIDTH,HEIGHT);
        setResizable(false);

        setLayout(new FlowLayout(FlowLayout.CENTER,50,15));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dispose();
            }
        });

        add(createLabelPanel());
        add(createTextFieldPanel());

        btnOk=new Button("OK");
        btnOk.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        btnOk.setPreferredSize(new Dimension(150,35));
        btnOk.addActionListener(this);
        add(btnOk);

    }

    private Panel createTextFieldPanel() {
        Panel panel=new Panel(new FlowLayout(FlowLayout.CENTER,0,15));
        Font font =new Font(Font.SANS_SERIF,Font.PLAIN,24);
        panel.setPreferredSize(new Dimension(100,170));
        panel.setFont(font);


        tfColumns=new TextField(""+columns,TF_COLUMNS);
        tfColumns.addTextListener(this);
        tfColumns.addKeyListener(this);
        addComponent(panel,tfColumns, null);
        tfRows=new TextField(""+rows,TF_COLUMNS);
        tfRows.addKeyListener(this);
        tfRows.addTextListener(this);
        addComponent(panel,tfRows, null);
        tfLifeSpan=new TextField(""+lifeSpan,TF_COLUMNS);
        tfLifeSpan.addKeyListener(this);
        tfLifeSpan.addTextListener(this);
        addComponent(panel,tfLifeSpan, null);

        return panel;
    }

    private Panel createLabelPanel() {
        Font font =new Font(Font.SANS_SERIF,Font.BOLD,24);

        Panel panel=new Panel(new FlowLayout(FlowLayout.CENTER,0,20));
        panel.setPreferredSize(new Dimension(130,170));
        panel.setFont(font);

        Dimension dimension=new Dimension(110,30);

        addComponent(panel, new Label("Columns:"),dimension);
        addComponent(panel,new Label("Rows:"),dimension);
        addComponent(panel,new Label("Max age:"),dimension);

        return panel;

    }



    private void addComponent(Container container,Component comp, Dimension dimen) {
        comp.setPreferredSize(dimen);
        container.add(comp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        rows=Integer.parseInt(tfRows.getText());
        columns=Integer.parseInt(tfColumns.getText());
        lifeSpan=Integer.parseInt(tfLifeSpan.getText());
        controller.onOkAction(rows,columns,lifeSpan);
        dispose();
    }

    @Override
    public void textValueChanged(TextEvent e) {
        boolean enabled;

        //Запретить кнопку, если значения полей ниже минимальных или отсутствуют
        try {
           int r=Integer.parseInt(tfColumns.getText());
           int c=Integer.parseInt(tfRows.getText());

           enabled=!(r < MIN_SIZE || c < MIN_SIZE) &&
                   !tfLifeSpan.getText().isEmpty();
        }
        catch (NumberFormatException ex) {
           enabled=false;
        }
        btnOk.setEnabled(enabled);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c=e.getKeyChar();

        //Вводить только цифры
        if(!Character.isDigit(c)){
            e.setKeyChar('\0');
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
