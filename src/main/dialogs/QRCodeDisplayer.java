package main.dialogs;

import main.interfaces.DefaultWindowsInterface;

import javax.swing.*;
import java.awt.*;

public class QRCodeDisplayer extends JDialog {

    public QRCodeDisplayer(Window parent) {
        super(parent);
        setTitle("Scan to Visit!");

        // Note: Image path might need adjustment depending on execution context, but
        // keeping as is.
        ImageIcon qrImg = new ImageIcon("ReadmeImages/GitHubVisai.png");
        Image scaledImage = qrImg.getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH);

        JLabel qrLabel = new JLabel(new ImageIcon(scaledImage));
        setResizable(false);
        qrLabel.setPreferredSize(new Dimension(256, 256));
        setSize(new Dimension(300, 300));

        // DefaultWindowsInterface.backgroundColor must be accessible
        qrLabel.setBackground(DefaultWindowsInterface.backgroundColor);
        qrLabel.setOpaque(true); // Needed for background color to show on JLabel

        add(qrLabel);
        setLocationRelativeTo(parent);
    }
}
