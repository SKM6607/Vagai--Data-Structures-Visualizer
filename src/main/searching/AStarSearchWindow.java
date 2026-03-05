package main.searching;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static main.interfaces.TreeLightWeightInterface.Tree;

import static main.interfaces.MacroInterface.*;
final class AStarSearchAlgorithm extends SearchingAlgorithm {
    AStarSearchAlgorithm(Tree tree) {
        super(tree,A_STAR_ALGORITHM);
    }
    @Override
    int search(int value) {
        return 0;
    }
}

final class AStarSearchVisual extends SearchingVisual {
    private static AStarSearchVisual singleton;

    private AStarSearchVisual(Tree tree) {
        super(tree, new AStarSearchAlgorithm(tree));
    }

    public static AStarSearchVisual initialize() {
        return (singleton == null) ? singleton = new AStarSearchVisual(new Tree()) : singleton;
    }
    @Override
    protected void drawSearch(Graphics2D g) {

    }


}

public class AStarSearchWindow extends SearchingWindow {
    private static AStarSearchWindow singleton;

    protected AStarSearchWindow(SearchingVisual searchingVisual) {
        super(searchingVisual);

    }

    @Override
    protected JPanel setupDetailsPanel() {
        return null;
    }

    @Override
    protected JPanel setupLegendPanel() {
        return null;
    }

    public static AStarSearchWindow createAStarSearchWindow() {
        return (singleton == null) ? singleton = new AStarSearchWindow(AStarSearchVisual.initialize()) : singleton;
    }
}