package main.pages;

import org.jetbrains.annotations.NotNull;
import utils.main.MainCardPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static main.interfaces.DefaultWindowsInterface.*;
import static main.interfaces.MacroInterface.SORTING_ALGORITHMS;

public class HomePage extends JPanel {
    public static final Font newFont;
    private static final JButton startButton;
    private static final JLabel startLabel;
    private static HomePage singleton;
    private Runnable onStartTask;
    static {
        startLabel = new JLabel("Vagai - An Algorithms Demonstrator");
        try {
            newFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/PressStart2P.ttf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        startButton = new JButton("START LEARNING");
    }
    private void onStart(MainCardPanel parent){
        parent.cardLayout.show(parent, SORTING_ALGORITHMS);
        onStartTask.run();
    }
    private HomePage(JPanel parent) {
        setLayout(null);
        setBackground(backgroundColor);
        startButton.addActionListener(_ -> onStart((MainCardPanel) parent));
        setPreferredSize(new Dimension(1800, 1000));
        startLabel.setBounds(width / 6, height / 6, 1200, 300);
        startLabel.setForeground(foregroundColor);
        startLabel.setFont(newFont.deriveFont(32f));
        startButton.setFont(newFont.deriveFont(24.2f));
        startButton.setBounds(width / 2 - 200, height / 2, 400, 50);
        startButton.setForeground(backgroundColor);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(startLabel);
        add(startButton);
    }

    public static @NotNull HomePage getInstance(JPanel parent) {
        return (singleton == null) ? singleton = new HomePage(parent) : singleton;
    }

    public void setOnStartTask(Runnable onStartTask) {
        this.onStartTask = onStartTask;
    }
}
