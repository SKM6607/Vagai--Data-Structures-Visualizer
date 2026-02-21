package main.searching;

import main.interfaces.MacroInterface;
import main.interfaces.TreeLightWeightInterface.Tree;

import java.util.concurrent.Callable;

public abstract class SearchingAlgorithm {
    protected String algorithmName = MacroInterface.SEARCHING_ALGORITHMS;
    protected Tree tree;
    protected SearchingAlgorithm(Tree tree,String algorithmName){
        this.tree=tree;
        this.algorithmName=algorithmName;
    }

    abstract int search(int value);

    Object[] timeTaken(Callable<Integer> function) throws Exception {
        var t1 = System.currentTimeMillis();
        int position = function.call();
        var t2 = System.currentTimeMillis();
        return new Object[]{position, (t2 - t1) / 1e3};
    }
}