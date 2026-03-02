package main.searching;

import lombok.Getter;
import main.base_panels.VisualizerGridPanel;
import main.interfaces.NodeListener;
import main.interfaces.TreeInterface;

import javax.naming.CannotProceedException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private int fontOffset;
    private TreeInterface.TreeNode target;
    @Getter
    private TreeInterface.TreeNode selectedNode;
    private static final Random random=new Random();
    protected SearchingVisual(Tree tree, SearchingAlgorithm algorithm) {
        this.tree = tree;
        this.algorithm = algorithm;
        setBackground(defaultBackgroundColor);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    }
    /**
     * Randomizes Tree based on range parameter
     *
     * @param range Range of the random values (-range,range)
     * @param depth Maximum level of depth Allowed till 3
     *
     */
    public void randomizeTree(int range) throws CannotProceedException {
        tree.treeReset();
        if (range > 999 || range < -999 || range == 0)
            throw new CannotProceedException("Cannot Randomize tree as the inputs are invalid. Kindly provide valid inputs");
        int depth=random.nextInt(0,3);
        randomizeHelper(tree.root, range, depth);
        Tree.alignAllTreeNodes(tree);
        repaint();
    }

    private void randomizeHelper(TreeInterface.TreeNode node, int range, int depth) {
        if (depth == 0) return;
        int rInt = random.nextInt(-range, range);
        int lInt = random.nextInt(-range, range);
        if(random.nextBoolean()) {
            node.setRight(new TreeInterface.TreeNode(rInt));
            randomizeHelper(node.getRight(), range, depth - 1);
        }
        if(random.nextBoolean()) {
            node.setLeft(new TreeInterface.TreeNode(lInt));
            randomizeHelper(node.getLeft(), range, depth - 1);
        }
    }

    public void addNodeListener(NodeListener listener) {
        listenerList.add(listener);
    }

    protected void fireNodeSelected(TreeInterface.TreeNode node) {
        for (NodeListener l : listenerList) {
            l.onSelected(node);
        }
    }

    public void extendTreeAtSelectedNode(TreeInterface.TreeNode node, int val) {
        TreeInterface.TreeNode right = node.getRight();
        TreeInterface.TreeNode child = new TreeInterface.TreeNode(val);
        if (node.getRight() != null && node.getLeft() != null) {
            throw new IndexOutOfBoundsException("The Selected Node is Full");
        }
        if (right != null) {
            tree.extendTree(node, child, TreeInterface.TreeNode.NodeDirection.LEFT);
        } else {
            tree.extendTree(node, child, TreeInterface.TreeNode.NodeDirection.RIGHT);
        }
        repaint();
    }

    public void removeSelectedNode(TreeInterface.TreeNode node) throws CannotProceedException {
        if (node == tree.root) {
            throw new CannotProceedException("Cannot delete Root Node");
        }
        tree.removeNode(node);
        repaint();
    }

    public void setGoalState(TreeInterface.TreeNode node) {
        if (target != null) {
            target.setNodeColor(backgroundColor);
            target.setTextColor(Color.WHITE);
        }
        goalStateFinder(tree.root, node);
        target.setNodeColor(Color.GREEN);
        target.setTextColor(Color.BLACK);
        repaint();
    }

    private void goalStateFinder(TreeInterface.TreeNode node, TreeInterface.TreeNode target) {
        if (node == null) {
            return;
        }
        if (node == target) {
            this.target = target;
            return;
        }
        goalStateFinder(node.getLeft(), target);
        goalStateFinder(node.getRight(), target);
    }

    public void updateNode(TreeInterface.TreeNode node,int val){
        tree.updateNode(node,val);
        repaint();
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
        //TODO TRY MAKING THEM ARROWS IF U PREFER
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
        int parentX = node.getXPos() + nodeRadius - 1, parentY = node.getYPos() + nodeRadius + 1;
        if (!node.isLastNode()) {
            TreeInterface.TreeNode right = node.getRight();
            TreeInterface.TreeNode left = node.getLeft();
            if (left != null) {
                g.drawLine(parentX, parentY, left.getXPos() + nodeRadius - 1, left.getYPos());
            }
            if (right != null) {
                g.drawLine(parentX, parentY, right.getXPos() + nodeRadius - 1, right.getYPos());
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
        if (distance <= nodeRadius * nodeRadius) {
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