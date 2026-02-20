package main.queues;

import main.base_panels.VisualizerGridPanel;
import main.interfaces.GridInterface;
import main.interfaces.QueueInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        extends VisualizerGridPanel
        implements QueueInterface, GridInterface
        permits PriorityQueue, SimpleQueue, CircularQueue {
    protected final int nodeWidth = 100;
    protected final int nodeHeight = 120;
    protected final int spacing = 30;
    protected final int startX = 100;
    protected static final Color gridColor=new Color(0x1C233D);
    protected final int startY = 150;
    protected int front = -1;
    protected int rear = -1;
    protected int animationSpeed;
    protected int capacity = 15;

    protected abstract void drawNode(Graphics2D g, Node node, int... args);
    public Queue(){
        setBackground(defaultBackgroundColor);
    }
    public void setAnimationSpeed(int s) {
        this.animationSpeed = s;
    }

    public abstract void enqueue(int... args);

    protected void resize() {
        invalidateGrid();
    }

    @Override
    public boolean isFull() {
        return sizeQ() >= capacity;
    }
}
