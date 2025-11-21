package main.menu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static main.interfaces.MacroInterface.*;
public final class LinkedListMenu extends GenericMenu {
    private LinkedListMenu(JPanel parent){
        super(LINKED_LIST,LINKED_LIST_ARRAY,parent);
    }
    /**
     * Used for providing Linked List Menu
     * @return <code>LinkedListMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull LinkedListMenu getInstance(JPanel parent){
        return new LinkedListMenu(parent);
    }
}
