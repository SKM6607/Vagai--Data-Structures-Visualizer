package Windows;
import Windows.interfaces.DefaultWindowsInterface;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class LoadingPage extends JPanel implements DefaultWindowsInterface {
    public static final Font newFont;
    private static final JButton startButton;
    private static final JLabel startLabel;
    public JButton returnControlOfLoadButton(){
        return startButton;
    }
    static {
        startLabel=new JLabel("Vagai - An Algorithms Demonstrator");
        try {
            newFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/PressStart2P-Regular.ttf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        startButton=new JButton("Start Learning!");
    }
    public LoadingPage() {
        setLayout(null);
        setBackground(themeColorBG);
        setPreferredSize(new Dimension(1800,1000));
        startLabel.setBounds(width/6,height/6,1200,300);
        startLabel.setForeground(foreGroundBG);
        startLabel.setFont(newFont.deriveFont(32f));
        startButton.setFont(newFont.deriveFont(24.2f));
        startButton.setBounds(width/2 -200,height/2,400,50);
        startButton.setForeground(themeColorBG);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(startLabel);
        add(startButton);
    }
}
