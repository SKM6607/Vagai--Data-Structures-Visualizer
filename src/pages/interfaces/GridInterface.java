package pages.interfaces;
import java.awt.*;

public interface GridInterface extends DefaultWindowsInterface{
    int SPACING=25;
    /**
     * The method is to draw Grid
     * @param g Graphics Context
     * @param color  Grid Color
     * */
    default void drawGrid(Graphics2D g,Color color){
        drawGrid(g,color,width);
    }
    /**
    * This one is for scrolling and dynamic Updation
     * @param g Graphics Context
     * @param color  Grid Color
     * @param dynamicWidth is used as dynamic width value for horizontal scrolling
    * */
    default void drawGrid(Graphics2D g,Color color,int dynamicWidth){
        Color retColor=g.getColor();
        g.setColor(color);
        for (int i = 0; i < dynamicWidth; i+=SPACING) {
            g.drawLine(i,0,i,height);
        }
        for (int i = 0; i < height; i+=SPACING) {
            g.drawLine(0,i,dynamicWidth,i);
        }
        g.setColor(retColor);
    }
}
