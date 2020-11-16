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

    //Максимальная ширина и высота поля
    private static final int MAX_SIZE=30;

    private final SettingsController controller;

    //Размер поля
    private Dimension size;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Поле ввода ширины игрового поля
    private TextField tfWidth;

    //Поле ввода высоты игрового поля
    private TextField tfHeight;

    //Поле ввода максимального возраста колонии
    private TextField tfLifeSpan;

    private Button btnOk;


    public SettingsDialog(Frame parent, SettingsController controller, int width, int height, int lifeSpan) {
        super(parent,true);
        size=new Dimension(width,height);
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


        tfWidth=new TextField(""+size.width,TF_COLUMNS);
        tfWidth.addTextListener(this);
        tfWidth.addKeyListener(this);
        addComponent(panel,tfWidth, null);
        tfHeight=new TextField(""+size.height,TF_COLUMNS);
        tfHeight.addKeyListener(this);
        tfHeight.addTextListener(this);
        addComponent(panel,tfHeight, null);
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

        addComponent(panel, new Label("Width:"),dimension);
        addComponent(panel,new Label("Height:"),dimension);
        addComponent(panel,new Label("Life Span:"),dimension);

        return panel;

    }



    private void addComponent(Container container,Component comp, Dimension dimen) {
        comp.setPreferredSize(dimen);
        container.add(comp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        size.height=Integer.parseInt(tfHeight.getText());
        size.width=Integer.parseInt(tfWidth.getText());
        lifeSpan=Integer.parseInt(tfLifeSpan.getText());
        controller.onOkAction(size,lifeSpan);
        dispose();
    }

    @Override
    public void textValueChanged(TextEvent e) {
        boolean enabled;

        //Запретить кнопку, если значения полей выходят за пределы разрешенных или отсутствуют
        try {
           int w=Integer.parseInt(tfWidth.getText());
           int h=Integer.parseInt(tfHeight.getText());

           enabled=!(w < MIN_SIZE || h < MIN_SIZE) &&
                   !(w>MAX_SIZE||h>MAX_SIZE)&&
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
