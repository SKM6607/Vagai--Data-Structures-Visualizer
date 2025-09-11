package Windows.Interfaces;

import java.awt.*;

public interface LinkedListLightWeightInterface extends LinkedListInterface{
    void append();
    void pop();
    int sizeLL();
    void drawNode(Graphics2D g);
}