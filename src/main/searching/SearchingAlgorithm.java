package main.searching;
import main.interfaces.TreeInterface;
import main.interfaces.TreeLightWeightInterface.Tree;
import javax.swing.*;

public abstract class SearchingAlgorithm {
    protected String algorithmName;
    protected Tree tree;
    protected SearchingAlgorithm(Tree tree, String algorithmName) {
        this.tree = tree;
        this.algorithmName = algorithmName;
    }
    // TODO FIGURE OUT HOW TO START SEARCHING FOR SET GOAL STATE?
    protected SwingWorker<TreeInterface.TreeNode, TreeInterface.TreeNode> startSearchAlgorithm;
    protected void startSearch(){
        startSearchAlgorithm.execute();
    }
}