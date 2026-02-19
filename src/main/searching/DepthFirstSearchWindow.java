package main.searching;

import java.awt.*;

import static main.interfaces.TreeLightWeightInterface.Tree;

final class DepthFirstSearch extends SearchingAlgorithm {

    DepthFirstSearch(Tree tree) {
        super(tree);
    }

    @Override
    int search(int value) {
        return 0;
    }
}

final class DepthFirstSearchVisual
        extends SearchingVisual {
    private static DepthFirstSearchVisual singleton;
    private DepthFirstSearchVisual(Tree tree) {
        super(tree, new DepthFirstSearch(tree));
    }

    public static DepthFirstSearchVisual initialize() {
        return (singleton == null) ? singleton = new DepthFirstSearchVisual(new Tree()) : singleton;
    }

    @Override
    protected void drawSearch(Graphics2D g) {

    }

}

public final class DepthFirstSearchWindow
        extends SearchingWindow {
    private static DepthFirstSearchWindow singleton;

    private DepthFirstSearchWindow(SearchingVisual searchingVisual) {
        super(searchingVisual);
    }

    public static DepthFirstSearchWindow createDepthFirstSearchWindow() {
        return (singleton == null) ?
                singleton = new DepthFirstSearchWindow(
                        DepthFirstSearchVisual.initialize()
                ) :
                singleton;
    }
}