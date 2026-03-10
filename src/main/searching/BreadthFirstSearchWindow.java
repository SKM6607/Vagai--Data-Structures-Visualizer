package main.searching;

import main.interfaces.TreeInterface;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static main.interfaces.MacroInterface.BFS_ALGORITHM;
import static main.interfaces.TreeLightWeightInterface.Tree;

final class BreadthFirstSearchAlgorithm extends SearchingAlgorithm {

    private final Queue<TreeInterface.TreeNode> queue = new LinkedList<>();

    BreadthFirstSearchAlgorithm(Tree tree, TreeInterface.TreeNode target) {
        super(tree, BFS_ALGORITHM, target);
        queue.add(tree.root);
    }

    @Override
    protected TreeInterface.TreeNode search(TreeInterface.TreeNode node) throws InterruptedException {
        if (node == target || node == null) return node;
        publish(queue.poll());
        Thread.sleep(400);
        queue.add(node);
        if (node.getLeft() != null) return search(node.getLeft());
        if (node.getRight() != null) return search(node.getRight());
        return node;
    }

    @Override
    protected void process(List<TreeInterface.TreeNode> chunks) {
        TreeInterface.TreeNode node = chunks.getLast();
        highlightSelectedNode(node);
        //TODO inga painting pannu illana color maathu
    }

    private void highlightSelectedNode(TreeInterface.TreeNode node){
        node.setNodeColor(Color.GREEN);
        node.setTextColor(Color.BLACK);

    }
}

final class BreadthFirstSearchVisual extends SearchingVisual {
    private static BreadthFirstSearchVisual singleton;

    //TODO ALGO AH MATTUM MATHIDU
    private BreadthFirstSearchVisual(Tree tree) {
        super(tree, new BreadthFirstSearchAlgorithm(tree, null),null);
    }

    public static BreadthFirstSearchVisual initialize() {
        Tree tree = new Tree();
        return (singleton == null) ? singleton = new BreadthFirstSearchVisual(tree) : singleton;
    }

    @Override
    protected void searchForNode(TreeInterface.TreeNode node) {
        algorithm = new BreadthFirstSearchAlgorithm(tree, node);
        algorithm.execute();
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

    @Override
    protected JPanel setupDetailsPanel() {
        return null;
    }

    @Override
    protected JPanel setupLegendPanel() {
        return null;
    }
}