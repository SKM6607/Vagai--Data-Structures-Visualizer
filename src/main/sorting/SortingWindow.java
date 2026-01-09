package main.sorting;

import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import static main.interfaces.MacroInterface.*;

public final class SortingWindow extends JPanel {
    private static SortingWindow singleton;
    Sorting sorting;
    JLabel algorithm = new JLabel(SELECTION_SORTING);
    JPanel returnPanel = new JPanel();
    private JButton startButton;
    private JLabel label;
    private JSlider slider;
    private int MAX_ELEMENTS = 5;
    private SortingWindow() {
        setLayout(new BorderLayout());
        add(getUserPanel(), BorderLayout.NORTH);
        sorting = new SelectionSort(this);
        add(sorting, BorderLayout.CENTER);
    }

    public static @NotNull SortingWindow createSortingWindow() {
        return (singleton == null) ? singleton = new SortingWindow() : singleton;
    }

    private JPanel getUserPanel() {
        GridLayout layout = new GridLayout(1, 4);
        label = new JLabel("Current Array Elements: 5");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        int LIMIT = 50;
        slider = new JSlider(0, LIMIT, MAX_ELEMENTS);
        slider.addChangeListener( _->{
            basicSliderSetup();
            sorting.setBlocks(MAX_ELEMENTS);
        });
        slider.setOpaque(false);
        slider.setBackground(Color.BLACK);
        startButton = getButton();
        returnPanel.setLayout(layout);
        returnPanel.add(label);
        returnPanel.add(slider);
        algorithm.setForeground(Color.WHITE);
        algorithm.setBackground(Color.BLACK);
        algorithm.setOpaque(true);
        algorithm.setPreferredSize(new Dimension(250, 50));
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        algorithm.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        returnPanel.add(algorithm);
        returnPanel.add(startButton);
        returnPanel.setPreferredSize(new Dimension(1000, 50));
        returnPanel.setBackground(Color.BLACK);
        return returnPanel;
    }

    private @NotNull JButton getButton() {
        JButton startButton = new JButton("START");
        startButton.addActionListener(_ -> {
            ctrl(false);
            SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    sorting.sort();
                    return null;
                }

                @Override
                protected void done() {
                    ctrl(true);
                }
            };
            swingWorker.execute();
        });
        startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        startButton.setSize(150, 50);
        return startButton;
    }
    private void basicSliderSetup(){
        label.setText(String.format("Current Array Elements: %d", slider.getValue()));
        MAX_ELEMENTS = slider.getValue();
    }
    public void switchAlgorithm(String algorithm) {
        remove(sorting);
        switch (algorithm) {
            case INSERTION_SORTING -> sorting = new InsertionSort(this);
            case BUBBLE_SORTING -> sorting = new BubbleSort(this);
            case QUICK_SORTING -> sorting = new QuickSort(this);
            default -> sorting = new SelectionSort(this);
        }
        add(sorting);
        revalidate();
        repaint();
        if (Arrays.binarySearch(SORTING_ARRAY, algorithm) != -1) this.algorithm.setText(algorithm);
        else this.algorithm.setText(SELECTION_SORTING);
    }

    private void ctrl(boolean s) {
        startButton.setEnabled(s);
        slider.setEnabled(s);
    }

}
