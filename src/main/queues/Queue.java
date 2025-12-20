package main.queues;

import main.interfaces.GridInterface;
import main.interfaces.LinkedListInterface.VisualNode;
import main.interfaces.QueueInterface;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The class Queue is an abstract class that provides with the most basic fields and methods for queue implementations
 * @see QueueInterface
 * @see GridInterface
 * @since v0.0.3
 * @version v0.0.5
 * @author Sri Koushik JK
 * */
public sealed abstract class Queue
        extends JPanel
        implements QueueInterface,GridInterface
        permits PriorityQueue, SimpleQueue, CircularQueue
{
    protected final int nodeWidth = 100;
    protected final int nodeHeight = 120;
    protected final int spacing = 30;
    protected final int startX = 100;
    protected final int startY = 150;
    protected int dynamicWidth = width;
    protected int[] queue;
    protected int front = -1;
    protected int rear = -1;
    protected abstract void drawNode(Graphics2D g, VisualNode node);
    public abstract void setAnimationSpeed(int value);
    protected <T extends VisualNode>void resize(ArrayList<T> nodeList){
        if (nodeList.isEmpty()) {
            dynamicWidth= width;
        } else {
            int lastX = nodeList.getLast().xPos;
            dynamicWidth = Math.max(width, lastX + nodeWidth + 200);
        }
        setPreferredSize(new Dimension(dynamicWidth, height));
        revalidate();
        repaint();
    }
    protected abstract <T extends VisualNode>void enqueue(@NotNull T node,int... args) ;
    public abstract void dequeue();
    protected static final class PriorityNode extends VisualNode {
        int priority;
        Color color;
        PriorityNode(int value,int xPos,int yPos,int priority) {
            super(value,xPos,yPos);
            this.priority = priority;
            this.color=new Color(0x1E3A8A);
        }
    }
}
