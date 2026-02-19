package main.searching;

import javax.swing.*;
import java.awt.*;
public abstract class SearchingWindow
        extends JPanel {
    protected SearchingVisual searchingVisual;
    protected JPanel controlPanel;

    protected SearchingWindow(SearchingVisual searchingVisual) {
        this.searchingVisual = searchingVisual;
        add(searchingVisual, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

}
