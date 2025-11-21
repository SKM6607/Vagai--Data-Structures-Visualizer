package main.menu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import static main.interfaces.MacroInterface.*;
public final class LinkedListMenu extends GenericMenu {
    private LinkedListMenu(){
        super(LINKED_LIST,LINKED_LIST_ARRAY);
    }
    /**
     * Used for providing Linked List Menu
     * @return <code>LinkedListMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull LinkedListMenu getInstance(){
        return new LinkedListMenu();
    }
}
