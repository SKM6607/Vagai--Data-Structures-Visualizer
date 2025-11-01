package main.sorting;

import main.dialogs.LegendDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class QuickSort extends Sorting{
    public QuickSort(JPanel parent){
        super(parent);
        algoName=QUICK_SORTING;
        legend.put("Pivot Selected", Color.GREEN);
        legend.put("Start Element",Color.YELLOW);
        legend.put("End Element",Color.ORANGE);
        legend.put("Swap Elements",Color.RED);
        legend.put("Finished Sorting",Color.CYAN);
        legendDialog = new LegendDialog(parentWindow, "Legend: Quick Sort", legend);
    }
    private void quickSortHelper(ArrayList<ArrayBlock> arrayList, int low, int high) {

        if (low >= high) return;

        int start = low;
        int end = high;
        int pivotHeight = arrayList.get((start + end) / 2).height;
        ArrayBlock pivotBlock = arrayList.get((start + end) / 2);

        // Highlight pivot
        pivotBlock.color = Color.GREEN;
        repaint();
        sleep();

        while (start <= end) {
            // Highlight comparison on left
            arrayList.get(start).color = Color.YELLOW;
            repaint();
            sleep();
            while (arrayList.get(start).height < pivotHeight) {
                arrayList.get(start).color = Color.WHITE; // reset
                start++;
                arrayList.get(start).color = Color.YELLOW;
                repaint();
                sleep();
            }

            // Highlight comparison on right
            arrayList.get(end).color = Color.ORANGE;
            repaint();
            sleep();
            while (arrayList.get(end).height > pivotHeight) {
                arrayList.get(end).color = Color.WHITE; // reset
                end--;
                arrayList.get(end).color = Color.ORANGE;
                repaint();
                sleep();
            }

            if (start <= end) {
                // Highlight swap
                arrayList.get(start).color = Color.RED;
                arrayList.get(end).color = Color.RED;
                repaint();
                sleep();

                SortingHelper.swapHeights(arrayList, start, end);

                arrayList.get(start).color = Color.WHITE;
                arrayList.get(end).color = Color.WHITE;
                start++;
                end--;
            }
        }

        // Reset pivot highlight
        pivotBlock.color = Color.WHITE;
        repaint();
        sleep();

        if (low < end) quickSortHelper(arrayList, low, end);
        if (start < high) quickSortHelper(arrayList, start, high);
    }
    @Override
    public void sort() {
        quickSortHelper(blocks, 0, blocks.size() - 1);
        displaySuccess();
    }
}
