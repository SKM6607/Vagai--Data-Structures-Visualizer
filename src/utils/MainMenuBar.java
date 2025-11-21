package utils;
import main.menu.LinkedListMenu;
import main.menu.QueueMenu;
import main.menu.SortingMenu;
import main.menu.StackMenu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
public final class MainMenuBar extends JMenuBar {
    private MainMenuBar() {
        add(SortingMenu.getInstance());
        add(Box.createHorizontalStrut(15));
        add(LinkedListMenu.getInstance());
        add(Box.createHorizontalStrut(15));
        add(StackMenu.getInstance());
        add(Box.createHorizontalStrut(15));
        add(QueueMenu.getInstance());
        add(Box.createHorizontalStrut(15));
    }
    @Contract(" -> new")
    public static @NotNull MainMenuBar getInstance() {
        return new MainMenuBar();
    }
}
