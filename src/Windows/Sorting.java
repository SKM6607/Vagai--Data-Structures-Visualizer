package Windows;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//TODO: Idea is to enable users to enter max size and it has random numbers in an array
public class Sorting extends JPanel {
    private final int SLEEP_TIME=500;
    private static final int SPACING = 25;
    private final int LIMIT = 50;
    private final int widthX = 20, spacingX = 10;
    private final ArrayList<ArrayBlock> blocks = new ArrayList<>();
    private final int choice;
    private final String currentAlgorithm;
    private final Map<String, Color> map = new HashMap<>();
    private JButton startButton;
    private JLabel label;
    private JSlider slider;
    private final Runnable[] sortingAlgorithms = new Runnable[]{this::selectionSort, this::insertionSort, this::bubbleSort, this::quickSort};
    private int WIDTH, HEIGHT, MAX_ELEMENTS = 5;
    private boolean isStart = true;
    private volatile boolean paused = false;
    private Thread thread;
    private Window myParent = SwingUtilities.getWindowAncestor(this);
    private final JDialog legend;
    public Sorting(int width, int height, String menuOption) {
        //setBlock
        {
            currentAlgorithm = menuOption;
            switch (menuOption) {
                case "Insertion Sort":
                    map.clear();
                    map.put("Successfully Sorted Elements", Color.GREEN);
                    map.put("Current Comparison", Color.RED);
                    map.put("Finished Sorting", Color.CYAN);
                    legend = new LegendDialog(myParent, "Legend: Insertion Sort", map);
                    choice = 1;
                    break;
                case "Bubble Sort":
                    map.clear();
                    map.put("Successfully Sorted Elements", Color.GREEN);
                    map.put("Current Comparison", Color.RED);
                    map.put("Finished Sorting", Color.CYAN);
                    legend = new LegendDialog(myParent, "Legend: Bubble Sort", map);
                    choice = 2;
                    break;
                case "Quick Sort":
                    map.put("Pivot Selected", Color.GREEN);
                    map.put("Start Element",Color.YELLOW);
                    map.put("End Element",Color.ORANGE);
                    map.put("Swap Elements",Color.RED);
                    map.put("Finished Sorting",Color.CYAN);
                    legend = new LegendDialog(myParent, "Legend: Quick Sort", map);
                    choice = 3;
                    break;
                case "Selection Sort":
                    map.clear();
                    map.put("Successfully Sorted Elements", Color.GREEN);
                    map.put("Current Minimum", Color.BLUE);
                    map.put("Comparisons", Color.YELLOW);
                    map.put("Next Minimum", Color.RED);
                    map.put("Finished Sorting", Color.CYAN);
                default:
                    legend = new LegendDialog(myParent, "Legend: Selection Sort", map);
                    choice = 0;
                    break;
            }
            legend.setVisible(false);
            WIDTH = width;
            HEIGHT = height;
            initAnimation();
        }
        add(getUserPanel(), BorderLayout.WEST);
        setBackground(Color.BLACK);
    }


    private void initAnimation() {
        blocks.clear();
        Random random = new Random();
        int startX = -spacingX + 5;
        for (int i = 0; i < MAX_ELEMENTS; i++) {
            int randomRange = random.nextInt(10, 500);
            blocks.add(new ArrayBlock(startX += widthX + spacingX, HEIGHT - 200, randomRange, Color.WHITE));
        }
        repaint();
    }

