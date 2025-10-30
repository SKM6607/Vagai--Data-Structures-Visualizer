package pages.queues;

import pages.interfaces.DefaultWindowsInterface;
import pages.interfaces.GridInterface;
import pages.interfaces.QueueInterface;
import shapes.MyArrow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

class SimpleQueueVisual extends JPanel implements QueueInterface, GridInterface {
    private final ArrayList<VisualNode> queue = new ArrayList<>();
    private final MyArrow arrow = new MyArrow(80, 12);
    private final int nodeWidth = 120;
    private final int nodeHeight = 100;
    private final int spacing = 20;
    private int dynamicWidth = width;
    private final int maxCapacity = 15;
    private int animationSpeed = 300;
    
    SimpleQueueVisual() {
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0xA0F29));
    }
    
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g, new Color(0x1C233D));
        drawQueueLabels(g);
        
        for (int i = 0; i < queue.size(); i++) {
            drawNode(g, queue.get(i));
        }
    }
    
    private void drawQueueLabels(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        
        if (!queue.isEmpty()) {
            // Front label
            g.drawString("FRONT →", 50, height / 2 + 10);
            // Rear label
            int rearX = queue.get(queue.size() - 1).xPos + nodeWidth + 40;
            g.drawString("← REAR", rearX, height / 2 + 10);
        } else {
            g.drawString("QUEUE EMPTY", width / 2 - 100, height / 2);
        }
    }
    
    @Override
    public void enqueue(int value) {
        if (isFull()) {
            JOptionPane.showMessageDialog(this, "Queue is full!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int xPos = queue.isEmpty() ? 200 : queue.get(queue.size() - 1).xPos + nodeWidth + spacing;
        int yPos = height / 2 - nodeHeight / 2;
        
        VisualNode newNode = new VisualNode(value, xPos, yPos);
        if (!queue.isEmpty()) {
            queue.get(queue.size() - 1).nextNode = newNode;
            queue.get(queue.size() - 1).nextAddress = newNode.address;
        }
        queue.add(newNode);
        
        // Smooth animation
        animateEnqueue(newNode);
        resize();
    }
    
    private void animateEnqueue(VisualNode node) {
        final int originalY = node.yPos;
        node.yPos = -nodeHeight;
        
        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            if (node.yPos < originalY) {
                node.yPos += 8;
                repaint();
            } else {
                node.yPos = originalY;
                timer.stop();
                repaint();
            }
        });
        timer.start();
    }
    
    @Override
    public int dequeue() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        
        VisualNode front = queue.get(0);
        int value = front.data;
        
        // Animate removal
        animateDequeue(front);
        
        return value;
    }
    
    private void animateDequeue(VisualNode node) {
        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            node.yPos -= 10;
            if (node.yPos < -nodeHeight - 50) {
                timer.stop();
                queue.remove(0);
                if (!queue.isEmpty()) {
                    queue.get(0).nextAddress = queue.size() > 1 ? queue.get(1).address : null;
                }
                shiftLeft();
                repaint();
            } else {
                repaint();
            }
        });
        timer.start();
    }
    
    private void shiftLeft() {
        if (queue.isEmpty()) return;
        
        final int targetX = 200;
        final int[] step = {0};
        final int steps = 20;
        
        Timer timer = new Timer(animationSpeed / steps, null);
        timer.addActionListener(e -> {
            step[0]++;
            float progress = (float) step[0] / steps;
            
            for (int i = 0; i < queue.size(); i++) {
                int targetPos = targetX + i * (nodeWidth + spacing);
                queue.get(i).xPos = (int) (queue.get(i).xPos + (targetPos - queue.get(i).xPos) * progress);
            }
            
            repaint();
            
            if (step[0] >= steps) {
                timer.stop();
                resize();
            }
        });
        timer.start();
    }
    
    @Override
    public int peek() {
        return isEmpty() ? -1 : queue.get(0).data;
    }
    
    @Override
    public int sizeQ() {
        return queue.size();
    }
    
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    @Override
    public boolean isFull() {
        return queue.size() >= maxCapacity;
    }
    
    private void resize() {
        if (queue.isEmpty()) {
            dynamicWidth = width;
        } else {
            int lastX = queue.get(queue.size() - 1).xPos;
            dynamicWidth = Math.max(width, lastX + nodeWidth + 200);
        }
        setPreferredSize(new Dimension(dynamicWidth, height));
        revalidate();
        repaint();
    }
    
    public void setAnimationSpeed(int speed) {
        this.animationSpeed = speed;
    }
    
    @Override
    public void drawNode(Graphics2D g, VisualNode node) {
        int x = node.xPos;
        int y = node.yPos;
        
        // Draw shadow
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(x + 5, y + 5, nodeWidth, nodeHeight);
        
        // Draw node
        g.setColor(new Color(0x1E3A8A));
        g.fillRect(x, y, nodeWidth, nodeHeight);
        
        // Draw border with glow effect
        g.setStroke(new BasicStroke(4f));
        g.setColor(new Color(0xFFD700));
        g.drawRect(x, y, nodeWidth, nodeHeight);
        
        // Draw data
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        String dataStr = String.valueOf(node.data);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(dataStr);
        g.drawString(dataStr, x + (nodeWidth - textWidth) / 2, y + nodeHeight / 2 + 10);
        
        // Draw address label
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g.setColor(Color.WHITE);
        g.drawString(node.address, x + 5, y - 5);
        
        // Draw arrow to next node
        if (node.nextNode != null) {
            arrow.draw(g, x + nodeWidth, y + nodeHeight / 2, new Color(0xFFD700));
        }
    }
    
    @Override
    public void drawGrid(Graphics2D g, Color color) {
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

public class SimpleQueueWindow extends JPanel implements DefaultWindowsInterface {
    private final SimpleQueueVisual visualQueue;
    private final JTextField textField;
    private final JSlider speedSlider;
    private final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    
    public SimpleQueueWindow() {
        setLayout(new BorderLayout());
        
        visualQueue = new SimpleQueueVisual();
        JScrollPane scrollPane = new JScrollPane(visualQueue,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        
        // Control panel
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        controlPanel.setBackground(new Color(0, 18, 121));
        
        // Input and buttons panel
        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        inputPanel.setBackground(new Color(0, 18, 121));
        
        textField = createTextField();
        JButton enqueueBtn = createButton("ENQUEUE", new Color(0, 18, 121));
        JButton dequeueBtn = createButton("DEQUEUE", Color.BLACK);
        
        enqueueBtn.addActionListener(e -> {
            if (verifyInput()) {
                visualQueue.enqueue(Integer.parseInt(textField.getText()));
                textField.setText("");
            }
        });
        
        dequeueBtn.addActionListener(e -> visualQueue.dequeue());
        
        inputPanel.add(textField);
        inputPanel.add(enqueueBtn);
        inputPanel.add(dequeueBtn);
        
        // Speed control
        JPanel speedPanel = new JPanel(new BorderLayout());
        speedPanel.setBackground(new Color(0, 18, 121));
        JLabel speedLabel = new JLabel("Animation Speed", SwingConstants.CENTER);
        speedLabel.setForeground(Color.WHITE);
        speedLabel.setFont(font);
        
        speedSlider = new JSlider(50, 500, 300);
        speedSlider.setBackground(new Color(0, 18, 121));
        speedSlider.setForeground(Color.WHITE);
        speedSlider.addChangeListener(e -> visualQueue.setAnimationSpeed(speedSlider.getValue()));
        
        speedPanel.add(speedLabel, BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);
        
        // Info panel
        JLabel infoLabel = new JLabel("Simple Queue (FIFO) - First In First Out", SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(new Color(0, 18, 121));
        
        controlPanel.add(infoLabel);
        controlPanel.add(inputPanel);
        controlPanel.add(speedPanel);
        
        add(scrollPane, BorderLayout.CENTER);
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
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBackground(themeColorBG);
        field.setForeground(Color.WHITE);
        field.setToolTipText("Enter integer value");
        field.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                try {
                    Integer.parseInt(((JTextField) input).getText());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        return field;
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        return button;
    }
    
    private boolean verifyInput() {
        return textField.getInputVerifier().verify(textField);
    }
}
