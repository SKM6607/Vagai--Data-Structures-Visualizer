package main.sorting;

import main.dialogs.LegendDialog;

import javax.swing.*;
import java.awt.*;

public final class SelectionSort extends Sorting {
    public SelectionSort(JPanel parent)
    {
        super(parent);
        legend.put("Successfully Sorted Elements", Color.GREEN);
        legend.put("Current Minimum", Color.BLUE);
        legend.put("Comparisons", Color.YELLOW);
        legend.put("Next Minimum", Color.RED);
        legend.put("Finished Sorting", Color.CYAN);
        legendDialog= new LegendDialog(parentWindow, "Legend: Insertion Sort", legend);
    }
}
