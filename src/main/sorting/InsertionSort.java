package main.sorting;

import main.dialogs.LegendDialog;

import javax.swing.*;
import java.awt.*;

public final class InsertionSort extends Sorting {
    public InsertionSort(JPanel parent) {
        super(parent);
        algoName=INSERTION_SORTING;
        legend.put("Finished Sorting", Color.CYAN);
        legend.put("Successfully Sorted Elements", Color.GREEN);
        legend.put("Current Comparison", Color.RED);
        legendDialog = new LegendDialog(parentWindow, "Legend: Insertion Sort", legend);
    }

    @Override
    public void sort() {
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
        displaySuccess();
    }
}
