package ru.ton_ton.splitFolder.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ton_ton.splitFolder.logging.ui.LogAppenderUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Consumer;

public class SplitFolderUI extends JFrame {
    private JTextField txtInputDate;
    private JButton btnSelectSource, btnSelectTarget, btnStart;
    private JFileChooser fileChooser;

    private LocalDateTime time;

    public SplitFolderUI(Consumer<StartSplitFolderAction> start) {

        StartSplitFolderAction startSplitFolderAction = new StartSplitFolderAction();
        setTitle("Разбить папку на папки фиксированного размера");
        setSize(800, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtInputDate = new JTextField();

        btnSelectSource = new JButton("Выбрать");
        btnSelectSource.addActionListener(e -> {
            int result = fileChooser.showDialog(this, "Выбрать");
            if (result == JFileChooser.APPROVE_OPTION) {
                File sourceDir = fileChooser.getSelectedFile();
                startSplitFolderAction.setSourceDir(sourceDir);
                btnSelectSource.setText(sourceDir.getAbsolutePath());
            }
        });

        btnSelectTarget = new JButton("Выбрать");
        btnSelectTarget.addActionListener(e -> {
            int result = fileChooser.showDialog(this, "Выбрать");
            if (result == JFileChooser.APPROVE_OPTION) {
                File destinationDir = fileChooser.getSelectedFile();
                startSplitFolderAction.setDestinationDir(destinationDir);
                btnSelectTarget.setText(destinationDir.getAbsolutePath());
            }
        });


        JPanel panel = new JPanel();
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(2, 1));

        panel.setLayout(new GridLayout(6, 2));
        main.add(panel);
        panel.add(new JLabel("Выберите папку источник:"));
        panel.add(btnSelectSource);
        panel.add(new JLabel("Выберите папку назначения:"));
        panel.add(btnSelectTarget);
        panel.add(new JLabel("Введите дату по которой отсекаются старые файлы"));
        panel.add(txtInputDate);


        String[] options = {"10", "4400", "700", "1000", "2000", "3000"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.addActionListener(e -> {
            Long size = Long.parseLong((String) Objects.requireNonNull(dropdown.getSelectedItem()));
            startSplitFolderAction.setMaxSize(size);
        });
        panel.add(new JLabel("ВВедите размер файла"));
        panel.add(dropdown);

        btnStart = new JButton("Отправить");
        btnStart.addActionListener(e -> {
            start.accept(startSplitFolderAction);
        });


        // Cоздание многострочных полей
        JTextArea area1 = new JTextArea("", 8, 10);
        // Шрифт и табуляция
        area1.setFont(new Font("Dialog", Font.PLAIN, 14));
        //area1.setTabSize(10);

        // Параметры переноса слов
        //area1.setLineWrap(true);
        //area1.setWrapStyleWord(true);
        main.add(new JScrollPane(area1));
        LogAppenderUI.addLogListener((e)->area1.append(e.getMessage().getFormattedMessage()+"\n"));


        panel.add(new JLabel());
        panel.add(btnStart);
        add(main);
        // Добавим поля в окно
        setVisible(true);

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       /* OutputUI.add("for ui");
        OutputUI.add("for ui");
        OutputUI.add("for ui");
        logger.trace("trace");
        logger.info("info");
        logger.debug("debug");
        logger.error("error");*/
    }
}
