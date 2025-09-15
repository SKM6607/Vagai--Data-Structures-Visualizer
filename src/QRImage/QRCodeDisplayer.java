package QRImage;

import Windows.Interfaces.DefaultWindowsInterface;

import javax.swing.*;
import java.awt.*;

public class QRCodeDisplayer extends JDialog implements DefaultWindowsInterface {
    private final JLabel qrLabel;
    public QRCodeDisplayer(Window parent){
        setTitle("Scan to Visit!");
        ImageIcon qrImg=new ImageIcon("ReadmeImages/GitHubVisai.png");
        Image scaledImage=qrImg.getImage().getScaledInstance(256,256,Image.SCALE_SMOOTH);
        qrLabel=new JLabel(new ImageIcon(scaledImage));
        add(qrLabel);
        setResizable(false);
        qrLabel.setPreferredSize(new Dimension(256,256));
        setSize(300,300);
        qrLabel.setBackground(themeColorBG);
        setLocationRelativeTo(parent);
    }
}
