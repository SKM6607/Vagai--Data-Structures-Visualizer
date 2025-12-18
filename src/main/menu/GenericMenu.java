package main.menu;

import main.dialogs.LegendDialog;
import main.dialogs.QRCodeDisplayer;
import utils.main.MainCardPanel;

import javax.swing.*;
import java.awt.*;

import static main.interfaces.DefaultWindowsInterface.*;

public sealed abstract class GenericMenu extends JMenu
        permits LinkedListMenu,
        SortingMenu,
        QueueMenu,
        StackMenu {
    private final LayoutManager layout;
    protected JMenuItem[] menuItems;
    protected JMenuItem currentItem;

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
            var ref = this.menuItems[i];
            ref.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            ref.setForeground(foregroundColor);
            ref.setBackground(backgroundColor);
            ref.setFont(menuFont);
            final int effectivelyFinalI = i;
            ref.addActionListener(e -> {
                currentItem = (JMenuItem) e.getSource();
                defaultOnClickOperation(parent, menuItems[effectivelyFinalI]);
            });
            this.setFont(menuFont);
            add(ref);
        }
    }

    protected static void closeQRWindow() {
        for (Window window : Window.getWindows()) {
            if (window instanceof QRCodeDisplayer) {
                window.dispose();
            }
        }
    }

    protected void closeChildWindows() {
        for (Window window : Window.getWindows()) {
            if (!(window instanceof LegendDialog currentWindow)) return;
            var currentAlgorithm = currentWindow.getTitle().split(":")[1].trim();
            if (!currentAlgorithm.equals(this.currentItem.getText())) {
                window.dispose();
            }
        }
        closeQRWindow();
    }

    protected final void defaultOnClickOperation(JPanel parent, String s) {
        closeChildWindows();
        ((CardLayout) layout).show(parent, s);
    }

}
