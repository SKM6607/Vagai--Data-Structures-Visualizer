package MyShapes;
import java.awt.*;
public class MyArrow {
    private int length, size;
    public MyArrow(int length,int size) {
        this.size=size;
        this.length=length;
    }
    public void draw(Graphics2D g,int x,int y,Color color) {
        g.setColor(color);
        g.drawLine(x,y,x+=length,y);
        g.fillPolygon(new Polygon(new int[]{x+size,x,x},new int[]{y,y+size,y-size},3));
        g.setColor(Color.WHITE);
    }
}
