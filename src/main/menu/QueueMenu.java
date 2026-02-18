package main.menu;

import utils.main.MainCardPanel;
import static main.interfaces.MacroInterface.*;

public final class QueueMenu extends GenericMenu {
    private static QueueMenu singleton;

    private QueueMenu(MainCardPanel parent) {
        super(QUEUE, QUEUE_ARRAY, parent);
    }

    /**
     * Used for providing Queue Menu
     * 
     * @return <code>QueueMenu</code>
     */
    public static QueueMenu getInstance(MainCardPanel parent) {
        return (singleton == null) ? singleton = new QueueMenu(parent) : singleton;
    }
}
