package utils.window;

import sound.BackgroundMusic;

import javax.swing.*;
import java.awt.*;

import static main.interfaces.MacroInterface.*;

/**
 * The <code>Main</code> class is the entry point <h5 color="blue">VAGAI Algorithms Visualizer</h4>
 */
public final class MainWindow extends JFrame {
    private static final Thread backgroundMusicThread = new Thread(new BackgroundMusic("src/resources/Granius.wav"));
    private static final CardLayout cardLayout = new CardLayout();
    private static final String WINDOW_TITLE="VAGAI- Algorithms Visualizer";
    private MainWindow() {
        super(WINDOW_TITLE);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundMusicThread.start();
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        MainCardPanel mainPanel = MainCardPanel.getInstance(this);
        add(mainPanel, BorderLayout.CENTER);
        setLayout(cardLayout);
        setVisible(true);
    }
    public static void startVAGAI(){
        new MainWindow();
    }
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            jFrame = new JFrame("VAGAI");
            jFrame.setSize(width, height);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setBackground(backgroundColor);
            jFrame.setForeground(foregroundColor);
            backgroundMusicThread.start();
            jFrame.add(cardPanel, BorderLayout.CENTER);
            jFrame.setLayout(cardLayout);
            jFrame.setJMenuBar(menuBarMain);
            cardLayout.show(cardPanel, DEFAULT);
            jFrame.setVisible(true);
        });
    }*/
}