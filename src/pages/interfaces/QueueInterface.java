package pages.interfaces;

import java.awt.*;

public interface QueueInterface extends LinkedListInterface {
    void enqueue(int value);
    int dequeue();
    int peek();
    int sizeQ();
    boolean isEmpty();
    boolean isFull();
    void drawNode(Graphics2D g, VisualNode node);
}
