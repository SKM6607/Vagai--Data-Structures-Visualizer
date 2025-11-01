package main.sorting;

import main.dialogs.LegendDialog;
import main.interfaces.GridInterface;
import main.interfaces.MacroInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public sealed abstract class Sorting
        extends JPanel
        implements MacroInterface, GridInterface
        permits SelectionSort, InsertionSort, BubbleSort, QuickSort {
    protected final int widthX = 20;
    protected String algoName;
    protected final ArrayList<ArrayBlock> blocks = new ArrayList<>();
    public LegendDialog legendDialog;
    protected Map<String, Color> legend;
    protected Window parentWindow;

    public Sorting(JPanel parent) {
        parentWindow = SwingUtilities.getWindowAncestor(parent);
        algoName=null;
    }
    public final String getAlgoName(){
        return algoName;
    }
    protected final void initAnimation() {
        blocks.clear();
        Random random = new Random();
        int spacingX = 10;
        int startX = -spacingX + 5;
        for (int i = 0; i < 5; i++) {
            int randomRange = random.nextInt(10, 500);
            blocks.add(new ArrayBlock(startX += widthX + spacingX, HEIGHT - 200, randomRange, Color.WHITE));
        }
        repaint();
    }

    public abstract void sort();

    public final void increaseBlocksByOne() {
        Random random = new Random();
        int spacingX = 10;
        int randomRange = random.nextInt(10, 500);
        blocks.add(new ArrayBlock(blocks.getLast().x += widthX + spacingX, HEIGHT - 200, randomRange, Color.WHITE));
    }

    public final void decreaseBlocksByOne() {
        Random random = new Random();
        blocks.removeLast();
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
        for (int j = 0; j < blocks.size(); j++) {
            blocks.get(j).color = Color.CYAN;
            repaint();
        }
    }

    protected final boolean isSorted() {
        boolean isSorted = true;
        for (int i = 1; i < blocks.size(); i++) {
            if (blocks.get(i).height < blocks.get(i - 1).height) isSorted = false;
        }
        return isSorted;
    }

    protected final void quickReset() {
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).color = Color.WHITE;
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
