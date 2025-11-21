import main.linkedList.LinkedListCycleDetection;
import main.linkedList.LinkedListImplementation;
import main.pages.HomePage;
import main.queues.CircularQueueWindow;
import main.queues.PriorityQueueWindow;
import main.queues.SimpleQueueWindow;
import main.sorting.SortingManager;
import main.stack.StackWindowUsable;
import sound.BackgroundMusic;
import main.dialogs.UpdateDialog;
import static main.interfaces.MacroInterface.*;
import main.dialogs.LegendDialog;
import main.dialogs.QRCodeDisplayer;
import javax.swing.*;
import java.awt.*;
/**
 *The <code>Main</code> class is the entry point <h5 color="blue">VAGAI Algorithms Visualizer</h4>
*/
public final class Main{
    private static final Thread backgroundMusicThread = new Thread(new BackgroundMusic("src/Sound/Granius.wav"));
    private static final JMenuBar menuBarMain = new JMenuBar();
    private static final JMenu sortingMenu = new JMenu(SORTING_ALGORITHMS);
    private static final JMenu linkedListMenu = new JMenu(LINKED_LIST);
    private static final JMenu stackMenu = new JMenu(STACK);
    private static final JMenu queueMenu = new JMenu(QUEUE);
    private static final JMenu updateDialog=new JMenu(MORE);
    private static final JMenuItem[] sortingMenuItems = new JMenuItem[4];
    private static final JMenuItem linkedListMenuItem = new JMenuItem(LINKED_LIST);
    private static final JMenuItem cycleDetectionMenuItem = new JMenuItem(CYCLE_DETECTION);
    private static final JMenuItem stackMenuItem=new JMenuItem(STACK);
    private static final JMenuItem simpleQueueMenuItem = new JMenuItem(SIMPLE_QUEUE);
    private static final JMenuItem circularQueueMenuItem = new JMenuItem(CIRCULAR_QUEUE);
    private static final JMenuItem priorityQueueMenuItem = new JMenuItem(PRIORITY_QUEUE);
    private static final JMenuItem updateDialogMenuItem=new JMenuItem(UPDATE_DIALOG);
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel cardPanel = new JPanel(cardLayout);
    private static final HomePage HOME_PAGE = new HomePage();
    private static final SortingManager sortingManager = new SortingManager();
    private static final LinkedListImplementation linkedListPanel= new LinkedListImplementation();
    private static final LinkedListCycleDetection cycleDetectionPanel = new LinkedListCycleDetection();
    private static final StackWindowUsable stackWindow=new StackWindowUsable();
    private static final SimpleQueueWindow simpleQueueWindow = new SimpleQueueWindow();
    private static final CircularQueueWindow circularQueueWindow = new CircularQueueWindow();
    private static final PriorityQueueWindow priorityQueueWindow = new PriorityQueueWindow();
    private static JFrame jFrame;
    private static final Font menuFont=new Font(Font.SANS_SERIF, Font.BOLD, 18);
    static {
        menuBarMain.setBorderPainted(false);
        menuBarMain.setVisible(false);
        menuBarMain.setForeground(foregroundColor);
        menuBarMain.setBackground(backgroundColor);
        sortingMenu.setForeground(foregroundColor);
        stackMenu.setForeground(foregroundColor);
        stackMenu.setBackground(backgroundColor);
        sortingMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        for (int i = 0; i < sortingMenuItems.length; i++) {
            sortingMenuItems[i] = new JMenuItem(SORTING_ARRAY[i]);
            sortingMenu.add(sortingMenuItems[i]).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            sortingMenuItems[i].setBackground(backgroundColor);
            sortingMenuItems[i].setForeground(foregroundColor);
            sortingMenuItems[i].setFont(menuFont);
        }
        cardPanel.add(new SortingManager());
        // Add menu items to their respective menus
        stackMenu.add(stackMenuItem);
        linkedListMenu.add(linkedListMenuItem);
        linkedListMenu.add(cycleDetectionMenuItem);
        queueMenu.add(simpleQueueMenuItem);
        queueMenu.add(circularQueueMenuItem);
        queueMenu.add(priorityQueueMenuItem);
        
        // Add panels to card layout
        cardPanel.add(linkedListPanel, LINKED_LIST);
        cardPanel.add(cycleDetectionPanel, CYCLE_DETECTION);
        cardPanel.add(HOME_PAGE, DEFAULT);
        cardPanel.add(stackWindow, STACK);
        cardPanel.add(simpleQueueWindow, SIMPLE_QUEUE);
        cardPanel.add(circularQueueWindow, CIRCULAR_QUEUE);
        cardPanel.add(priorityQueueWindow, PRIORITY_QUEUE);

        // Style menu items
        linkedListMenuItem.setFont(menuFont);
        cycleDetectionMenuItem.setFont(menuFont);
        stackMenuItem.setFont(menuFont);
        simpleQueueMenuItem.setFont(menuFont);
        circularQueueMenuItem.setFont(menuFont);
        priorityQueueMenuItem.setFont(menuFont);

        // Style menus
        linkedListMenu.setBackground(backgroundColor);
        linkedListMenu.setForeground(foregroundColor);
        queueMenu.setBackground(backgroundColor);
        queueMenu.setForeground(foregroundColor);
        queueMenu.setFont(menuFont);
        
        // Style menu items colors
        linkedListMenuItem.setBackground(backgroundColor);
        linkedListMenuItem.setForeground(foregroundColor);
        cycleDetectionMenuItem.setBackground(backgroundColor);
        cycleDetectionMenuItem.setForeground(foregroundColor);
        stackMenuItem.setBackground(backgroundColor);
        stackMenuItem.setForeground(foregroundColor);
        simpleQueueMenuItem.setBackground(backgroundColor);
        simpleQueueMenuItem.setForeground(foregroundColor);
        circularQueueMenuItem.setBackground(backgroundColor);
        circularQueueMenuItem.setForeground(foregroundColor);
        priorityQueueMenuItem.setBackground(backgroundColor);
        priorityQueueMenuItem.setForeground(foregroundColor);
        
        // Set cursors
        linkedListMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cycleDetectionMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stackMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        simpleQueueMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        circularQueueMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        priorityQueueMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateDialog.setBackground(backgroundColor);
        updateDialog.setForeground(foregroundColor);
        updateDialog.setFont(menuFont);
        updateDialog.add(updateDialogMenuItem);
        updateDialogMenuItem.setForeground(foregroundColor);
        updateDialogMenuItem.setBackground(backgroundColor);
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
                sortingManager.switchAlgorithm(item.getText());
            });
        }
        linkedListMenuItem.addActionListener(_ -> {
            closeChildWindows();
            cardLayout.show(cardPanel, LINKED_LIST);
        });
        cycleDetectionMenuItem.addActionListener(_ -> {
            closeChildWindows();
            cardLayout.show(cardPanel, CYCLE_DETECTION);
        });
        HOME_PAGE.returnControlOfLoadButton().addActionListener(_ -> {
            closeQRWindow();
            menuBarMain.setVisible(true);
            sortingManager.switchAlgorithm(SELECTION_SORTING);
        });
        stackMenuItem.addActionListener(_ -> {
            closeChildWindows();
            cardLayout.show(cardPanel, STACK);
        });
        simpleQueueMenuItem.addActionListener(_ -> {
            closeChildWindows();
            cardLayout.show(cardPanel, SIMPLE_QUEUE);
        });
        circularQueueMenuItem.addActionListener(_ -> {
            closeChildWindows();
            cardLayout.show(cardPanel, CIRCULAR_QUEUE);
        });
        priorityQueueMenuItem.addActionListener(_ -> {
            closeChildWindows();
            cardLayout.show(cardPanel, PRIORITY_QUEUE);
        });
        updateDialogMenuItem.addActionListener(_ -> {
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
            jFrame.setSize(width,height);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setBackground(backgroundColor);
            jFrame.setForeground(foregroundColor);
            backgroundMusicThread.start();
            jFrame.add(cardPanel, BorderLayout.CENTER);
            jFrame.setLayout(cardLayout);
            jFrame.setJMenuBar(menuBarMain);
            cardLayout.show(cardPanel, DEFAULT);
            jFrame.setVisible(true);
        });
    }
}