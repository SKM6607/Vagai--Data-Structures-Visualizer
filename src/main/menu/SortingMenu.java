package main.menu;

import main.sorting.SortingWindow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.main.MainCardPanel;

import javax.swing.*;

import static main.interfaces.MacroInterface.SORTING_ALGORITHMS;
import static main.interfaces.MacroInterface.SORTING_ARRAY;

public final class SortingMenu extends GenericMenu {
    private static SortingMenu singleton;
    SortingWindow sortingWindow =SortingWindow.getInstance();
    private SortingMenu(MainCardPanel parent) {
        super(SORTING_ALGORITHMS, SORTING_ARRAY, parent);
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
        return (singleton == null) ? singleton = new SortingMenu(parent): singleton;
    }
}
