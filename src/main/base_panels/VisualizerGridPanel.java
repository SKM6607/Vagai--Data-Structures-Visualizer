package main.base_panels;

import main.interfaces.GridInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class VisualizerGridPanel
        extends JPanel
        implements GridInterface {
    protected Color color=getGraphics().getColor();
    private BufferedImage bufferedImage;
    private Dimension cachedSize;

    protected void paintGrid(Graphics2D g, Color... colors) {
        color = g.getColor();
        Color fg = g.getColor();
        if (colors.length >= 1 && colors[0] != null) fg = colors[0];
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
