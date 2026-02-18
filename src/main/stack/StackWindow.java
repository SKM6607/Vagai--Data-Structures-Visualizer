package main.stack;

import main.interfaces.DefaultWindowsInterface;
import main.interfaces.GridInterface;
import main.interfaces.LinkedListInterface;
import main.interfaces.StackLightWeightInterface;
import shapes.MyArrow;
import utils.AnimationHelper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StackWindow extends JPanel implements StackLightWeightInterface, GridInterface {

    private final int myWidth = StackLightWeightInterface.width; // Interface constants are static final
    private final int myHeight = StackLightWeightInterface.height;
    private final MyArrow myArrow = new MyArrow(80, 12);
    private LinkedListInterface.VisualNode top;
    private int size = 1;
    private final int nodeHeight = 80;
    private int nodeWidth = 100;
    private final int startX = returnClosest(myWidth / 5, myWidth / 3, SPACING + 5);
    private final int endX = returnClosest(myWidth - 2 * nodeWidth, myWidth - nodeWidth, SPACING + 5);
    private int startY = nodeHeight * 2;
    private int endY = returnClosest(myHeight - 4 * nodeHeight, myHeight - 3 * nodeHeight, SPACING + 5);
    private int dynamicHeight = startY;
    private boolean stopPop = false;
    private boolean isAnimating = false;
    private final int animationSpeed = 15;

    public StackWindow() {
        nodeWidth = endX - startX - 60;
        top = new LinkedListInterface.VisualNode(0, startX + 30, endY - nodeHeight - SPACING);
        setBackground(new Color(0xA0F29));
        setPreferredSize(new Dimension(myWidth, myHeight));
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        drawGrid(g, new Color(0x1C233D));
        drawTitle(g);
        drawBasket(g);
        LinkedListInterface.VisualNode temp = top;
        while (temp != null) {
            drawNode(g, temp);
            temp = temp.getNextNode();
        }
    }

    private void drawTitle(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        g.drawString("Stack Visualization (LIFO)", myWidth / 2 - 170, 40);

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.setColor(new Color(0xFFD700));
        g.drawString("Size: " + size, myWidth / 2 - 40, 70);
    }

    public void setCamCentered(JScrollPane scrollPane) {
        if (isAnimating)
            return;

        SwingUtilities.invokeLater(() -> {
            JViewport viewport = scrollPane.getViewport();
            Rectangle viewRect = viewport.getViewRect();
            int currentX = viewRect.x;
            int contentHeight = getPreferredSize().height;
            int viewportHeight = viewRect.height;
            int targetCenterY = top.yPos + nodeHeight / 2;
            int unclampedY = targetCenterY - viewportHeight / 2;
            int maxY = Math.max(0, contentHeight - viewportHeight);
            int clampedY = Math.max(0, Math.min(unclampedY, maxY));

            int startY = viewRect.y;
            int distance = clampedY - startY;
            int steps = 10;

            // Java Timer is different from Kotlin Timer. Using Swing Timer.
            new Timer(20, new java.awt.event.ActionListener() {
                int currentStep = 0;

                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    currentStep++;
                    float progress = AnimationHelper.easeInOut((float) currentStep / steps);
                    int newY = startY + (int) (distance * progress);
                    viewport.setViewPosition(new Point(currentX, newY));

                    if (currentStep >= steps) {
                        ((Timer) e.getSource()).stop();
                        viewport.setViewPosition(new Point(currentX, clampedY));
                    }
                }
            }).start();
        });
    }

    @Override
    public void drawGrid(Graphics2D g, Color color) {
        Color retColor = g.getColor();
        g.setColor(color);
        for (int i = 0; i < DefaultWindowsInterface.width; i += SPACING) {
            g.drawLine(i, 0, i, getPreferredSize().height);
        }
        for (int i = 0; i < getPreferredSize().height; i += SPACING) {
            g.drawLine(0, i, DefaultWindowsInterface.width, i);
        }
        g.setColor(retColor);
    }

    private void shiftElements() {
        int shiftY = nodeHeight * 4;
        LinkedListInterface.VisualNode temp = top;
        while (temp != null) {
            temp.yPos += shiftY;
            temp = temp.getNextNode();
        }
    }

    private void resizePush() {
        if (top.yPos % myHeight < 20) {
            endY += nodeHeight * 4;
            shiftElements();
        }
        setPreferredSize(new Dimension(myWidth, endY + nodeHeight * 2));
        revalidate();
        repaint();
    }

    @Override
    public void push(int value) {
        if (isAnimating)
            return;

        size++;
        resizePush();
        stopPop = false;

        LinkedListInterface.VisualNode newNode = new LinkedListInterface.VisualNode(value, top.xPos, -nodeHeight);
        newNode.setNextNode(top);
        newNode.setNextAddress(top.getAddress());
        LinkedListInterface.VisualNode oldTop = top;
        top = newNode;

        animatePush(newNode, oldTop.yPos - nodeHeight - 10);
    }

    private void animatePush(LinkedListInterface.VisualNode node, int targetY) {
        isAnimating = true;
        new Timer(animationSpeed, e -> {
            if (node.yPos < targetY) {
                node.yPos += 12;
                repaint();
            } else {
                node.yPos = targetY;
                isAnimating = false;
                ((Timer) e.getSource()).stop();
                repaint();
            }
        }).start();
    }

    @Override
    public int pop() {
        if (isAnimating)
            return top.data;

        if (stopPop) {
            dynamicHeight = myHeight;
            setPreferredSize(new Dimension(myWidth, myHeight));
            scrollRectToVisible(new Rectangle(getPreferredSize()));
            repaint();
            return top.data;
        }

        if (sizeSt() == 1 && !stopPop) {
            stopPop = true;
            dynamicHeight -= nodeHeight * 2;
            animatePop(top, -nodeHeight * 3, true);
            endY -= nodeHeight * 2;
            return top.data;
        }

        int retData = top.data;
        LinkedListInterface.VisualNode nodeToRemove = top;
        top.setNextAddress(null);
        LinkedListInterface.VisualNode temp = top.getNextNode();
        top.setNextNode(null);
        top = temp;
        size--;

        animatePop(nodeToRemove, -nodeHeight * 2, false);

        return retData;
    }

    private void animatePop(LinkedListInterface.VisualNode node, int targetY, boolean isSingleElement) {
        isAnimating = true;
        new Timer(animationSpeed, e -> {
            if (node.yPos > targetY) {
                node.yPos -= 12;
                repaint();
            } else {
                node.yPos = targetY;
                isAnimating = false;
                ((Timer) e.getSource()).stop();
                if (!isSingleElement) {
                    resizePop();
                }
                repaint();
            }
        }).start();
    }

    private void resizePop() {
        if (getPreferredSize().height % top.yPos > 100 && sizeSt() > 1) {
            dynamicHeight = top.yPos - nodeHeight * 2;
        }
        setPreferredSize(new Dimension(myWidth, endY));
        scrollRectToVisible(new Rectangle(getPreferredSize()));
        revalidate();
        repaint();
    }

    public int peek() {
        return top.data;
    }

    @Override
    public int sizeSt() {
        return size;
    }

    @Override
    public void drawNode(Graphics2D g, LinkedListInterface.VisualNode node) {
        Color oldColor = g.getColor();
        Stroke resetStroke = g.getStroke();

        // Draw shadow for depth
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRoundRect(node.xPos + 4, node.yPos + 4, nodeWidth, nodeHeight, 10, 10);

        // Draw main node with rounded corners
        g.setColor(new Color(0x1E3A8A));
        g.fillRoundRect(node.xPos, node.yPos, nodeWidth, nodeHeight, 10, 10);

        // Draw border
        g.setColor(new Color(0xFFD700));
        g.setStroke(new BasicStroke(4f));
        g.drawRoundRect(node.xPos, node.yPos, nodeWidth, nodeHeight, 10, 10);

        // Draw vertical divider
        g.setStroke(new BasicStroke(3f));
        g.drawLine(node.xPos + nodeWidth / 2, node.yPos + 5, node.xPos + nodeWidth / 2, node.yPos + nodeHeight - 5);

        // Draw data section
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        g.setColor(Color.WHITE);
        String dataStr = String.valueOf(node.data);
        FontMetrics fm = g.getFontMetrics(g.getFont());
        g.drawString(dataStr, node.xPos + nodeWidth / 4 - fm.stringWidth(dataStr) / 2, node.yPos + nodeHeight / 2 + 10);

        // Draw label
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        g.setColor(new Color(0xFFD700));
        g.drawString("Data", node.xPos + nodeWidth / 4 - 15, node.yPos + 20);

        // Draw next address
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g.setColor(Color.WHITE);
        String nextAddr = (node.getNextAddress() == null) ? "NULL"
                : node.getNextAddress().substring(0, Math.min(6, node.getNextAddress().length()));
        g.drawString(nextAddr, node.xPos + 3 * nodeWidth / 5 - 10, node.yPos + nodeHeight / 2 + 10);

        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        g.setColor(new Color(0xFFD700));
        g.drawString("Next", node.xPos + 3 * nodeWidth / 5 - 5, node.yPos + 20);

        // Draw address pointer
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        String shortAddr = node.getAddress().substring(0, Math.min(8, node.getAddress().length()));
        g.drawString(shortAddr, 30, node.yPos + nodeHeight / 2 + 5);

        myArrow.draw(
                g,
                g.getFontMetrics(g.getFont()).stringWidth(shortAddr) + 35,
                node.yPos + nodeHeight / 2,
                new Color(0xFFD700));

        g.setColor(oldColor);
        g.setStroke(resetStroke);
    }

    private int returnClosest(int startPoint, int endPoint, int divisor) {
        for (int i = startPoint; i <= endPoint; i++) {
            if (i % divisor == 0) {
                return i;
            }
        }
        return startPoint;
    }

    @Override
    public void drawBasket(Graphics2D g) {
        Stroke stroke = g.getStroke();
        Color color = g.getColor();
        g.setStroke(new BasicStroke(15f));
        g.setColor(DefaultWindowsInterface.backgroundColor);
        g.drawLine(startX, dynamicHeight, startX, endY);
        g.drawLine(startX, endY, endX, endY);
        g.drawLine(endX, endY, endX, dynamicHeight);
        g.setStroke(stroke);
        g.setColor(color);
    }
}
