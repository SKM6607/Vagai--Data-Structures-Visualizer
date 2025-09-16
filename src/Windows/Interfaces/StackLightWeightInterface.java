package Windows.Interfaces;

import java.awt.*;

public interface StackLightWeightInterface extends LinkedListInterface{
    void push(int value);
    int pop();
    int peek();
    int sizeSt();
    void drawNode(Graphics2D g,VisualNode node);
    void drawBasket(Graphics2D g);
}
