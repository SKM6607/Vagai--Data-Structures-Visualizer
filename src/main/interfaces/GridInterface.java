package main.interfaces;

import java.awt.*;
/**
 * The <code>GridInterface</code> contains methods to draw grids with custom width and colors depending on requirements.
 * @author Sri Koushik JK
 * @since v0.0.1
 * @version v0.0.5
 * @see DefaultWindowsInterface
 * */
public interface GridInterface extends DefaultWindowsInterface {
    int SPACING = 25;

    /**
     * The method is to draw Grid.
     * The color of the grid is the current color of graphics context provided. Uses <code>g.getColor()</code> for color
     *
     * @param g Graphics Context
     * @author Sri Koushik JK
     * @since v0.0.2
     */
    default void drawGrid(Graphics2D g) {
        drawGrid(g, g.getColor());
    }

    /**
     * The method is to draw Grid
     *
     * @param g     Graphics Context
     * @param color Grid Color
     * @author Sri Koushik JK
     * @since v0.0.2
     */
    default void drawGrid(Graphics2D g, Color color) {
        drawGrid(g, color, width);
    }

    /**
     * This one is for scrolling and dynamic updating
     *
     * @param g            Graphics Context
     * @param color        Grid Color
     * @param dynamicWidth is used as dynamic width value for horizontal scrolling
     * @since v0.0.2
     * @author Sri Koushik JK
     */
    default void drawGrid(Graphics2D g, Color color, int dynamicWidth) {
        Color retColor = g.getColor();
        g.setColor(color);
        for (int i = 0; i < dynamicWidth; i += SPACING) {
            g.drawLine(i, 0, i, height);
        }
        for (int i = 0; i < height; i += SPACING) {
            g.drawLine(0, i, dynamicWidth, i);
        }
        g.setColor(retColor);
    }
}
