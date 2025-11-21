import sound.BackgroundMusic;
import static main.interfaces.MacroInterface.*;
import javax.swing.*;
import java.awt.*;
/**
 *The <code>Main</code> class is the entry point <h5 color="blue">VAGAI Algorithms Visualizer</h4>
*/
public final class Main{
    private static final Thread backgroundMusicThread = new Thread(new BackgroundMusic("src/Sound/Granius.wav"));
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel cardPanel = new JPanel(cardLayout);
    private static JFrame jFrame;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            jFrame = new JFrame("VAGAI");
            jFrame.setSize(width,height);
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
    }
}