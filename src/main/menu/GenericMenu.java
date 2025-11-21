package main.menu;
import javax.swing.*;
public sealed abstract class GenericMenu extends JMenu
        permits LinkedListMenu,
        SortingMenu,
        QueueMenu,
        StackMenu {
    protected JMenuItem[] menuItems;
    protected GenericMenu(String menuName, String[] menuItems) {
        super(menuName);
        this.menuItems = new JMenuItem[menuItems.length];
        for (int i = 0; i < menuItems.length; i++) {
            this.menuItems[i] = new JMenuItem(menuItems[i]);
        }
    }
}
