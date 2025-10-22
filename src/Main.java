import sound.BackgroundMusic;
import pages.*;
import pages.dialogs.UpdateDialog;
import pages.interfaces.MacroInterface;
import pages.dialogs.LegendDialog;
import pages.dialogs.QRCodeDisplayer;
import javax.swing.*;
import java.awt.*;
//NOT WORKING VERSION
public class Main implements MacroInterface {
    static final Thread backgroundMusicThread = new Thread(new BackgroundMusic("src/Sound/Granius.wav"));
    private static final JMenuBar menuBarMain = new JMenuBar();
    private static final JMenu sortingMenu = new JMenu(SORTING_ALGORITHMS);
    private static final JMenu linkedListMenu = new JMenu(LINKED_LIST);
    private static final JMenu stackMenu = new JMenu(STACK);
    private static final JMenu queueMenu = new JMenu(QUEUE);
    private static final JMenu updateDialog=new JMenu(MORE);
    private static final JMenuItem[] sortingMenuItems = new JMenuItem[4];
    private static final JMenuItem linkedListMenuItem = new JMenuItem("Linked List");
    private static final JMenuItem cycleDetectionMenuItem = new JMenuItem("Cycle Detection");
    private static final JMenuItem stackMenuItem=new JMenuItem(STACK);
    private static final JMenuItem simpleQueueMenuItem = new JMenuItem("Simple Queue");
    private static final JMenuItem circularQueueMenuItem = new JMenuItem("Circular Queue");
    private static final JMenuItem priorityQueueMenuItem = new JMenuItem("Priority Queue");
    private static final JMenuItem updateDialogMenuItem=new JMenuItem(UPDATE_DIALOG);
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel cardPanel = new JPanel(cardLayout);
    private static final LoadingPage loadingPage = new LoadingPage();
    private static final Sorting sortingPanel = new Sorting(width, height, SELECTION_SORTING);
    private static final LinkedList linkedListPanel=new LinkedList();
    private static final LinkedListCycleDetection cycleDetectionPanel = new LinkedListCycleDetection();
    private static final StackWindowUsable stackWindow=new StackWindowUsable();
    private static final SimpleQueueWindow simpleQueueWindow = new SimpleQueueWindow();
    private static final CircularQueueWindow circularQueueWindow = new CircularQueueWindow();
    private static final PriorityQueueWindow priorityQueueWindow = new PriorityQueueWindow();
    private static JFrame jFrame;
    private static QRCodeDisplayer qrCode;
    private static final Font menuFont=new Font(Font.SANS_SERIF, Font.BOLD, 18);
    static {
        menuBarMain.setBorderPainted(false);
        menuBarMain.setVisible(false);
        menuBarMain.setForeground(foreGroundBG);
        menuBarMain.setBackground(themeColorBG);
        sortingMenu.setForeground(foreGroundBG);
        stackMenu.setForeground(foreGroundBG);
        stackMenu.setBackground(themeColorBG);
        sortingMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        for (int i = 0; i < sortingMenuItems.length; i++) {
            sortingMenuItems[i] = new JMenuItem(IDENTIFIER_ARRAY[i]);
            cardPanel.add(new Sorting(width,height , IDENTIFIER_ARRAY[i]), IDENTIFIER_ARRAY[i]);
            sortingMenu.
                    add(sortingMenuItems[i]).
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            sortingMenuItems[i].setBackground(themeColorBG);
            sortingMenuItems[i].setForeground(foreGroundBG);
            sortingMenuItems[i].setFont(menuFont);
        }
        // Add menu items to their respective menus
        stackMenu.add(stackMenuItem);
        linkedListMenu.add(linkedListMenuItem);
        linkedListMenu.add(cycleDetectionMenuItem);
        queueMenu.add(simpleQueueMenuItem);
        queueMenu.add(circularQueueMenuItem);
        queueMenu.add(priorityQueueMenuItem);
        
        // Add panels to card layout
        cardPanel.add(linkedListPanel, "Linked List");
        cardPanel.add(cycleDetectionPanel, "Cycle Detection");
        cardPanel.add(loadingPage, DEFAULT);
        cardPanel.add(stackWindow, STACK);
        cardPanel.add(simpleQueueWindow, "Simple Queue");
        cardPanel.add(circularQueueWindow, "Circular Queue");
        cardPanel.add(priorityQueueWindow, "Priority Queue");
        
        // Style menu items
        linkedListMenuItem.setFont(menuFont);
        cycleDetectionMenuItem.setFont(menuFont);
        stackMenuItem.setFont(menuFont);
        simpleQueueMenuItem.setFont(menuFont);
        circularQueueMenuItem.setFont(menuFont);
        priorityQueueMenuItem.setFont(menuFont);
        
        // Style menus
        linkedListMenu.setBackground(themeColorBG);
        linkedListMenu.setForeground(foreGroundBG);
        queueMenu.setBackground(themeColorBG);
        queueMenu.setForeground(foreGroundBG);
        queueMenu.setFont(menuFont);
        
        // Style menu items colors
        linkedListMenuItem.setBackground(themeColorBG);
        linkedListMenuItem.setForeground(foreGroundBG);
        cycleDetectionMenuItem.setBackground(themeColorBG);
        cycleDetectionMenuItem.setForeground(foreGroundBG);
        stackMenuItem.setBackground(themeColorBG);
        stackMenuItem.setForeground(foreGroundBG);
        simpleQueueMenuItem.setBackground(themeColorBG);
        simpleQueueMenuItem.setForeground(foreGroundBG);
        circularQueueMenuItem.setBackground(themeColorBG);
        circularQueueMenuItem.setForeground(foreGroundBG);
        priorityQueueMenuItem.setBackground(themeColorBG);
        priorityQueueMenuItem.setForeground(foreGroundBG);
        
        // Set cursors
        linkedListMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cycleDetectionMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stackMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        simpleQueueMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        circularQueueMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        priorityQueueMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateDialog.setBackground(themeColorBG);
        updateDialog.setForeground(foreGroundBG);
        updateDialog.setFont(menuFont);
        updateDialog.add(updateDialogMenuItem);
        updateDialogMenuItem.setForeground(foreGroundBG);
        updateDialogMenuItem.setBackground(themeColorBG);
        updateDialogMenuItem.setFont(menuFont);
        updateDialogMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateDialog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stackMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkedListMenu.add(linkedListMenuItem).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkedListMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        queueMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Add menus to menu bar
        menuBarMain.add(sortingMenu);
        menuBarMain.add(Box.createHorizontalStrut(15));
        menuBarMain.add(linkedListMenu);
        menuBarMain.add(Box.createHorizontalStrut(15));
        menuBarMain.add(stackMenu);
        menuBarMain.add(Box.createHorizontalStrut(15));
        menuBarMain.add(queueMenu);
        menuBarMain.add(Box.createHorizontalStrut(15));
        menuBarMain.add(updateDialog);
        
        sortingMenu.setFont(menuFont);
        linkedListMenu.setFont(menuFont);
        stackMenu.setFont(menuFont);
    }
    static {
        for (JMenuItem item : sortingMenuItems) {
            item.addActionListener(_ -> {
                closeChildWindows();
                cardLayout.show(cardPanel, item.getText());
                new Sorting(width,height,item.getText()).invokeLegend();
            });
        }
        linkedListMenuItem.addActionListener(_ -> {
            closeChildWindows();
            cardLayout.show(cardPanel, "Linked List");
        });
        cycleDetectionMenuItem.addActionListener(_ -> {
            closeChildWindows();
            cardLayout.show(cardPanel, "Cycle Detection");
        });
        loadingPage.returnControlOfLoadButton().addActionListener(_ -> {
            closeQRWindow();
            menuBarMain.setVisible(true);
            cardLayout.show(cardPanel, SELECTION_SORTING);
            sortingPanel.invokeLegend();
        });
        stackMenuItem.addActionListener(_->{
            closeChildWindows();
            cardLayout.show(cardPanel,STACK);
        });
        updateDialogMenuItem.addActionListener(_->{
            closeChildWindows();
            new UpdateDialog(null);
        });
    }
    private static void closeChildWindows() {
        for (Window window : Window.getWindows()) {
            if (window instanceof LegendDialog) {
                window.dispose();
            }
        }
    }
    private static void closeQRWindow() {
        for (Window window : Window.getWindows()) {
            if (window instanceof QRCodeDisplayer) {
                window.dispose();
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            jFrame = new JFrame("VAGAI");
            {
                qrCode=new QRCodeDisplayer(jFrame);
                qrCode.setVisible(true);
            }
            jFrame.setSize(width,height);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setBackground(themeColorBG);
            jFrame.setForeground(foreGroundBG);
            backgroundMusicThread.start();
            jFrame.add(cardPanel, BorderLayout.CENTER);
            jFrame.setLayout(cardLayout);
            jFrame.setJMenuBar(menuBarMain);
            cardLayout.show(cardPanel, DEFAULT);
            jFrame.setVisible(true);
        });
    }
}