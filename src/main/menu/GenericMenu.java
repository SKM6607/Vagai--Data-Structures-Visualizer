package main.menu;

import main.dialogs.LegendDialog;
import main.dialogs.QRCodeDisplayer;
import utils.mainWindow.MainCardPanel;

import javax.swing.*;
import java.awt.*;

import static main.interfaces.DefaultWindowsInterface.*;

public sealed abstract class GenericMenu extends JMenu
        permits LinkedListMenu,
        SortingMenu,
        QueueMenu,
        StackMenu {
    protected JMenuItem[] menuItems;
    private LayoutManager layout;
    protected GenericMenu(String menuName, String[] menuItems, MainCardPanel parent) {
        super(menuName);
        layout = parent.getLayout();
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
            int effectivelyFinalI = i;
            this.menuItems[i].addActionListener(_-> defaultOnClickOperation(parent,menuItems[effectivelyFinalI]));
            this.setFont(menuFont);
        }
    }
    protected final void defaultOnClickOperation(JPanel parent,String s){
        closeChildWindows();
        ((CardLayout)layout).show(parent,s);
    }
    protected static void closeChildWindows() {
        for (Window window : Window.getWindows()) {
            if (window instanceof LegendDialog) {
                window.dispose();
            }
        }
    }
    protected static void closeQRWindow() {
        for (Window window : Window.getWindows()) {
            if (window instanceof QRCodeDisplayer) {
                window.dispose();
            }
        }
    }
    public void onClickMenuItem(String menuItem, Runnable r) {
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItem.equals(menuItems[i].getText())) {
                menuItems[i].addActionListener(_ -> r.run());
            }
        }
    }
}
