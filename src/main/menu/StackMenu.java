package main.menu;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.mainWindow.MainCardPanel;

import javax.swing.*;
import static main.interfaces.MacroInterface.*;
public final class StackMenu extends GenericMenu {
    private StackMenu(MainCardPanel parent) {
        super(STACK,STACK_ARRAY,parent);
    }
    /**
     * Used for providing Stack Menu
     * @return <code>StackMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull StackMenu getInstance(MainCardPanel parent) {
        return new StackMenu(parent);
    }
}
