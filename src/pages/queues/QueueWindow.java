package pages.queues;

import pages.interfaces.QueueLightWeightInterface;

import javax.swing.*;
import java.awt.*;

import static pages.abstractClasses.ComponentUtilities.createTextField;

/**
 * QueueWindow is an abstract sealed class, that enables the inheritors to use the children classes of Queue class
 *
 * @see Queue
 * @since v0.0.3
 */
public sealed abstract class QueueWindow<T extends Queue>
        extends JPanel
        implements QueueLightWeightInterface
        permits PriorityQueueWindow, SimpleQueueWindow, CircularQueueWindow {
    T visualQueue;
    JLabel infoLabel;
    JTextField textField = createTextField(font, themeColorBG);

    JPanel getPanel() {
        JPanel speedPanel = new JPanel(new BorderLayout());
        JLabel speedLabel = new JLabel("ANIMATION SPEED", SwingConstants.CENTER);
        JSlider speedSlider = new JSlider();
        speedPanel.setBackground(new Color(0, 18, 121));
        speedLabel.setForeground(Color.WHITE);
        speedLabel.setFont(font);
        speedSlider.setBackground(new Color(0, 18, 121));
        speedSlider.setForeground(Color.WHITE);
        speedSlider.addChangeListener(e -> visualQueue.setAnimationSpeed(speedSlider.getValue()));
        speedPanel.add(speedLabel, BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);
        return speedPanel;
    }

}