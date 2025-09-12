import Sound.BackgroundMusic;
import Windows.Interfaces.SortingAlgorithmsInterface;
import Windows.LegendDialog;
import Windows.LinkedList;
import Windows.LoadingPage;
import Windows.Sorting;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main implements SortingAlgorithmsInterface {
    static final Thread backgroundMusicThread = new Thread(new BackgroundMusic("src/Sound/Granius.wav"));
    private static final JMenuBar menuBarMain = new JMenuBar();
    private static final JMenu sortingMenu = new JMenu("Sorting");
    private static final JMenu linkedListMenu = new JMenu("Linked List");
    private static final JMenuItem[] sortingMenuItems = new JMenuItem[4];
    private static final JMenuItem linkedListMenuItem = new JMenuItem(LINKED_LIST);
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel cardPanel = new JPanel(cardLayout);
    private static final LoadingPage loadingPage = new LoadingPage();
    private static final Sorting sortingPanel = new Sorting(width, height, SELECTION_SORTING);
    private static final LinkedList linkedListPanel=new LinkedList();
    static {
        menuBarMain.setVisible(false);
        for (int i = 0; i < sortingMenuItems.length; i++) {
            sortingMenuItems[i] = new JMenuItem(IDENTIFIER_ARRAY[i]);
            cardPanel.add(new Sorting(width,height , IDENTIFIER_ARRAY[i]), IDENTIFIER_ARRAY[i]);
            sortingMenu.add(sortingMenuItems[i]);
            sortingMenuItems[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        }
        cardPanel.add(linkedListPanel,LINKED_LIST);
        cardPanel.add(loadingPage, DEFAULT);
        linkedListMenuItem.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        linkedListMenu.add(linkedListMenuItem);
        menuBarMain.add(sortingMenu);
        menuBarMain.add(linkedListMenu);
        sortingMenu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        linkedListMenu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
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
            cardLayout.show(cardPanel, LINKED_LIST);
        });
        loadingPage.returnControlOfLoadButton().addActionListener(_ -> {
            menuBarMain.setVisible(true);
            cardLayout.show(cardPanel, SELECTION_SORTING);
            sortingPanel.invokeLegend();
        });
    }
    private static void closeChildWindows() {
        for (Window window : Window.getWindows()) {
            if (window instanceof LegendDialog) {
                window.dispose();
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException _) {
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame jFrame = new JFrame("VAGAI");
            jFrame.setSize(width,height);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            backgroundMusicThread.start();
            jFrame.add(cardPanel, BorderLayout.CENTER);
            jFrame.setLayout(cardLayout);
            jFrame.setJMenuBar(menuBarMain);
            cardLayout.show(cardPanel, DEFAULT);
            jFrame.setVisible(true);
        });
    }
}