package utils.mainWindow;

import main.menu.LinkedListMenu;
import main.menu.QueueMenu;
import main.menu.SortingMenu;
import main.menu.StackMenu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static main.interfaces.DefaultWindowsInterface.backgroundColor;
import static main.interfaces.DefaultWindowsInterface.foregroundColor;

public final class MainMenuBar extends JMenuBar {
    private static MainMenuBar singleton = null;
    private MainMenuBar(MainCardPanel parent) {
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
    public static @NotNull MainMenuBar getInstance(MainCardPanel parent) {
        return (singleton == null) ? singleton = new MainMenuBar(parent) : singleton;
    }
}
