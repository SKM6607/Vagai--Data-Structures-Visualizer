package main.sorting;

import main.dialogs.LegendDialog;

import javax.swing.*;
import java.awt.*;

import static main.sorting.Sorting.SortingHelper.swapBlocks;

public final class SelectionSort extends Sorting {
    public SelectionSort(JPanel parent) {
        super(parent);
        algoName = SELECTION_SORTING;
        legend.put("Successfully Sorted Elements", Color.GREEN);
        legend.put("Current Minimum", Color.BLUE);
        legend.put("Comparisons", Color.YELLOW);
        legend.put("Next Minimum", Color.RED);
        legend.put("Finished Sorting", Color.CYAN);
        legendDialog = new LegendDialog(parentWindow, "Legend: Insertion Sort", legend);
    }

    @Override
    public void sort() {
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
        displaySuccess();
    }
}
