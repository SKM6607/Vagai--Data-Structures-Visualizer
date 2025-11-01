package main.queues;
import main.interfaces.QueueLightWeightInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static main.abstractClasses.ComponentUtilities.createTextField;
/**
 * <code>QueueWindow</code> is an abstract sealed class, that enables the inheritors to use the children of Queue class
 *
 * @see Queue
 * @see QueueLightWeightInterface
 * @since v0.0.3
 */
public sealed abstract class QueueWindow <T extends Queue>
        extends JPanel
        implements QueueLightWeightInterface
        permits PriorityQueueWindow, SimpleQueueWindow, CircularQueueWindow {
    JPanel speedPanel;
    final T visualQueue;
    JLabel infoLabel;
    JTextField textField = createTextField(font, themeColorBG);
    JButton enqueueButton = getEnqueueButton();
    JButton dequeueButton = getDequeueButton();
    public QueueWindow(T visualQueue){
        this.visualQueue=visualQueue;
        setLayout(new BorderLayout());
        this.speedPanel= new JPanel(new BorderLayout());
        JLabel speedLabel = new JLabel("ANIMATION SPEED", SwingConstants.CENTER);
        JSlider speedSlider = new JSlider();
        speedPanel.setBackground(new Color(0, 18, 121));
        speedLabel.setForeground(Color.WHITE);
        speedLabel.setFont(font);
        speedSlider.setBackground(new Color(0, 18, 121));
        speedSlider.setForeground(Color.WHITE);
        speedSlider.addChangeListener(_ -> visualQueue.setAnimationSpeed(speedSlider.getValue()));
        speedPanel.add(speedLabel, BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n' && verifyInput()) {
                    visualQueue.enqueue(Integer.parseInt(textField.getText()));
                    textField.setText("");
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    protected boolean verifyInput() {
        return textField.getInputVerifier().verify(textField);
    }
}