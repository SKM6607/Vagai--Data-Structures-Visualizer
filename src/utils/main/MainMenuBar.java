package utils.main;

import main.menu.*;
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
        SearchingMenu searchingMenu=SearchingMenu.getInstance(parent);
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
        add(Box.createHorizontalStrut(15));
        add(searchingMenu);
    }
    @Contract(" -> new")
    public static @NotNull MainMenuBar getInstance(MainCardPanel parent) {
        return (singleton == null) ? singleton = new MainMenuBar(parent) : singleton;
    }
}
