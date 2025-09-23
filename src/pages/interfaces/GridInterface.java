package Windows.interfaces;

import java.awt.*;

public interface GridInterface extends DefaultWindowsInterface{
    int SPACING=25;
    default void drawGrid(Graphics2D g,Color color){
        Color retColor=g.getColor();
        g.setColor(color);
        for (int i = 0; i < width; i+=SPACING) {
            g.drawLine(i,0,i,height);
        }
        for (int i = 0; i < height; i+=SPACING) {
            g.drawLine(0,i,width,i);
        }
        g.setColor(retColor);
    }
}
