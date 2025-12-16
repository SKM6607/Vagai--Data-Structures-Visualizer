package utils.mainWindow;

import sound.BackgroundMusic;

import javax.swing.*;
import java.awt.*;

import static main.interfaces.MacroInterface.*;

public final class MainWindow extends JFrame {
    private static final Thread backgroundMusicThread = new Thread(BackgroundMusic.getInstance());
    private static final CardLayout cardLayout = new CardLayout();
    private static final String WINDOW_TITLE = "VAGAI- Algorithms Visualizer";
    private static MainWindow singleton = null;

    private MainWindow() {
        super(WINDOW_TITLE);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundMusicThread.start();
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        MainCardPanel mainPanel = MainCardPanel.getInstance();
        MainMenuBar menuBar = MainMenuBar.getInstance(mainPanel);
        setJMenuBar(menuBar);
        mainPanel.setMenuBarForAppearance(menuBar);
        add(mainPanel, BorderLayout.CENTER);
        setLayout(cardLayout);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This Method is the entry point.
     * <br><i>Initiates the class </i><code>MainWindow</code>
     *
     * @author Sri Koushik JK
     * @see MainWindow
     * @see MainCardPanel
     * @see MainMenuBar
     * @since v0.0.4
     */
    public static void startVAGAI() {
        if (singleton == null) singleton = new MainWindow();
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