package main.searching;

import main.interfaces.GridInterface;
import main.interfaces.TreeLightWeightInterface;

import javax.swing.*;
import java.awt.*;

public sealed abstract class SearchingVisual
        extends JPanel
        implements GridInterface
        permits
        BreadthFirstSearchVisual,
        DepthFirstSearchVisual,
        AStarSearchVisual
{
    protected final TreeLightWeightInterface.Tree tree;
    protected SearchingAlgorithm algorithm;

    protected SearchingVisual(TreeLightWeightInterface.Tree tree, SearchingAlgorithm algorithm) {
        this.tree = tree;
        this.algorithm = algorithm;
    }

    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        drawGrid(g, g.getColor());
        drawTree(g);
        drawArrows(g);
        drawText(g);
        drawSearch(g);
    }

    protected void drawTree(Graphics2D g) {
        //TODO DEFAULT DRAWING FOR TREES
    }

    protected void drawArrows(Graphics2D g) {
        //TODO DEFAULT DRAWING FOR ARROWS
    }

    protected void drawText(Graphics2D g) {
        //TODO DEFAULT DRAWING FOR TEXT
    }

    protected abstract void drawSearch(Graphics2D g);
}