    private @NotNull JPanel getUserPanel() {
        GridLayout layout = new GridLayout(1, 4);
        label = new JLabel("Current Array Elements: 5");
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        slider = new JSlider(0, LIMIT, MAX_ELEMENTS);
        slider.addChangeListener(_ -> {
            isStart = true;
            label.setText(String.format("Current Array Elements: %d", slider.getValue()));
            MAX_ELEMENTS = slider.getValue();
            initAnimation();
        });
        slider.setOpaque(false);
        slider.setBackground(Color.BLACK);
        JLabel algorithmName = new JLabel(currentAlgorithm);
        JPanel returnPanel = new JPanel();
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

    private @NotNull JButton getButton(JSlider slider) {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(_ -> {
            if (isStart) {
                thread = new Thread(sortingAlgorithms[choice]);
                slider.setEnabled(false);
                paused = false;
                startButton.setEnabled(false);
                isStart = false;
                thread.start();
            }
        });
        startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        startButton.setSize(150, 50);
        return startButton;
    }
    public void invokeLegend(){
        legend.setVisible(true);
    }
    @Override
    protected void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        //Set Background
        {
            g1.setColor(Color.BLACK);
            g1.fillRect(0, 0, WIDTH, HEIGHT);
            drawGrid(g);
        }
        drawElements(g);
    }

    private void drawGrid(Graphics2D g) {
        Color set = new Color(33, 33, 33);
        g.setColor(set);
        for (int i = 0; i < WIDTH; i += SPACING) {
            g.drawLine(i, 0, i, HEIGHT);
        }
        for (int i = 0; i < HEIGHT; i += SPACING) {
            g.drawLine(0, i, WIDTH, i);
        }
    }

    void drawElements(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        for (ArrayBlock element : blocks) {
            g.setColor(element.color);
            g.fillRect(element.x, element.y - element.height, widthX, element.height);
            g.drawString(String.format("%d", element.height), element.x, element.y + 30);
        }
    }

    private void swapBlocks(ArrayBlock block1, ArrayBlock block2) {
        int temp = block1.height;
        block1.height = block2.height;
        block2.height = temp;
    }

    private void selectionSort() {
        if (isSorted()) {
            return;
        }
            for (int i = 0, success; i < blocks.size(); i++) {
                quickReset();
                for (success = 0; success < i; success++) {
                    blocks.get(success).color = Color.GREEN;
                    repaint();
                    sleep();
                }
                ArrayBlock current = blocks.get(i);
                int minimum = current.height;
                int idx = i;
                current.color = Color.BLUE;
                repaint();
                sleep();
                for (int j = i + 1; j < blocks.size(); j++) {
                    ArrayBlock comparison = blocks.get(j);
                    int compHeight = comparison.height;
                    boolean shadeRed = true;
                    if (compHeight < minimum) {
                        minimum = compHeight;
                        idx = j;
                        for (int k = j; k < blocks.size(); k++) {
                            if (blocks.get(k).height < minimum) {
                                shadeRed = false;
                                break;
                            }
                        }
                        if (shadeRed) {
                            comparison.color = Color.RED;
                            repaint();
                            sleep();
                        } else {
                            comparison.color = Color.YELLOW;
                            repaint();
                            sleep();
                        }
                    } else {
                        comparison.color = Color.YELLOW;
                        repaint();
                        sleep();
                    }
                }
                swapBlocks(blocks.get(idx), current);
                blocks.get(idx).color = Color.WHITE;
                current.color = Color.WHITE;
                repaint();
                sleep();
            }
            returnCtrl();
    }

    void returnCtrl() {
        startButton.setEnabled(true);
        for (int j = 0; j < blocks.size(); j++) {
            blocks.get(j).color = Color.CYAN;
            repaint();
        }
        slider.setEnabled(true);
    }

    private void insertionSort() {
            if (isSorted()) return;
            for (int i = 1; i < blocks.size(); i++) {
                for (int a = 0; a < i; a++) {
                    boolean shadeGreen = true;
                    for (int b = a + 1; b < blocks.size(); b++) {
                        if (blocks.get(b).height < blocks.get(a).height) {
                            shadeGreen = false;
                            break;
                        }
                    }
                    if (shadeGreen) {
                        blocks.get(a).color = Color.GREEN;
                        repaint();
                        sleep();
                    }
                }
                int keyHeight = blocks.get(i).height;   // keep the value
                int j = i - 1;
                blocks.get(i).color = Color.BLUE; // current key
                repaint();
                sleep();
                //Blue is for current key, RED is for shifting elements such as j+1 and j each time
                while (j >= 0 && blocks.get(j).height > keyHeight) {
                    blocks.get(j + 1).height = blocks.get(j).height;
                    blocks.get(j + 1).color = blocks.get(j).color = Color.RED; // shifting element
                    repaint();
                    sleep();
                    blocks.get(j + 1).color = blocks.get(j).color = Color.WHITE; // shifting element
                    repaint();
                    j--;
                }
                blocks.get(i).color = Color.WHITE; // current key
                repaint();
                blocks.get(j + 1).height = keyHeight;
            }
            returnCtrl();
    }

