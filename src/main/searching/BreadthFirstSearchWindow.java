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
        Tree tree=new Tree();
        /*
        TreeInterface.TreeNode right=new TreeInterface.TreeNode(12);
        TreeInterface.TreeNode left=new TreeInterface.TreeNode(12);
        TreeInterface.TreeNode right1=new TreeInterface.TreeNode(12);
        TreeInterface.TreeNode left1=new TreeInterface.TreeNode(12);
        tree.extendTree(tree.root,right, TreeInterface.TreeNode.NodeDirection.RIGHT);
        tree.extendTree(tree.root,left, TreeInterface.TreeNode.NodeDirection.LEFT);
        tree.extendTree(tree.root.getRight(),right1, TreeInterface.TreeNode.NodeDirection.RIGHT);
        tree.extendTree(tree.root.getLeft(),left1, TreeInterface.TreeNode.NodeDirection.LEFT);*/
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

    public static BreadthFirstSearchWindow createBreadthFirstSearchWindow() {
        return (singleton == null) ? singleton = new BreadthFirstSearchWindow(BreadthFirstSearchVisual.initialize()) : singleton;
    }
}