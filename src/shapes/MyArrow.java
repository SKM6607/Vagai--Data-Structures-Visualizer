package MyShapes;
import java.awt.*;
public class MyArrow {
    private int length, size;
    public MyArrow(int length,int size) {
        this.size=size;
        this.length=length;
    }
    public void draw(Graphics2D g,int x,int y,Color color) {
        Color oldColor=g.getColor();
        int endX=x+length;
        g.setColor(color);
        g.drawLine(x,y,endX,y);
        g.fillPolygon(new Polygon(new int[]{endX+size,endX,endX},new int[]{y,y+size,y-size},3));
        g.setColor(oldColor);
    }
}
