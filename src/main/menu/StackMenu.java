package main.menu;

import utils.main.MainCardPanel;

import static main.interfaces.MacroInterface.STACK;
import static main.interfaces.MacroInterface.STACK_ARRAY;

public final class StackMenu extends GenericMenu {
    private static StackMenu singleton;

    private StackMenu(MainCardPanel parent) {
        super(STACK, STACK_ARRAY, parent);
    }

    /**
     * Used for providing Stack Menu
     *
     * @return <code>StackMenu</code>
     */
    public static StackMenu getInstance(MainCardPanel parent) {
        return (singleton == null) ? singleton = new StackMenu(parent) : singleton;
    }
}
