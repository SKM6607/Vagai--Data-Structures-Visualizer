package Windows.Interfaces;

import java.awt.*;

public interface LinkedListLightWeightInterface extends LinkedListInterface{
    void append(int value);
    void pop();
    int sizeLL();
    void drawNode(Graphics2D g,VisualNode node);
}