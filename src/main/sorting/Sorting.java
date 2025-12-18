package main.sorting;

import main.dialogs.LegendDialog;
import main.interfaces.GridInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import static main.interfaces.DefaultWindowsInterface.*;
import static main.interfaces.MacroInterface.SELECTION_SORTING;

/**
 * The <code>Sorting</code> abstract class is the class with the most basic methods for designing  and implementing <code>Sorting Algorithms</code>
 *
 * @author Sri Koushik JK
 * @since v0.0.3
 */
public sealed abstract class Sorting
        extends JPanel
        implements GridInterface
        permits SelectionSort, InsertionSort, BubbleSort, QuickSort {
    private static final Color backgroundColor = new Color(0xA0F29);
    protected final int widthX = 20;
    protected final ArrayList<ArrayBlock> blocks = new ArrayList<>();
    protected final Window parentWindow;
    protected final JPanel parentPanel;
    public LegendDialog legendDialog;
    protected String algoName;
    protected Map<String, Color> legend = new HashMap<>();

    public Sorting(JPanel parent) {
        parentWindow = SwingUtilities.getWindowAncestor(parent);
        parentPanel = parent;
        algoName = SELECTION_SORTING;
        setBackground(backgroundColor);
        legendSetup();
        invokeLegend();
        initAnimation();
    }
    /**
     * The <code>legendSetup()</code> method is an abstract method required to be implemented whenever one is writing a sorting algorithm.
     * <br>This helps opening a window that provides a legend for explaining sort algorithm
     *
     * @author Sri Koushik JK
     * @see Sorting
     * @since v0.0.5
     *
     */
    protected abstract void legendSetup();

    /**
     * Allows you to set the number of blocks for display
     *
     * @param n Number of Blocks
     * @author Sri Koushik JK
     * @since v0.0.5
     *
     */
    public final void setBlocks(int n) {
        if(!blocks.isEmpty()) blocks.clear();
        Random random = new Random();
        final int dx = 10;
        int startX = 5;
        for (int i = 0; i < n; i++) {
            int randomRange = random.nextInt(10, 500);
            blocks.add(new ArrayBlock(startX, height - 200, randomRange, Color.WHITE));
            startX += widthX + dx;
        }
        repaint();
    }

    protected final void initAnimation() {
        setBlocks(5);
    }

    /**
     * The <code>sort</code> method is an abstract method needed to be implemented whenever you are writing <code>Sorting Algorithms</code>
     *
     * @author Sri Koushik JK
     * @see Sorting
     * @since v0.0.3
     *
     */
    public abstract void sort();

    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        drawGrid(g);
        drawElements(g);
    }

    public final void invokeLegend() {
        legendDialog.setVisible(true);
    }

    protected final void drawElements(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        for (ArrayBlock element : blocks) {
            g.setColor(element.color);
            g.fillRect(element.x, element.y - element.height, widthX, element.height);
            g.drawString(String.format("%d", element.height), element.x, element.y + 30);
        }
    }

    protected final void displaySuccess() {
        for (ArrayBlock block : blocks) {
            block.color = Color.CYAN;
            repaint();
        }
    }

    protected final boolean isSorted() {
        boolean isSorted = true;
        for (int i = 1; i < blocks.size(); i++) {
            if (blocks.get(i).height < blocks.get(i - 1).height) {
                isSorted = false;
                break;
            }
        }
        ((SortingWindow) parentPanel).returnCtrl();
        return isSorted;
    }

    protected final void quickReset() {
        for (ArrayBlock block : blocks) {
            block.color = Color.WHITE;
            repaint();
        }
    }

    public final void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected static final class ArrayBlock {
        int x, y, height;
        Color color;

        public ArrayBlock(int x, int y, int height, Color color) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.color = color;
        }
    }

    protected static final class SortingHelper {
        public static void swapBlocks(ArrayBlock block1, ArrayBlock block2) {
            int temp = block1.height;
            block1.height = block2.height;
            block2.height = temp;
        }

        public static void swapHeights(ArrayList<ArrayBlock> arrayList, int i0, int i1) {
            int temp = arrayList.get(i1).height;
            arrayList.get(i1).height = arrayList.get(i0).height;
            arrayList.get(i0).height = temp;
        }
    }
}
