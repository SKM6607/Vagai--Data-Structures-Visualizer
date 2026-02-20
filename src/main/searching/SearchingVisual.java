package main.searching;

import main.base_panels.VisualizerGridPanel;

import java.awt.*;

import static main.interfaces.TreeLightWeightInterface.Tree;

public sealed abstract class SearchingVisual
        extends VisualizerGridPanel
        permits
        BreadthFirstSearchVisual,
        DepthFirstSearchVisual,
        AStarSearchVisual {
    protected final Tree tree;
    protected final int MAX_WIDTH = width, MAX_HEIGHT = 3 * height / 4;
    public SearchingAlgorithm algorithm;

    protected SearchingVisual(Tree tree, SearchingAlgorithm algorithm) {
        this.tree = tree;
        this.algorithm = algorithm;
        setBackground(defaultBackgroundColor);
        setPreferredSize(new Dimension(MAX_WIDTH,MAX_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        paintGrid(g);
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