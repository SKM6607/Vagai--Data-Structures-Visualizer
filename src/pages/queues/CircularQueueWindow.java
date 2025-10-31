package pages.queues;

import org.jetbrains.annotations.NotNull;
import pages.interfaces.DefaultWindowsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static pages.abstractClasses.ComponentUtilities.*;
final class CircularQueueVisual extends Queue {

    private final int capacity = 12;
    private final int centerX;
    private final int centerY;
    private final int radius = 250;
    private int animationSpeed = 300;
    private Integer highlightIndex = null;
    private Color highlightColor = Color.GREEN;
    
    CircularQueueVisual() {
        queue = new int[capacity];
        centerX = width / 2;
        centerY = height / 2;
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0xA0F29));
    }
    
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g, new Color(0x1C233D));
        drawCircularStructure(g);
        drawNodes(g);
        drawPointers(g);
    }
    
    private void drawCircularStructure(Graphics2D g) {
        g.setColor(new Color(0x1E3A8A));
        g.setStroke(new BasicStroke(3f));
        g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        // Draw connection lines
        g.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
        for (int i = 0; i < capacity; i++) {
            double angle = 2 * Math.PI * i / capacity - Math.PI / 2;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            g.drawLine(centerX, centerY, x, y);
        }
    }
    
    private void drawNodes(Graphics2D g) {
        for (int i = 0; i < capacity; i++) {
            double angle = 2 * Math.PI * i / capacity - Math.PI / 2;
            int nodeSize = 60;
            int x = centerX + (int) (radius * Math.cos(angle)) - nodeSize / 2;
            int y = centerY + (int) (radius * Math.sin(angle)) - nodeSize / 2;
            
            // Draw shadow
            g.setColor(new Color(0, 0, 0, 50));
            g.fillOval(x + 3, y + 3, nodeSize, nodeSize);

            Color nodeColor;
            if (highlightIndex != null && highlightIndex == i) {
                nodeColor = highlightColor;
            } else if (isEmpty()) {
                nodeColor = new Color(0x2D3748);
            } else if (front == rear && i == front) {
                nodeColor = new Color(0x1E3A8A);
            } else if (front <= rear && i >= front && i <= rear) {
                nodeColor = new Color(0x1E3A8A);
            } else if (front > rear && (i >= front || i <= rear)) {
                nodeColor = new Color(0x1E3A8A);
            } else {
                nodeColor = new Color(0x2D3748);
            }
            
            // Draw node circle
            g.setColor(nodeColor);
            g.fillOval(x, y, nodeSize, nodeSize);
            
            // Draw border
            g.setColor(new Color(0xFFD700));
            g.setStroke(new BasicStroke(3f));
            g.drawOval(x, y, nodeSize, nodeSize);
            
            // Draw index
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            g.setColor(Color.YELLOW);
            String indexStr = String.valueOf(i);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(indexStr, x + nodeSize / 2 - fm.stringWidth(indexStr) / 2, y - 5);
            
            // Draw value if position is occupied
            if (!isEmpty() && isOccupied(i)) {
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                g.setColor(Color.WHITE);
                String valueStr = String.valueOf(queue[i]);
                fm = g.getFontMetrics();
                g.drawString(valueStr, x + nodeSize / 2 - fm.stringWidth(valueStr) / 2, 
                           y + nodeSize / 2 + 7);
            }
        }
    }
    
    private void drawPointers(Graphics2D g) {
        if (isEmpty()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
            g.drawString("QUEUE EMPTY", centerX - 80, centerY);
            return;
        }
        
        // Draw FRONT pointer
        double frontAngle = 2 * Math.PI * front / capacity - Math.PI / 2;
        int frontX = centerX + (int) ((radius + 80) * Math.cos(frontAngle));
        int frontY = centerY + (int) ((radius + 80) * Math.sin(frontAngle));
        
        g.setColor(Color.GREEN);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.drawString("FRONT", frontX - 25, frontY);
        
        // Draw arrow to front
        int arrowStartX = centerX + (int) ((radius + 60) * Math.cos(frontAngle));
        int arrowStartY = centerY + (int) ((radius + 60) * Math.sin(frontAngle));
        int arrowEndX = centerX + (int) ((radius + 35) * Math.cos(frontAngle));
        int arrowEndY = centerY + (int) ((radius + 35) * Math.sin(frontAngle));
        
        g.setStroke(new BasicStroke(2f));
        g.drawLine(arrowStartX, arrowStartY, arrowEndX, arrowEndY);
        drawArrowHead(g, arrowStartX, arrowStartY, arrowEndX, arrowEndY);
        
        // Draw REAR pointer
        double rearAngle = 2 * Math.PI * rear / capacity - Math.PI / 2;
        int rearX = centerX + (int) ((radius + 80) * Math.cos(rearAngle));
        int rearY = centerY + (int) ((radius + 80) * Math.sin(rearAngle));
        
        g.setColor(Color.RED);
        g.drawString("REAR", rearX - 20, rearY);
        
        // Draw arrow to rear
        arrowStartX = centerX + (int) ((radius + 60) * Math.cos(rearAngle));
        arrowStartY = centerY + (int) ((radius + 60) * Math.sin(rearAngle));
        arrowEndX = centerX + (int) ((radius + 35) * Math.cos(rearAngle));
        arrowEndY = centerY + (int) ((radius + 35) * Math.sin(rearAngle));
        
        g.drawLine(arrowStartX, arrowStartY, arrowEndX, arrowEndY);
        drawArrowHead(g, arrowStartX, arrowStartY, arrowEndX, arrowEndY);
        
        // Draw size info in center
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        String sizeStr = "Size: " + sizeQ();
        FontMetrics fm = g.getFontMetrics();
        g.drawString(sizeStr, centerX - fm.stringWidth(sizeStr) / 2, centerY + 5);
    }
    
    private void drawArrowHead(Graphics2D g, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowSize = 10;
        
        int[] xPoints = {
            x2,
            x2 - (int) (arrowSize * Math.cos(angle - Math.PI / 6)),
            x2 - (int) (arrowSize * Math.cos(angle + Math.PI / 6))
        };
        int[] yPoints = {
            y2,
            y2 - (int) (arrowSize * Math.sin(angle - Math.PI / 6)),
            y2 - (int) (arrowSize * Math.sin(angle + Math.PI / 6))
        };
        g.fillPolygon(xPoints, yPoints, 3);
    }
    
    private boolean isOccupied(int index) {
        if (isEmpty()) return false;
        if (front <= rear) {
            return index >= front && index <= rear;
        } else {
            return index >= front || index <= rear;
        }
    }
    
    public void enqueue(int value) {
        if (isFull()) {
            JOptionPane.showMessageDialog(this, "Queue is full!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (isEmpty()) {
            front = rear = 0;
        } else {
            rear = (rear + 1) % capacity;
        }
        
        queue[rear] = value;
        animateHighlight(rear, Color.GREEN);
    }
    
    public void dequeue() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        animateHighlight(front, Color.RED);
        
        Timer timer = new Timer(animationSpeed, _ -> {
            if (front == rear) {
                front = rear = -1;
            } else {
                front = (front + 1) % capacity;
            }
            repaint();
        });
        timer.setRepeats(false);
        timer.start();

    }

    private void animateHighlight(int index, Color color) {
        highlightIndex = index;
        highlightColor = color;
        repaint();
        
        Timer timer = new Timer(animationSpeed, _ -> {
            highlightIndex = null;
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    public boolean isEmpty() {
        return front == -1;
    }
    
    public boolean isFull() {
        return (rear + 1) % capacity == front;
    }

    @Override
    public void drawNode(Graphics2D g, VisualNode node) {

    }

    public int sizeQ() {
        if (isEmpty()) return 0;
        if (front <= rear) {
            return rear - front + 1;
        } else {
            return capacity - front + rear + 1;
        }
    }
    
    public void setAnimationSpeed(int speed) {
        this.animationSpeed = speed;
    }

}
public final class CircularQueueWindow extends QueueWindow<CircularQueueVisual> {
    public CircularQueueWindow() {
        setLayout(new BorderLayout());
        
        visualQueue = new CircularQueueVisual();
        
        // Control panel
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        controlPanel.setBackground(new Color(0, 18, 121));
        
        // Input and buttons
        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        inputPanel.setBackground(new Color(0, 18, 121));

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
        textField =createTextField(font,themeColorBG);
        JButton enqueueBtn = createButton("ENQUEUE", new Color(0, 18, 121), font);
        JButton dequeueBtn = createButton("DEQUEUE", Color.BLACK, font);
        
        enqueueBtn.addActionListener(_ -> {
            if (verifyInput()) {
                visualQueue.enqueue(Integer.parseInt(textField.getText()));
                textField.setText("");
            }
        });
        
        dequeueBtn.addActionListener(_ -> visualQueue.dequeue());
        
        inputPanel.add(textField);
        inputPanel.add(enqueueBtn);
        inputPanel.add(dequeueBtn);
        // Speed control
        JPanel speedPanel = getPanel();
        // Info panel
        infoLabel = setInfoLabel("Circular Queue - Efficient Space Utilization");
        controlPanel.add(infoLabel);
        controlPanel.add(inputPanel);
        controlPanel.add(speedPanel);
        add(visualQueue, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        // Key listener
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n' && verifyInput()) {
                    visualQueue.enqueue(Integer.parseInt(textField.getText()));
                    textField.setText("");
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {}
            
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    private boolean verifyInput() {
        return textField.getInputVerifier().verify(textField);
    }
}
