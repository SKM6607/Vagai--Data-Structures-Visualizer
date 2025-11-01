package main.linkedList;
import main.interfaces.GridInterface;
import main.interfaces.LinkedListInterface;
import main.interfaces.LinkedListLightWeightInterface;
import javax.swing.*;
import java.awt.*;
public sealed abstract class LinkedList extends JPanel implements LinkedListInterface, LinkedListLightWeightInterface, GridInterface permits LinkedListVisual,CycleDetectionVisual{
    public LinkedList(){
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0xA0F29));
    }
    protected abstract void resize();
    protected static void drawTitle(Graphics2D g,String title,int x,int y){
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        g.drawString(title, x,y);
    }
    protected abstract void drawInitialDetails(Graphics2D g);
    protected abstract void reset();
}
