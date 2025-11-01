package main.sorting;

import main.dialogs.LegendDialog;

import javax.swing.*;
import java.awt.*;


public final class BubbleSort extends Sorting {
    public BubbleSort(JPanel parent){
        super(parent);
        algoName=BUBBLE_SORTING;
        legend.put("Successfully Sorted Elements", Color.GREEN);
        legend.put("Current Comparison", Color.RED);
        legend.put("Finished Sorting", Color.CYAN);
        legendDialog = new LegendDialog(parentWindow, "Legend: Bubble Sort", legend);
    }

    @Override
    public void sort() {
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
                    SortingHelper.swapBlocks(blocks.get(j), blocks.get(j + 1));
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
        displaySuccess();
    }

}
