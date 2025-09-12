package Windows.Interfaces;

import java.awt.*;

public interface LinkedListLightWeightInterface extends LinkedListInterface{
    static String LINKED_LIST="Linked List";
    void append(int value);
    void pop();
    int sizeLL();
    void drawNode(Graphics2D g,VisualNode node);
}