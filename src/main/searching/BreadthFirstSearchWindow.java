package main.searching;

import java.awt.*;

import static main.interfaces.TreeLightWeightInterface.Tree;
import static main.interfaces.MacroInterface.*;
final class BreadthFirstSearchAlgorithm extends SearchingAlgorithm {

    BreadthFirstSearchAlgorithm(Tree tree) {
        super(tree,BFS_ALGORITHM);
    }

    @Override
    int search(int value) {
        return 0;
    }
}

final class BreadthFirstSearchVisual extends SearchingVisual {
    private static BreadthFirstSearchVisual singleton;

    private BreadthFirstSearchVisual(Tree tree) {
        super(tree, new BreadthFirstSearchAlgorithm(tree));
    }

    public static BreadthFirstSearchVisual initialize() {
        return (singleton == null) ? singleton = new BreadthFirstSearchVisual(new Tree()) : singleton;
    }

    @Override
    protected void drawSearch(Graphics2D g) {

    }
}

public final class BreadthFirstSearchWindow extends SearchingWindow {
    private static BreadthFirstSearchWindow singleton;

    private BreadthFirstSearchWindow(SearchingVisual searchingVisual) {
        super(searchingVisual);
    }

    public static BreadthFirstSearchWindow createBreadthFirstSearchWindow() {
        return (singleton == null) ? singleton = new BreadthFirstSearchWindow(BreadthFirstSearchVisual.initialize()) : singleton;
    }
}