package main.menu;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static main.interfaces.MacroInterface.*;
public final class SortingMenu extends GenericMenu {
    private SortingMenu(JPanel parent){
        super(SORTING_ALGORITHMS, SORTING_ARRAY,parent);
    }
    /**
     * Used for providing Sorting Algorithms Menu
     * @return <code>SortingMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull SortingMenu getInstance(JPanel parent){
        return new SortingMenu(parent);
    }
}
