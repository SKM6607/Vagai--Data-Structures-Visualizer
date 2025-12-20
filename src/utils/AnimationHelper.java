package utils;

import main.interfaces.LinkedListInterface;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Utility class for creating smooth animations in DSA visualizations
 */
public final class AnimationHelper implements LinkedListInterface {

    /**
     * Easing function for smooth transitions (ease-in-out)
     */
    public static float easeInOut(float t) {
        return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }

}
