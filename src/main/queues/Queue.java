package main.queues;
import main.interfaces.GridInterface;
import main.interfaces.QueueInterface;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
/**
 * The class Queue is an abstract class that provides with the most basic fields and methods for queue implementations
 *
 * @author Sri Koushik JK
 * @version v0.0.5
 * @see QueueInterface
 * @see GridInterface
 * @since v0.0.3
 *
 */
public sealed abstract class Queue
        extends JPanel
        implements QueueInterface, GridInterface
        permits PriorityQueue, SimpleQueue, CircularQueue
{
    protected final int nodeWidth = 100;
    protected final int nodeHeight = 120;
    protected final int spacing = 30;
    protected final int startX = 100;
    protected final int startY = 150;
    protected int dynamicWidth = width;
    protected int front = -1;
    protected int rear = -1;
    protected int animationSpeed;
    protected abstract void drawNode(Graphics2D g, Node node,int ...args);
    public void setAnimationSpeed(int s){
        this.animationSpeed=s;
    }
    public abstract void enqueue(int ...args);
    protected <T extends VisualNode>void resize(ArrayList<T> nodeList){
        if (nodeList.isEmpty()) {
            dynamicWidth = width;
        } else {
            int lastX = nodeList.getLast().xPos;
            dynamicWidth = Math.max(width, lastX + nodeWidth + 200);
        }
        setPreferredSize(new Dimension(dynamicWidth, height));
        revalidate();
        repaint();
    }
}
