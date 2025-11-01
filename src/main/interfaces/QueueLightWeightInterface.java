package main.interfaces;

import javax.swing.*;
import java.awt.*;

import static main.abstractClasses.ComponentUtilities.createButton;

public interface QueueLightWeightInterface extends DefaultWindowsInterface {
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    default JButton getEnqueueButton() {
        return createButton("ENQUEUE", new Color(0, 18, 121), font);
    }

    default JButton getDequeueButton() {
        return createButton("DEQUEUE", Color.BLACK, font);
    }

    default JLabel setInfoLabel(String infoLabelText) {
        JLabel infoLabel = new JLabel(infoLabelText, SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(new Color(0, 18, 121));
        return infoLabel;
    }
}
