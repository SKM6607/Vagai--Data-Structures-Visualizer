package main.sorting;

import main.dialogs.LegendDialog;

import javax.swing.*;
import java.awt.*;

public final class QuickSort extends Sorting{
    public QuickSort(JPanel parent){
        super(parent);
        legend.put("Pivot Selected", Color.GREEN);
        legend.put("Start Element",Color.YELLOW);
        legend.put("End Element",Color.ORANGE);
        legend.put("Swap Elements",Color.RED);
        legend.put("Finished Sorting",Color.CYAN);
        legendDialog = new LegendDialog(parentWindow, "Legend: Quick Sort", legend);
    }
}
