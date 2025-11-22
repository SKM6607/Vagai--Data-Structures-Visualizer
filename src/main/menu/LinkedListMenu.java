package main.menu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.mainWindow.MainCardPanel;
import static main.interfaces.MacroInterface.*;
public final class LinkedListMenu extends GenericMenu {
    private LinkedListMenu(MainCardPanel parent){
        super(LINKED_LIST,LINKED_LIST_ARRAY,parent);
    }
    /**
     * Used for providing Linked List Menu
     * @return <code>LinkedListMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull LinkedListMenu getInstance(MainCardPanel parent){
        return new LinkedListMenu(parent);
    }
}
