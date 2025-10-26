package utils;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for creating smooth animations in DSA visualizations
 */
public class AnimationHelper {
    
    /**
     * Easing function for smooth transitions (ease-in-out)
     */
    public static float easeInOut(float t) {
        return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }
    
    /**
     * Linear interpolation between two values
     */
    public static int lerp(int start, int end, float progress) {
        return (int) (start + (end - start) * progress);
    }
    
    /**
     * Smooth color transition
     */
    public static Color lerpColor(Color start, Color end, float progress) {
        int r = lerp(start.getRed(), end.getRed(), progress);
        int g = lerp(start.getGreen(), end.getGreen(), progress);
        int b = lerp(start.getBlue(), end.getBlue(), progress);
        return new Color(r, g, b);
    }
    
    /**
     * Animate position change with smooth transition
     */
    public static void animatePosition(JPanel panel, Runnable updatePositions, 
                                      int duration, int steps) {
        Timer timer = new Timer(duration / steps, null);
        final int[] step = {0};
        
        timer.addActionListener(e -> {
            step[0]++;
            float progress = easeInOut((float) step[0] / steps);
            updatePositions.run();
            panel.repaint();
            
            if (step[0] >= steps) {
                timer.stop();
            }
        });
        timer.start();
    }
    
    /**
     * Pulse animation for highlighting elements
     */
    public static void pulseHighlight(JPanel panel, Runnable highlightAction, 
                                     int pulses, int duration) {
        Timer timer = new Timer(duration, null);
        final int[] count = {0};
        
        timer.addActionListener(e -> {
            highlightAction.run();
            panel.repaint();
            count[0]++;
            
            if (count[0] >= pulses * 2) {
                timer.stop();
            }
        });
        timer.start();
    }
}
