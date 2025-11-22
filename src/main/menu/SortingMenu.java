package main.menu;

import main.sorting.SortingWindow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.mainWindow.MainCardPanel;

import javax.swing.*;

import static main.interfaces.MacroInterface.SORTING_ALGORITHMS;
import static main.interfaces.MacroInterface.SORTING_ARRAY;

public final class SortingMenu extends GenericMenu {
    SortingWindow sortingWindow = new SortingWindow();
    private SortingMenu(MainCardPanel parent) {
        super(SORTING_ALGORITHMS, SORTING_ARRAY, parent);
        parent.add(sortingWindow);
        for (JMenuItem jMenuItem : menuItems) {
            jMenuItem.addActionListener(_ -> sortingWindow.switchAlgorithm(jMenuItem.getText()));
        }
    }

    /**
     * Used for providing Sorting Algorithms Menu
     *
     * @return <code>SortingMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull SortingMenu getInstance(MainCardPanel parent) {
        return new SortingMenu(parent);
    }
}
