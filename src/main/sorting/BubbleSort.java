package main.sorting;

import main.dialogs.LegendDialog;

import javax.swing.*;
import java.awt.*;

public final class BubbleSort extends Sorting {
    public BubbleSort(JPanel parent){
        super(parent);
        legend.put("Successfully Sorted Elements", Color.GREEN);
        legend.put("Current Comparison", Color.RED);
        legend.put("Finished Sorting", Color.CYAN);
        legendDialog = new LegendDialog(parentWindow, "Legend: Bubble Sort", legend);
    }

}
