package main.menu;
import javax.swing.*;
import java.awt.*;

import static main.interfaces.DefaultWindowsInterface.*;
public sealed abstract class GenericMenu extends JMenu
        permits LinkedListMenu,
        SortingMenu,
        QueueMenu,
        StackMenu {
    protected JMenuItem[] menuItems;
    protected GenericMenu(String menuName, String[] menuItems) {
        super(menuName);
        this.menuItems = new JMenuItem[menuItems.length];
        setForeground(foregroundColor);
        setBackground(backgroundColor);
        setFont(menuFont);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        for (int i = 0; i < menuItems.length; i++) {
            this.menuItems[i] = new JMenuItem(menuItems[i]);
            this.menuItems[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.menuItems[i].setForeground(foregroundColor);
            this.menuItems[i].setBackground(backgroundColor);
            this.setFont(menuFont);
        }
    }
}
