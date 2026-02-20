package main.base_panels;

import main.interfaces.GridInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * <li>The class VisualizerGridPanel is used to draw a static background for drawing upon.</li>
 * <li>This class can be used to make drawing Grid more efficient.</li>
 * @author Sri Koushik JK
 * @see GridInterface
 * @since v0.0.5
 * @version v0.0.5
 * */
public abstract class VisualizerGridPanel
        extends JPanel
        implements GridInterface {
    protected Color defaultBackgroundColor=new Color(0xA0F29);
    protected Color color;
    private BufferedImage bufferedImage;
    private Dimension cachedSize;
    protected void paintGrid(Graphics2D g, Color... colors) {
        color = g.getColor();
        if (colors.length >= 1 && colors[0] != null) color=colors[0];
        if (colors.length == 2) g.setColor(colors[1]);
        if (bufferedImage == null || !getSize().equals(cachedSize)) {
            rebuildGrid();
        }
        g.drawImage(bufferedImage, 0, 0, null);
    }

    protected void rebuildGrid() {
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        drawGrid(g, color);
        g.dispose();
        cachedSize = getSize();
    }

    protected void invalidateGrid() {
        bufferedImage = null;
    }
}
