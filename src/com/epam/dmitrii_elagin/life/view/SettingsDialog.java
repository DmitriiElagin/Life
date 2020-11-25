package com.epam.dmitrii_elagin.life.view;


import com.epam.dmitrii_elagin.life.Main;
import com.epam.dmitrii_elagin.life.controller.SettingsController;

import java.awt.*;
import java.awt.event.*;

//Окно настроек приложения
public class SettingsDialog extends Dialog implements ActionListener, TextListener, KeyListener {

    //Колл-во колонок текстовых полей
    private static final int TF_COLUMNS = 3;

    private final SettingsController controller;

    //Размер поля
    private final Dimension fieldSize;

    //Продолжительность жизни колонии
    private int lifeSpan;

    //Поле ввода ширины игрового поля
    private TextField tfWidth;

    //Поле ввода высоты игрового поля
    private TextField tfHeight;

    //Поле ввода максимального возраста колонии
    private TextField tfLifeSpan;

    //Минимальный размер поля
    private int minSize;

    //Максимальный размер поля
    private int maxSize;

    private Button btnOk;

    private final int resolution;


    public SettingsDialog(Frame parent, SettingsController controller,
                          Dimension fieldSize, int lifeSpan) {
        super(parent, true);
        this.fieldSize = fieldSize;
        this.lifeSpan = lifeSpan;
        this.controller = controller;

        resolution =Toolkit.getDefaultToolkit().getScreenResolution();

        initUI();
    }

    //Инициализация и настройка компонентов интерфейса
    private void initUI() {
        setTitle("Settings");

        //Установить окно по центру экрана
        setLocationRelativeTo(null);

        setSize(resolution*3, resolution*2-10);

        minSize = Main.getProperty(Main.MIN_SIZE);
        maxSize = Main.getProperty(Main.MAX_SIZE);

        setResizable(false);

        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.onCancel();
                dispose();
            }
        });

        add(createLabelPanel());
        add(createTextFieldPanel());

        btnOk = new Button("OK");
        btnOk.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,resolution/7));
        btnOk.setPreferredSize(new Dimension(resolution, resolution/4));
        btnOk.addActionListener(this);
        add(btnOk);

    }

    private Panel createTextFieldPanel() {
        Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, resolution/6);
        panel.setPreferredSize(new Dimension(resolution-30, resolution));
        panel.setFont(font);

        tfWidth = new TextField("" + fieldSize.width, TF_COLUMNS);
        tfWidth.addTextListener(this);
        tfWidth.addKeyListener(this);
        addComponent(panel, tfWidth, null);
        tfHeight = new TextField("" + fieldSize.height, TF_COLUMNS);
        tfHeight.addKeyListener(this);
        tfHeight.addTextListener(this);
        addComponent(panel, tfHeight, null);
        tfLifeSpan = new TextField("" + lifeSpan, TF_COLUMNS);
        tfLifeSpan.addKeyListener(this);
        tfLifeSpan.addTextListener(this);
        addComponent(panel, tfLifeSpan, null);

        return panel;
    }

    private Panel createLabelPanel() {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, resolution/6);

        Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        panel.setPreferredSize(new Dimension(resolution, resolution));
        panel.setFont(font);

        Dimension dimension = new Dimension(resolution, resolution/4);

        addComponent(panel, new Label("Width:"), dimension);
        addComponent(panel, new Label("Height:"), dimension);
        addComponent(panel, new Label("Life Span:"), dimension);

        return panel;

    }


    private void addComponent(Container container, Component comp, Dimension dimen) {
        comp.setPreferredSize(dimen);
        container.add(comp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fieldSize.height = Integer.parseInt(tfHeight.getText());
        fieldSize.width = Integer.parseInt(tfWidth.getText());
        lifeSpan = Integer.parseInt(tfLifeSpan.getText());
        controller.onOkAction(fieldSize, lifeSpan);
        dispose();
    }

    @Override
    public void textValueChanged(TextEvent e) {
        boolean enabled;

        //Запретить кнопку, если значения полей выходят за пределы разрешенных или отсутствуют
        try {
            int w = Integer.parseInt(tfWidth.getText());
            int h = Integer.parseInt(tfHeight.getText());

            enabled = !(w < minSize || h < minSize) &&
                    !(w > maxSize || h > maxSize) &&
                    !tfLifeSpan.getText().isEmpty();
        } catch (NumberFormatException ex) {
            enabled = false;
        }
        btnOk.setEnabled(enabled);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        //Вводить только цифры
        if (!Character.isDigit(c)) {
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
