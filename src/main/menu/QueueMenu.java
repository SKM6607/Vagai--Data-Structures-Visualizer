package main.menu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import static main.interfaces.MacroInterface.*;
public final class QueueMenu extends GenericMenu{
    private QueueMenu(){
        super(QUEUE,QUEUE_ARRAY);
    }
    /**
     * Used for providing Queue Menu
     * @return <code>QueueMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull QueueMenu getInstance(){
        return new QueueMenu();
    }
}
