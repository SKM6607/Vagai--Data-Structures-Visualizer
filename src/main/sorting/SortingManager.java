package main.sorting;
import main.interfaces.*;
import main.dialogs.LegendDialog;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import static main.interfaces.MacroInterface.*;
public final class SortingManager extends JPanel implements GridInterface {
    Sorting sortingAlgorithm=new SelectionSort(this);
    private JButton startButton;
    private JLabel label;
    private JSlider slider;
    private int MAX_ELEMENTS = 5;
    JLabel algorithmName = new JLabel(SELECTION_SORTING);
    JPanel returnPanel = new JPanel();
    public SortingManager() {
        //setBlock
        {
        }
        add(getUserPanel(), BorderLayout.WEST);
        setBackground(new Color(0xA0F29));
    }
    private JPanel getUserPanel() {
        GridLayout layout = new GridLayout(1, 4);
        label = new JLabel("Current Array Elements: 5");
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        int LIMIT = 50;
        slider = new JSlider(0, LIMIT, MAX_ELEMENTS);
        slider.addChangeListener(e -> {
            label.setText(String.format("Current Array Elements: %d", slider.getValue()));
            MAX_ELEMENTS = slider.getValue();
        });
        slider.setOpaque(false);
        slider.setBackground(Color.BLACK);
        startButton = getButton(slider);
        returnPanel.setLayout(layout);
        returnPanel.add(label);
        returnPanel.add(slider);
        algorithmName.setForeground(Color.WHITE);
        algorithmName.setBackground(Color.BLACK);
        algorithmName.setOpaque(false);
        algorithmName.setPreferredSize(new Dimension(250, 50));
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        algorithmName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        returnPanel.add(algorithmName);
        returnPanel.add(startButton);
        returnPanel.setPreferredSize(new Dimension(1000, 50));
        returnPanel.setBackground(Color.BLACK);
        return returnPanel;
    }
    private JButton getButton(JSlider slider) {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
                slider.setEnabled(false);
                startButton.setEnabled(false);
        });
        startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        startButton.setSize(150, 50);
        return startButton;
    }
    public void switchAlgorithm(String algorithm){
        switch (algorithm) {
            case INSERTION_SORTING:
                break;
            case BUBBLE_SORTING:
                break;
            case QUICK_SORTING:
                break;
            case SELECTION_SORTING:
            default:
                break;
        }
   }
    private void returnCtrl() {
        startButton.setEnabled(true);
        slider.setEnabled(true);
    }
}
