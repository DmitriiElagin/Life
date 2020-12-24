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

    private final int resolution;


    public SettingsDialog(Frame parent, SettingsController controller,
                          Dimension fieldSize, int lifeSpan,
                          int tightness, int loneliness) {
        super(parent, true);
        this.fieldSize = fieldSize;
        this.lifeSpan = lifeSpan;
        this.controller = controller;
        this.tightness=tightness;
        this.loneliness = loneliness;

        resolution = Toolkit.getDefaultToolkit().getScreenResolution();

        initUI();
    }

    //Инициализация и настройка компонентов интерфейса
    private void initUI() {
        setTitle("Settings");

        //Установить окно по центру экрана
        setLocationRelativeTo(null);

        setSize(resolution * 2, resolution * 2+100);

        setResizable(false);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, resolution / 9));

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.onCancelAction(SettingsDialog.this);

            }
        });

        add(createLabelPanel());
        add(createTextFieldPanel());

        lblError=new Label();
        lblError.setPreferredSize(new Dimension(getWidth()-20, resolution/6));
        lblError.setForeground(Color.red);
        add(lblError);

        btnOk = new Button("OK");
        btnOk.setPreferredSize(new Dimension(resolution, resolution / 4));
        btnOk.addActionListener(this);
        add(btnOk);

    }

    private Panel createTextFieldPanel() {
        Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, resolution / 6);
        panel.setPreferredSize(new Dimension(resolution - 30, resolution + 100));
        panel.setFont(font);

        tfWidth = new TextField("" + fieldSize.width, TF_COLUMNS);
        addComponent(panel, tfWidth, null);

        tfHeight = new TextField("" + fieldSize.height, TF_COLUMNS);
        addComponent(panel, tfHeight, null);

        tfLifeSpan = new TextField("" + lifeSpan, TF_COLUMNS);
        addComponent(panel, tfLifeSpan, null);

        tfTightness = new TextField("" + tightness, TF_COLUMNS);
        addComponent(panel, tfTightness, null);

        tfLoneliness = new TextField("" + loneliness, TF_COLUMNS);
        addComponent(panel, tfLoneliness, null);

        for(Component component:panel.getComponents()) {
            component.addKeyListener(this);
            ((TextField)component).addTextListener(this);
        }

        return panel;
    }

    public void showError(String message) {
        lblError.setText(message);
    }

    private Panel createLabelPanel() {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, resolution / 6);

        Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        panel.setPreferredSize(new Dimension(resolution, resolution + 100));
        panel.setFont(font);

        Dimension dimension = new Dimension(resolution, resolution / 4);

        addComponent(panel, new Label("Width:"), dimension);
        addComponent(panel, new Label("Height:"), dimension);
        addComponent(panel, new Label("Life Span:"), dimension);
        addComponent(panel, new Label("Tightness:"), dimension);
        addComponent(panel, new Label("Loneliness:"), dimension);

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
