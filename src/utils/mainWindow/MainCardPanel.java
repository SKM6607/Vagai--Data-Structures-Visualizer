package utils.mainWindow;

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
    private static final SortingWindow sortingWindow = new SortingWindow();
    private static final LinkedListImplementation linkedListPanel = new LinkedListImplementation();
    private static final LinkedListCycleDetection cycleDetectionPanel = new LinkedListCycleDetection();
    private static final StackWindowUsable stackWindow = new StackWindowUsable();
    private static final SimpleQueueWindow simpleQueueWindow = new SimpleQueueWindow();
    private static final CircularQueueWindow circularQueueWindow = new CircularQueueWindow();
    private static final PriorityQueueWindow priorityQueueWindow = new PriorityQueueWindow();
    private static MainCardPanel singleTon = null;
    public final CardLayout cardLayout = new CardLayout();
    private final HomePage homePage = HomePage.getInstance(this);

    private MainCardPanel() {
        setLayout(cardLayout);
        add(homePage, DEFAULT);
        cardLayout.show(this, DEFAULT);
        add(sortingWindow, SORTING_ALGORITHMS);
        add(linkedListPanel, LINKED_LIST);
        add(cycleDetectionPanel, CYCLE_DETECTION);
        add(stackWindow, STACK);
        add(simpleQueueWindow, SIMPLE_QUEUE);
        add(circularQueueWindow, CIRCULAR_QUEUE);
        add(priorityQueueWindow, PRIORITY_QUEUE);
    }

    @Contract("_ -> new")
    public static @NotNull MainCardPanel getInstance() {
        if (singleTon == null) singleTon = new MainCardPanel();
        return singleTon;
    }

    public void setMenuBarForAppearance(MainMenuBar menuBar) {
        homePage.setOnStartTask(() -> menuBar.setVisible(true));
    }
}
