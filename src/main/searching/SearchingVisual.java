package main.searching;

import lombok.Getter;
import main.base_panels.VisualizerGridPanel;
import main.interfaces.NodeListener;
import main.interfaces.TreeInterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import static main.interfaces.TreeInterface.nodeRadius;
import static main.interfaces.TreeLightWeightInterface.Tree;

public sealed abstract class SearchingVisual
        extends VisualizerGridPanel
        implements MouseMotionListener, MouseListener
        permits
        BreadthFirstSearchVisual,
        DepthFirstSearchVisual,
        AStarSearchVisual {
    protected final Tree tree;
    protected final int MAX_WIDTH = width, MAX_HEIGHT = 3 * height / 4;
    private final List<NodeListener> listenerList = new ArrayList<>();
    public SearchingAlgorithm algorithm;
    private boolean mouseInRange = false;
    private int fontOffset;
    @Getter
    private TreeInterface.TreeNode selectedNode;

    protected SearchingVisual(Tree tree, SearchingAlgorithm algorithm) {
        this.tree = tree;
        this.algorithm = algorithm;
        setBackground(defaultBackgroundColor);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    }

    public void addNodeListener(NodeListener listener) {
        listenerList.add(listener);
    }

    protected void fireNodeSelected(TreeInterface.TreeNode node) {
        for (NodeListener l : listenerList) {
            l.onSelected(node);
        }
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
        if (fontOffset == 0) this.fontOffset = g.getFontMetrics().stringWidth("Binary Tree");
        var oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(2.0f));
        g.setFont(menuFont.deriveFont(24.0f));
        drawTree(g, tree.root);
        g.setStroke(oldStroke);
    }

    private void drawTree(Graphics2D g, TreeInterface.TreeNode node) {
        if (node == null) return;
        drawNode(g, node);
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

    private void drawNode(Graphics2D g, TreeInterface.TreeNode node) {
        g.setColor(node.getNodeColor());
        var drawWithOffsets = node.getXPos() + fontOffset / 2;
        g.fillOval(drawWithOffsets, node.getYPos(), nodeRadius, nodeRadius);
        g.setColor(node.getTextColor());
        String data = (node.data != 0) ? String.valueOf(node.data) : "ROOT";
        var fontOffsetX = g.getFontMetrics().stringWidth(data);
        var fontOffsetY = g.getFontMetrics().getAscent();
        g.drawString(data, drawWithOffsets + (nodeRadius - fontOffsetX) / 2, node.getYPos() + nodeRadius / 2 + fontOffsetY / 2);
        g.setColor(Color.YELLOW);
        g.drawOval(drawWithOffsets, node.getYPos(), nodeRadius + 1, nodeRadius + 1);
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
        var oldColor = g.getColor();
        g.setColor(Color.WHITE);
        g.setFont(menuFont.deriveFont(22.0f));
        g.drawString("Binary Tree", width / 2 - fontOffset, 25);
        g.setColor(oldColor);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        TreeInterface.TreeNode node = returnNodeOnCursorIntersectionWithAnyNode(x, y, tree.root);
        if (node != null) {
            selectedNode = node;
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            node.setNodeColor(Color.YELLOW);
            node.setTextColor(Color.BLACK);
        } else {
            if (selectedNode != null) {
                this.setCursor(Cursor.getDefaultCursor());
                selectedNode.setNodeColor(backgroundColor);
                selectedNode.setTextColor(Color.WHITE);
            }
        }
        repaint();
    }

    TreeInterface.TreeNode returnNodeOnCursorIntersectionWithAnyNode(int x, int y, TreeInterface.TreeNode node) {
        if (node == null) {
            return null;
        }
        int centerX = node.getXPos() + nodeRadius / 2;
        int centerY = node.getYPos() + nodeRadius / 2;
        double distance = Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2);
        mouseInRange = distance <= nodeRadius * nodeRadius;
        if (mouseInRange) {
            return node;
        }
        TreeInterface.TreeNode intersectsInLeft = returnNodeOnCursorIntersectionWithAnyNode(x, y, node.getLeft());
        TreeInterface.TreeNode intersectsInRight = returnNodeOnCursorIntersectionWithAnyNode(x, y, node.getRight());
        return (intersectsInLeft != null) ? intersectsInLeft : intersectsInRight;
    }

    protected abstract void drawSearch(Graphics2D g);

    @Override
    public void mouseClicked(MouseEvent e) {
        if (selectedNode != null && returnNodeOnCursorIntersectionWithAnyNode(e.getX(), e.getY(), tree.root) == selectedNode) {
            fireNodeSelected(selectedNode);
            selectedNode = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}