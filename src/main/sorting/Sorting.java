package main.sorting;
import main.dialogs.LegendDialog;
import main.interfaces.GridInterface;
import main.interfaces.MacroInterface;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public sealed abstract class Sorting
        extends JPanel
        implements MacroInterface, GridInterface
        permits SelectionSort,InsertionSort,BubbleSort,QuickSort
{
    protected int sleepTime;
    protected Map<String,Color> legend;
    public LegendDialog legendDialog;
    protected Window parentWindow;
    public Sorting(JPanel parent){
        parentWindow=SwingUtilities.getWindowAncestor(parent);
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
    public void sleep() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
