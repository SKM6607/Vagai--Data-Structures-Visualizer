package main.searching;

import main.interfaces.TreeInterface;

import javax.swing.*;
import java.awt.*;

import static main.interfaces.MacroInterface.A_STAR_ALGORITHM;
import static main.interfaces.TreeLightWeightInterface.Tree;

final class AStarSearchAlgorithm extends SearchingAlgorithm {
    AStarSearchAlgorithm(Tree tree, TreeInterface.TreeNode target) {
        super(tree, A_STAR_ALGORITHM, target);
    }

    @Override
    protected TreeInterface.TreeNode search(TreeInterface.TreeNode node) {
        return null;
    }
}

final class AStarSearchVisual extends SearchingVisual {
    private static AStarSearchVisual singleton;

    private AStarSearchVisual(Tree tree) {
        super(tree, new AStarSearchAlgorithm(tree, null));
    }

    public static AStarSearchVisual initialize() {
        return (singleton == null) ? singleton = new AStarSearchVisual(new Tree()) : singleton;
    }

    protected void drawTree(Graphics2D g) {

    }

    @Override
    protected void searchForNode(TreeInterface.TreeNode node) {
        algorithm = new AStarSearchAlgorithm(tree, node);
        algorithm.execute();
    }



}

public class AStarSearchWindow extends SearchingWindow {
    private static AStarSearchWindow singleton;

    protected AStarSearchWindow(SearchingVisual searchingVisual) {
        super(searchingVisual);

    }

    public static AStarSearchWindow createAStarSearchWindow() {
        return (singleton == null) ? singleton = new AStarSearchWindow(AStarSearchVisual.initialize()) : singleton;
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