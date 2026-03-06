package main.searching;

import main.interfaces.TreeInterface;

import javax.swing.*;
import java.awt.*;

import static main.interfaces.MacroInterface.DFS_ALGORITHM;
import static main.interfaces.TreeLightWeightInterface.Tree;

final class DepthFirstSearch extends SearchingAlgorithm {

    DepthFirstSearch(Tree tree, TreeInterface.TreeNode target) {
        super(tree, DFS_ALGORITHM, target);
    }

    @Override
    protected TreeInterface.TreeNode search(TreeInterface.TreeNode node) {
        if (node == null || node == target) return node;
        publish(node);
        TreeInterface.TreeNode left = search(node.getLeft());
        if (left != null) return left;
        return search(node.getRight());
    }
}

final class DepthFirstSearchVisual
        extends SearchingVisual {
    private static DepthFirstSearchVisual singleton;

    private DepthFirstSearchVisual(Tree tree) {
        super(tree, new DepthFirstSearch(tree, null));
    }

    public static DepthFirstSearchVisual initialize() {
        return (singleton == null) ? singleton = new DepthFirstSearchVisual(new Tree()) : singleton;
    }


    @Override
    protected void searchForNode(TreeInterface.TreeNode node) {
        algorithm = new DepthFirstSearch(tree, node);
        algorithm.execute();
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

    @Override
    protected JPanel setupDetailsPanel() {
        return null;
    }

    @Override
    protected JPanel setupLegendPanel() {
        return null;
    }
}