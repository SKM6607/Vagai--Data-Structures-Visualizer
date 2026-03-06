package main.searching;

import main.interfaces.TreeInterface;

import javax.swing.*;
import java.awt.*;

import static main.interfaces.TreeLightWeightInterface.Tree;
import static main.interfaces.MacroInterface.*;
final class BreadthFirstSearchAlgorithm extends SearchingAlgorithm {

    BreadthFirstSearchAlgorithm(Tree tree) {
        super(tree,BFS_ALGORITHM);
    }


    @Override
    protected SwingWorker<TreeInterface.TreeNode, TreeInterface.TreeNode> returnSwingWorker() {
        return null;
    }
}

final class BreadthFirstSearchVisual extends SearchingVisual {
    private static BreadthFirstSearchVisual singleton;

    private BreadthFirstSearchVisual(Tree tree) {
        super(tree, new BreadthFirstSearchAlgorithm(tree));
    }

    public static BreadthFirstSearchVisual initialize() {
        Tree tree=new Tree();
        return (singleton == null) ? singleton = new BreadthFirstSearchVisual(tree) : singleton;
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

    @Override
    protected JPanel setupDetailsPanel() {
        return null;
    }

    @Override
    protected JPanel setupLegendPanel() {
        return null;
    }

    public static BreadthFirstSearchWindow createBreadthFirstSearchWindow() {
        return (singleton == null) ? singleton = new BreadthFirstSearchWindow(BreadthFirstSearchVisual.initialize()) : singleton;
    }
}