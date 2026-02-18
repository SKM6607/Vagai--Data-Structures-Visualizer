package main.sorting;

import java.util.ArrayList;

public final class SortingHelper {
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
