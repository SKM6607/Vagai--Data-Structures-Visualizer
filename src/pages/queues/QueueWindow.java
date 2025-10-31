package pages.queues;
import pages.interfaces.DefaultWindowsInterface;
import javax.swing.*;
import java.awt.*;
import static pages.abstractClasses.ComponentUtilities.createButton;
import static pages.abstractClasses.ComponentUtilities.createTextField;

public sealed abstract class QueueWindow <T extends Queue>
        extends JPanel
        implements DefaultWindowsInterface
        permits PriorityQueueWindow, SimpleQueueWindow, CircularQueueWindow
{
    protected T visualQueue;
    protected final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    protected JButton enqueueButton = createButton("ENQUEUE", new Color(0, 18, 121),font);
    protected JButton dequeueButton = createButton("DEQUEUE", Color.BLACK,font);
    protected JTextField textField=createTextField(font,themeColorBG);
    protected JLabel infoLabel;
    protected JPanel getPanel(){
        JPanel speedPanel= new JPanel(new BorderLayout());
        JLabel speedLabel = new JLabel("ANIMATION SPEED", SwingConstants.CENTER);
        JSlider speedSlider=new JSlider();
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
    protected void setInfoLabel(String infoLabelText){
        infoLabel = new JLabel(infoLabelText, SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(new Color(0, 18, 121));
    }
}