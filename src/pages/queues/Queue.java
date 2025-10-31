package pages.queues;

import pages.interfaces.GridInterface;
import pages.interfaces.QueueInterface;

import javax.swing.*;
import java.awt.*;
/**
 * The class Queue is an abstract class that provides with the most basic fields and methods for queue implementations
 * @see QueueInterface
 * @see GridInterface
 * @since v0.0.3
 * */
public sealed abstract class Queue
        extends JPanel
        implements QueueInterface,GridInterface
        permits PriorityQueueVisual,SimpleQueueVisual,CircularQueueVisual
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
    protected sealed static class Node{
        int value;
        int xPos;
        int yPos;
        Color color;
        public String nextAddress;
        public Node next;
        public Node(int value,Color color,int xPos,int yPos){
            this.value=value;
            this.color=color;
            this.xPos=xPos;
            this.yPos=yPos;
        }
    }
    protected static final class PriorityNode extends Node {
        int value;
        int priority;
        PriorityNode(int value,int xPos,int yPos,int priority) {
            super(value,new Color(0x1E3A8A),xPos,yPos);
            this.priority = priority;
        }
    }
}
