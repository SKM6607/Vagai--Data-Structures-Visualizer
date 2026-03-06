package main.searching;

import main.interfaces.TreeInterface;
import main.interfaces.TreeLightWeightInterface.Tree;

import javax.swing.*;

public abstract class SearchingAlgorithm extends SwingWorker<TreeInterface.TreeNode, TreeInterface.TreeNode> {
    protected final String algorithmName;
    protected final Tree tree;
    protected TreeInterface.TreeNode target;

    protected SearchingAlgorithm(Tree tree, String algorithmName, TreeInterface.TreeNode target) {
        this.tree = tree;
        this.algorithmName = algorithmName;
        if (target == null) {
            //TODO IN NON SEARCHING STATE OR INITIAL STATE
            return;
        }
        this.target = target;
    }

    protected abstract TreeInterface.TreeNode search(TreeInterface.TreeNode node) throws InterruptedException; //TODO IMPLEMENT SEARCH

    @Override
    protected TreeInterface.TreeNode doInBackground() throws Exception {

        return search(target);
    }

}