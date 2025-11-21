package utils;
import main.dialogs.LegendDialog;
import main.dialogs.QRCodeDisplayer;
import main.dialogs.UpdateDialog;
import main.linkedList.LinkedListCycleDetection;
import main.linkedList.LinkedListImplementation;
import main.pages.HomePage;
import main.queues.CircularQueueWindow;
import main.queues.PriorityQueueWindow;
import main.queues.SimpleQueueWindow;
import main.sorting.SortingManager;
import main.stack.StackWindowUsable;
import javax.swing.*;
import java.awt.*;
import static main.interfaces.MacroInterface.*;
public final class MainCardPanel extends JPanel {
    private final MainMenuBar menuBarMain = MainMenuBar.getInstance(this);
    private static final HomePage homePage = new HomePage();
    private static final SortingManager sortingManager = new SortingManager();
    private static final LinkedListImplementation linkedListPanel= new LinkedListImplementation();
    private static final LinkedListCycleDetection cycleDetectionPanel = new LinkedListCycleDetection();
    private static final StackWindowUsable stackWindow=new StackWindowUsable();
    private static final SimpleQueueWindow simpleQueueWindow = new SimpleQueueWindow();
    private static final CircularQueueWindow circularQueueWindow = new CircularQueueWindow();
    private static final PriorityQueueWindow priorityQueueWindow = new PriorityQueueWindow();
    private MainCardPanel() {
        super(new CardLayout());
        add(linkedListPanel, LINKED_LIST);
        add(cycleDetectionPanel, CYCLE_DETECTION);
        add(homePage, DEFAULT);
        add(stackWindow, STACK);
        add(simpleQueueWindow, SIMPLE_QUEUE);
        add(circularQueueWindow, CIRCULAR_QUEUE);
        add(priorityQueueWindow, PRIORITY_QUEUE);
    }
}
