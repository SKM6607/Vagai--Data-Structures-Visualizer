package utils.mainWindow;
import main.menu.LinkedListMenu;
import main.menu.QueueMenu;
import main.menu.SortingMenu;
import main.menu.StackMenu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
public final class MainMenuBar extends JMenuBar {
    private final SortingMenu sortingMenu;
    private final StackMenu stackMenu;
    private final LinkedListMenu linkedListMenu;
    private final QueueMenu queueMenu;
    private MainMenuBar(JPanel parent) {
        SortingMenu sortingMenu = SortingMenu.getInstance(parent);
        QueueMenu queueMenu = QueueMenu.getInstance(parent);
        LinkedListMenu linkedListMenu = LinkedListMenu.getInstance(parent);
        StackMenu stackMenu = StackMenu.getInstance(parent);
        setForeground(foregroundColor);
        setBackground(backgroundColor);
        setBorderPainted(false);
        setVisible(false);
        add(sortingMenu);
        add(Box.createHorizontalStrut(15));
        add(linkedListMenu);
        add(Box.createHorizontalStrut(15));
        add(stackMenu);
        add(Box.createHorizontalStrut(15));
        add(queueMenu);
    }
    @Contract(" -> new")
    public static @NotNull MainMenuBar getInstance(JPanel parent) {
        return new MainMenuBar(parent);
    }
}
