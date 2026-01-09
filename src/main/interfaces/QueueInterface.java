package main.interfaces;
import java.awt.*;
public interface QueueInterface  extends LinkedListInterface {
    /**
     * Used to enqueue nodes to the queue.
     *
     * @param args The arguments can generally differ based on the type of queue to be implemented.
     * @author Sri Koushik JK
     * @since v0.0.5
     */
    void enqueue(int ...args);
    /**
     * Used to enqueue nodes to the queue.
     *
     * @param node  The type of node to be inserted
     * @param args The arguments can generally differ based on the type of queue to be implemented.
     * @author Sri Koushik JK
     * @since v0.0.5
     *
     */
    default void enqueue(Node node,int ...args){}
    /**
     * Used to dequeue nodes from the queue.
     *
     * @return The data of the node removed.
     * <br><code>
     * {<br>
     * <b>Value</b> stored in the node;<br>
     * <b>Address</b> of the node;<br>
     * <b>Other properties</b>(if they exist);<br>
     * }<br>
     * </code>
     * @author Sri Koushik JK
     * @since v0.0.5
     *
     */
    Object[] dequeue();
    /**
     * Returns the size of the queue implemented
     * @return Size of the queue
     * @since v0.0.5
     * @author Sri Koushik JK
     * */
    int sizeQ();
    /**
     * @return <b>True</b> if the queue is empty, <b>False</b> otherwise
     * @since v0.0.5
     * @author Sri Koushik JK
     * */
    boolean isEmpty();
    /**
     * @return <b>True</b> if the queue is full, <b>False</b> otherwise
     * @since v0.0.5
     * @author Sri Koushik JK
     * */
    boolean isFull();
    /**
     * <li>This class provides the template to create a priority node.</li>
     * <li>A priority node consists of value and priority.</li>
     * <li>The lesser the priority, more the importance.</li>
     * @since v0.0.5
     * @version v0.0.5
     * @author Sri Koushik JK
     * */
    final class PriorityNode extends VisualNode {
        public int priority;
        public Color color;
        public PriorityNode(int value, int xPos, int yPos, int priority) {
            super(value, xPos, yPos);
            this.priority = priority;
            this.color = new Color(0x1E3A8A);
        }
    }
}
