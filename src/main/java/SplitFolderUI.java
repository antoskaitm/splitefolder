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
        panel.setLayout(new GridLayout(6, 2));
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

        panel.add(new JLabel());
        panel.add(btnStart);

        add(panel);
        setVisible(true);

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
}
