package pages.interfaces;

import java.awt.*;

public interface LinkedListLightWeightInterface extends LinkedListInterface{
    void insert(int value);
    void delete();
    int sizeLL();
    void drawNode(Graphics2D g,VisualNode node);
}