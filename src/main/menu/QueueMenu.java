package main.menu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static main.interfaces.MacroInterface.*;
public final class QueueMenu extends GenericMenu{
    private QueueMenu(JPanel parent){
        super(QUEUE,QUEUE_ARRAY,parent);
    }
    /**
     * Used for providing Queue Menu
     * @return <code>QueueMenu</code>
     */
    @Contract(" -> new")
    public static @NotNull QueueMenu getInstance(JPanel parent){
        return new QueueMenu(parent);
    }
}
