package main.sorting;

import main.dialogs.LegendDialog;

import javax.swing.*;
import java.awt.*;

public final class InsertionSort extends Sorting {
    public InsertionSort(JPanel parent) {
        super(parent);
        legend.put("Finished Sorting", Color.CYAN);
        legend.put("Successfully Sorted Elements", Color.GREEN);
        legend.put("Current Comparison", Color.RED);
        legendDialog = new LegendDialog(parentWindow, "Legend: Insertion Sort", legend);
    }
}
