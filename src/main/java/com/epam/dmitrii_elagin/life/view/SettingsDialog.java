package com.epam.dmitrii_elagin.life.view;


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

    private int tightness;

    private int loneliness;

    //Поле ввода ширины игрового поля
    private TextField tfWidth;

    //Поле ввода высоты игрового поля
    private TextField tfHeight;

    //Поле ввода максимального возраста колонии
    private TextField tfLifeSpan;

    //Поле ввода параметра "тесноты"
    private TextField tfTightness;

    //Поле ввода параметра "одиночества"
    private TextField tfLoneliness;

    private Label lblError;

    private Button btnOk;

    public SettingsDialog(Frame parent, SettingsController controller,
                          Dimension fieldSize, int lifeSpan,
                          int tightness, int loneliness) {
        super(parent, true);
        this.fieldSize = fieldSize;
        this.lifeSpan = lifeSpan;
        this.controller = controller;
        this.tightness=tightness;
        this.loneliness = loneliness;

        initUI();
    }

    //Инициализация и настройка компонентов интерфейса
    private void initUI() {
        GridBagLayout layout = new GridBagLayout();
        setTitle("Settings");

        //Установить окно по центру экрана
        setLocationRelativeTo(null);

        setSize(200, 320);

        setResizable(false);

        setLayout(layout);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.onCancelAction(SettingsDialog.this);
            }
        });

        createLabels();
        createTextFieldS();
        createErrorLabel();
        createOKButton();
    }

    private void createOKButton() {
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = 2;
        constraints.gridy = 6;
        constraints.ipady = 5;
        constraints.ipadx = 100;
        constraints.weighty = 0.8;


        btnOk = new Button("OK");

        btnOk.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        btnOk.addActionListener(this);
        add(btnOk, constraints);
    }

    private void createErrorLabel() {
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.ipady = 20;
        constraints.ipadx = 100;

        lblError = new Label();
        lblError.setForeground(Color.red);

        add(lblError, constraints);
    }


    private void createTextFieldS() {
        final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

        final GridBagConstraints constraints = new GridBagConstraints();

        final TextField[] fields = new TextField[5];

        constraints.gridx = 1;
        constraints.weighty = 0.2;
        tfWidth = new TextField("" + fieldSize.width, TF_COLUMNS);
        fields[0] = tfWidth;


        tfHeight = new TextField("" + fieldSize.height, TF_COLUMNS);
        fields[1] = tfHeight;

        tfLifeSpan = new TextField("" + lifeSpan, TF_COLUMNS);
        fields[2] = tfLifeSpan;

        tfTightness = new TextField("" + tightness, TF_COLUMNS);
        fields[3] = tfTightness;

        tfLoneliness = new TextField("" + loneliness, TF_COLUMNS);
        fields[4] = tfLoneliness;

        for (int y = 0; y < 5; y++) {
            constraints.gridy = y;
            fields[y].addKeyListener(this);
            fields[y].addTextListener(this);
            fields[y].setEditable(true);
            addComponent(fields[y], constraints, font);
        }
    }

    public void showError(String message) {
        lblError.setText(message);
    }

    private void addComponent(Component component, GridBagConstraints constraints, Font font) {
        component.setFont(font);
        add(component, constraints);
    }

    private void createLabels() {
        GridBagConstraints constraints = new GridBagConstraints();
        Font font = new Font(Font.DIALOG, Font.BOLD, 16);

        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(new Label("Width:"), constraints, font);

        constraints.gridy = 1;
        addComponent(new Label("Height:"), constraints, font);

        constraints.gridy = 2;
        addComponent(new Label("Life span:"), constraints, font);

        constraints.gridy = 3;
        addComponent(new Label("Tightness:"), constraints, font);

        constraints.gridy = 4;
        addComponent(new Label("Loneliness:"), constraints, font);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        fieldSize.height = Integer.parseInt(tfHeight.getText());
        fieldSize.width = Integer.parseInt(tfWidth.getText());

        lifeSpan = Integer.parseInt(tfLifeSpan.getText());

        tightness = Integer.parseInt(tfTightness.getText());
        loneliness = Integer.parseInt(tfLoneliness.getText());

        controller.onOkAction(fieldSize, lifeSpan, tightness, loneliness, this);
    }

    @Override
    public void textValueChanged(TextEvent e) {

        lblError.setText("");

        //Блокировать кнопку, если есть пустые поля
        boolean enabled = !(tfWidth.getText().isEmpty() ||
                tfHeight.getText().isEmpty() ||
                tfLifeSpan.getText().isEmpty() ||
                tfTightness.getText().isEmpty() ||
                tfLoneliness.getText().isEmpty());

        btnOk.setEnabled(enabled);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        //Вводить только цифры
        if (c != KeyEvent.VK_BACK_SPACE && !Character.isDigit(c)) {
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
