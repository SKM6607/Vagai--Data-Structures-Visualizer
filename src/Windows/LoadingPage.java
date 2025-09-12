package Windows;

import Windows.Interfaces.DefaultWindowsInterface;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadingPage extends JPanel implements DefaultWindowsInterface {
    public final Font newFont;
    private final JButton startButton;
    public JButton returnControlOfLoadButton(){
        return startButton;
    }
    public LoadingPage() {
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        setLayout(null);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1800,1000));
        try {
            newFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/PressStart2P-Regular.ttf"));
            repaint();
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        startButton=new JButton("Start Learning!");
        startButton.setFont(newFont.deriveFont(16f));
        startButton.setBounds(width/2 -200,height/2,400,50);
        startButton.setBorderPainted(true);
        add(startButton);
    }
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setColor(Color.WHITE);
        g.setFont(newFont.deriveFont(36f));
        g.drawString("VAGAI- Algorithms Demonstrator", getWidth()/5, getHeight()/6);
    }
}
