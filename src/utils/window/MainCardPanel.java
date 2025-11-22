package utils.window;
import main.linkedList.LinkedListCycleDetection;
import main.linkedList.LinkedListImplementation;
import main.pages.HomePage;
import main.queues.CircularQueueWindow;
import main.queues.PriorityQueueWindow;
import main.queues.SimpleQueueWindow;
import main.sorting.SortingWindow;
import main.stack.StackWindowUsable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import static main.interfaces.MacroInterface.*;
public final class MainCardPanel extends JPanel {
    private final MainMenuBar menuBarMain = MainMenuBar.getInstance(this);
    private static final HomePage homePage = new HomePage();
    private static final SortingWindow SORTING_WINDOW = new SortingWindow();
    private static final LinkedListImplementation linkedListPanel= new LinkedListImplementation();
    private static final LinkedListCycleDetection cycleDetectionPanel = new LinkedListCycleDetection();
    private static final StackWindowUsable stackWindow=new StackWindowUsable();
    private static final SimpleQueueWindow simpleQueueWindow = new SimpleQueueWindow();
    private static final CircularQueueWindow circularQueueWindow = new CircularQueueWindow();
    private static final PriorityQueueWindow priorityQueueWindow = new PriorityQueueWindow();
    private MainCardPanel(JFrame parentFrame) {
        super(new CardLayout());
        parentFrame.setJMenuBar(menuBarMain);
        add(linkedListPanel, LINKED_LIST);
        add(cycleDetectionPanel, CYCLE_DETECTION);
        add(homePage, DEFAULT);
        add(stackWindow, STACK);
        add(simpleQueueWindow, SIMPLE_QUEUE);
        add(circularQueueWindow, CIRCULAR_QUEUE);
        add(priorityQueueWindow, PRIORITY_QUEUE);
    }
    @Contract("_ -> new")
    public static @NotNull MainCardPanel getInstance(JFrame parentFrame){
        return new MainCardPanel(parentFrame);
    }
}