    private void bubbleSort() {
        int i, j;
        boolean swapped;

            for (i = 0; i < blocks.size() - 1; i++) {
                for (int k = blocks.size() - 1; k >= blocks.size() - i && i != 0; k--) {
                    blocks.get(k).color = Color.GREEN;
                    repaint();
                    sleep();
                }
                swapped = false;
                for (j = 0; j < blocks.size() - i - 1; j++) {
                    if (blocks.get(j).height > blocks.get(j + 1).height) {
                        blocks.get(j).color =
                                blocks.get(j + 1).color = Color.RED;
                        repaint();
                        swapBlocks(blocks.get(j), blocks.get(j + 1));
                        sleep();
                        swapped = true;
                    }
                    blocks.get(j).color =
                            blocks.get(j + 1).color = Color.WHITE;
                    repaint();
                }
                if (!swapped) {
                    break;
                }
            }

        returnCtrl();
    }

    private void quickSort() {
        quickSortHelper(blocks, 0, blocks.size() - 1);
        returnCtrl();
    }

    private void swapHeights(ArrayList<ArrayBlock> arrayList, int idx0, int idx1) {
        int temp = arrayList.get(idx1).height;
        arrayList.get(idx1).height = arrayList.get(idx0).height;
        arrayList.get(idx0).height = temp;
    }

    private void quickSortHelper(ArrayList<ArrayBlock> arrayList, int low, int high) {

        if (low >= high) return;

        int start = low;
        int end = high;
        int pivotHeight = arrayList.get((start + end) / 2).height;
        ArrayBlock pivotBlock = arrayList.get((start + end) / 2);

        // Highlight pivot
        pivotBlock.color = Color.GREEN;
        repaint();
        sleep();

        while (start <= end) {
            // Highlight comparison on left
            arrayList.get(start).color = Color.YELLOW;
            repaint();
            sleep();
            while (arrayList.get(start).height < pivotHeight) {
                arrayList.get(start).color = Color.WHITE; // reset
                start++;
                arrayList.get(start).color = Color.YELLOW;
                repaint();
                sleep();
            }

            // Highlight comparison on right
            arrayList.get(end).color = Color.ORANGE;
            repaint();
            sleep();
            while (arrayList.get(end).height > pivotHeight) {
                arrayList.get(end).color = Color.WHITE; // reset
                end--;
                arrayList.get(end).color = Color.ORANGE;
                repaint();
                sleep();
            }

            if (start <= end) {
                // Highlight swap
                arrayList.get(start).color = Color.RED;
                arrayList.get(end).color = Color.RED;
                repaint();
                sleep();

                swapHeights(arrayList, start, end);

                arrayList.get(start).color = Color.WHITE;
                arrayList.get(end).color = Color.WHITE;
                start++;
                end--;
            }
        }

        // Reset pivot highlight
        pivotBlock.color = Color.WHITE;
        repaint();
        sleep();

        if (low < end) quickSortHelper(arrayList, low, end);
        if (start < high) quickSortHelper(arrayList, start, high);
    }

    private void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void quickReset() {
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).color = Color.WHITE;
            repaint();
        }
    }

    private boolean isSorted() {
        boolean isSorted = true;
        for (int i = 1; i < blocks.size(); i++) {
            if (blocks.get(i).height < blocks.get(i - 1).height) isSorted = false;
        }
        return isSorted;
    }

    private static final class ArrayBlock {
        int x, y, height;
        Color color;

        public ArrayBlock(int x, int y, int height, Color color) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.color = color;
        }

    }
}
