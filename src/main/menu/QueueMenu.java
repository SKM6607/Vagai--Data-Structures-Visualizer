package main.menu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import utils.mainWindow.MainCardPanel;
import static main.interfaces.MacroInterface.*;
public final class QueueMenu extends GenericMenu{
    private static QueueMenu singleton;
    private QueueMenu(MainCardPanel parent){
        super(QUEUE,QUEUE_ARRAY,parent);
    }
    /**
     * Used for providing Queue Menu
     * @return <code>QueueMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull QueueMenu getInstance(MainCardPanel parent){
        return (singleton == null) ? singleton = new QueueMenu(parent): singleton;
    }
}
