package main.menu;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static main.interfaces.MacroInterface.*;
public final class SortingMenu extends GenericMenu {
    private static final JMenuItem[] sortingMenuItems = new JMenuItem[4];
    private SortingMenu(){
        super(SORTING_ALGORITHMS, SORTING_ARRAY);
    }
    /**
     * Used for providing Sorting Algorithms Menu
     * @return <code>SortingMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull SortingMenu getInstance(){
        return new SortingMenu();
    }
}
