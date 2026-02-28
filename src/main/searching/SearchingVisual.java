package main.searching;

import main.base_panels.VisualizerGridPanel;
import main.interfaces.TreeInterface;

import java.awt.*;

import static main.interfaces.TreeInterface.nodeRadius;
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
    private int fontOffset;

    protected SearchingVisual(Tree tree, SearchingAlgorithm algorithm) {
        this.tree = tree;
        this.algorithm = algorithm;
        setBackground(defaultBackgroundColor);
        setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
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
        if (fontOffset == 0) this.fontOffset = g.getFontMetrics().stringWidth("Binary Tree");
        var oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(2.0f));
        g.setFont(menuFont.deriveFont(20.0f));
        drawTree(g, tree.root);
        g.setStroke(oldStroke);
    }

    private void drawTree(Graphics2D g, TreeInterface.TreeNode node) {
        if (node == null) return;
        g.setColor(node.getNodeColor());
        var drawWithOffsets = node.getXPos() + fontOffset / 2;
        g.fillOval(drawWithOffsets, node.getYPos(), nodeRadius, nodeRadius);
        g.setColor(Color.WHITE);
        String data = (node.data != 0) ? String.valueOf(node.data) : "ROOT";
        var fontOffsetX = g.getFontMetrics().stringWidth(data);
        var fontOffsetY = g.getFontMetrics().getAscent();
        g.drawString(data, drawWithOffsets + (nodeRadius - fontOffsetX) / 2, node.getYPos() + nodeRadius / 2 + fontOffsetY / 2);
        g.setColor(Color.YELLOW);
        g.drawOval(drawWithOffsets, node.getYPos(), nodeRadius + 1, nodeRadius + 1);
        drawTree(g, node.getRight());
        drawTree(g, node.getLeft());
    }

    protected void drawArrows(Graphics2D g) {
        //TODO DEFAULT DRAWING FOR ARROWS
        var oldColor = g.getColor();
        var oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.YELLOW);
        drawArrowsHelper(g, tree.root);
        g.setColor(oldColor);
        g.setStroke(oldStroke);
    }

    protected void drawArrowsHelper(Graphics2D g, TreeInterface.TreeNode node) {
        if (node == null) return;
        int parentX = node.getXPos() + fontOffset, parentY = node.getYPos() + nodeRadius;
        if (!node.isLastNode()) {
            TreeInterface.TreeNode right = node.getRight();
            TreeInterface.TreeNode left = node.getLeft();
            if (left != null) {
                g.drawLine(parentX, parentY, left.getXPos() + nodeRadius, left.getYPos());
            }
            if (right != null) {
                g.drawLine(parentX, parentY, right.getXPos() + nodeRadius, right.getYPos());
            }
        }
        drawArrowsHelper(g, node.getLeft());
        drawArrowsHelper(g, node.getRight());
    }

    protected void drawText(Graphics2D g) {
        //TODO DEFAULT DRAWING FOR TEXT
        var oldColor = g.getColor();
        g.setColor(Color.WHITE);
        g.setFont(menuFont.deriveFont(22.0f));
        g.drawString("Binary Tree", width / 2 - fontOffset, 25);
        g.setColor(oldColor);
    }

    protected abstract void drawSearch(Graphics2D g);
}