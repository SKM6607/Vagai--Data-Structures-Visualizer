package pages;

import pages.interfaces.DefaultWindowsInterface;
import pages.interfaces.GridInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

class PriorityNode {
    int value;
    int priority;
    int xPos;
    int yPos;
    Color color;
    
    PriorityNode(int value, int priority, int x, int y) {
        this.value = value;
        this.priority = priority;
        this.xPos = x;
        this.yPos = y;
        this.color = new Color(0x1E3A8A);
    }
}

class PriorityQueueVisual extends JPanel implements GridInterface {
    private final List<PriorityNode> queue = new ArrayList<>();
    private final int nodeWidth = 100;
    private final int nodeHeight = 120;
    private final int spacing = 30;
    private final int startX = 100;
    private final int startY = 150;
    private int animationSpeed = 300;
    private int dynamicWidth = width;
    
    PriorityQueueVisual() {
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0xA0F29));
    }
    
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g, new Color(0x1C233D));
        
        drawTitle(g);
        drawNodes(g);
        drawPriorityLegend(g);
    }
    
    private void drawTitle(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        g.drawString("Priority Queue (Higher Priority → Lower Number)", width / 2 - 300, 50);
        
        if (queue.isEmpty()) {
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
            g.drawString("QUEUE EMPTY", width / 2 - 120, height / 2);
        } else {
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.setColor(Color.GREEN);
            g.drawString("HIGHEST PRIORITY →", 20, startY + nodeHeight / 2);
        }
    }
    
    private void drawNodes(Graphics2D g) {
        for (PriorityNode node : queue) {
            // Draw shadow
            g.setColor(new Color(0, 0, 0, 50));
            g.fillRoundRect(node.xPos + 4, node.yPos + 4, nodeWidth, nodeHeight, 15, 15);
            
            // Draw node with gradient effect
            GradientPaint gradient = new GradientPaint(
                node.xPos, node.yPos, node.color,
                node.xPos, node.yPos + nodeHeight, node.color.darker()
            );
            g.setPaint(gradient);
            g.fillRoundRect(node.xPos, node.yPos, nodeWidth, nodeHeight, 15, 15);
            
            // Draw border
            g.setColor(getPriorityColor(node.priority));
            g.setStroke(new BasicStroke(4f));
            g.drawRoundRect(node.xPos, node.yPos, nodeWidth, nodeHeight, 15, 15);
            
            // Draw priority badge
            int badgeSize = 30;
            g.setColor(getPriorityColor(node.priority));
            g.fillOval(node.xPos + nodeWidth - badgeSize - 5, node.yPos + 5, badgeSize, badgeSize);
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            String priorityStr = "P" + node.priority;
            FontMetrics fm = g.getFontMetrics();
            g.drawString(priorityStr, 
                node.xPos + nodeWidth - badgeSize / 2 - fm.stringWidth(priorityStr) / 2 - 5,
                node.yPos + 22);
            
            // Draw value
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
            String valueStr = String.valueOf(node.value);
            fm = g.getFontMetrics();
            g.drawString(valueStr, 
                node.xPos + nodeWidth / 2 - fm.stringWidth(valueStr) / 2,
                node.yPos + nodeHeight / 2 + 10);
            
            // Draw label
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            g.setColor(Color.YELLOW);
            g.drawString("Value", node.xPos + nodeWidth / 2 - 15, node.yPos + nodeHeight - 10);
            
            // Draw arrow
            if (queue.indexOf(node) < queue.size() - 1) {
                drawArrow(g, node.xPos + nodeWidth, node.yPos + nodeHeight / 2,
                         node.xPos + nodeWidth + spacing, node.yPos + nodeHeight / 2);
            }
        }
    }
    
    private void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.setColor(new Color(0xFFD700));
        g.setStroke(new BasicStroke(3f));
        g.drawLine(x1, y1, x2, y2);
        
        // Arrow head
        int arrowSize = 8;
        int[] xPoints = {x2, x2 - arrowSize, x2 - arrowSize};
        int[] yPoints = {y1, y1 - arrowSize, y1 + arrowSize};
        g.fillPolygon(xPoints, yPoints, 3);
    }
    
    private void drawPriorityLegend(Graphics2D g) {
        int legendX = width - 250;
        int legendY = 100;
        
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(legendX - 10, legendY - 30, 230, 180, 10, 10);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        g.drawString("Priority Legend:", legendX, legendY);
        
        String[] priorities = {"P0 - Critical", "P1 - High", "P2 - Medium", "P3+ - Low"};
        Color[] colors = {
            new Color(0xFF0000),
            new Color(0xFF6600),
            new Color(0xFFD700),
            new Color(0x00FF00)
        };
        
        for (int i = 0; i < priorities.length; i++) {
            int y = legendY + 30 + i * 30;
            g.setColor(colors[i]);
            g.fillRect(legendX, y - 12, 20, 20);
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
            g.drawString(priorities[i], legendX + 30, y);
        }
    }
    
    private Color getPriorityColor(int priority) {
        return switch (priority) {
            case 0 -> new Color(0xFF0000);      // Red - Critical
            case 1 -> new Color(0xFF6600);      // Orange - High
            case 2 -> new Color(0xFFD700);      // Gold - Medium
            default -> new Color(0x00FF00);     // Green - Low
        };
    }
    
    public void enqueue(int value, int priority) {
        PriorityNode newNode = new PriorityNode(value, priority, 0, startY);
        
        // Find correct position based on priority
        int insertIndex = 0;
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).priority > priority) {
                insertIndex = i;
                break;
            }
            insertIndex = i + 1;
        }
        
        queue.add(insertIndex, newNode);
        repositionNodes();
        animateInsertion(newNode, insertIndex);
    }
    
    private void animateInsertion(PriorityNode node, int index) {
        int targetX = startX + index * (nodeWidth + spacing);
        node.xPos = targetX;
        node.yPos = -nodeHeight;
        
        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            if (node.yPos < startY) {
                node.yPos += 10;
                repaint();
            } else {
                node.yPos = startY;
                timer.stop();
                repaint();
            }
        });
        timer.start();
    }
    
    public int dequeue() {
        if (queue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        
        PriorityNode front = queue.get(0);
        int value = front.value;
        
        animateRemoval(front);
        return value;
    }
    
    private void animateRemoval(PriorityNode node) {
        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            node.yPos -= 10;
            if (node.yPos < -nodeHeight - 50) {
                timer.stop();
                queue.remove(0);
                repositionNodes();
                animateShift();
            } else {
                repaint();
            }
        });
        timer.start();
    }
    
    private void repositionNodes() {
        for (int i = 0; i < queue.size(); i++) {
            queue.get(i).xPos = startX + i * (nodeWidth + spacing);
        }
        resize();
    }
    
    private void animateShift() {
        final int[] step = {0};
        final int steps = 15;
        
        Timer timer = new Timer(animationSpeed / steps, null);
        timer.addActionListener(e -> {
            step[0]++;
            repaint();
            
            if (step[0] >= steps) {
                timer.stop();
            }
        });
        timer.start();
    }
    
    private void resize() {
        if (queue.isEmpty()) {
            dynamicWidth = width;
        } else {
            int lastX = queue.get(queue.size() - 1).xPos;
            dynamicWidth = Math.max(width, lastX + nodeWidth + 100);
        }
        setPreferredSize(new Dimension(dynamicWidth, height));
        revalidate();
        repaint();
    }
    
    public void setAnimationSpeed(int speed) {
        this.animationSpeed = speed;
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
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

public class PriorityQueueWindow extends JPanel implements DefaultWindowsInterface {
    private final PriorityQueueVisual visualQueue;
    private final JTextField valueField;
    private final JTextField priorityField;
    private final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    
    public PriorityQueueWindow() {
        setLayout(new BorderLayout());
        
        visualQueue = new PriorityQueueVisual();
        JScrollPane scrollPane = new JScrollPane(visualQueue,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        
        // Control panel
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        controlPanel.setBackground(new Color(0, 18, 121));
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        inputPanel.setBackground(new Color(0, 18, 121));
        
        valueField = createTextField("Value");
        priorityField = createTextField("Priority (0-9)");
        JButton enqueueBtn = createButton("ENQUEUE", new Color(0, 18, 121));
        JButton dequeueBtn = createButton("DEQUEUE", Color.BLACK);
        
        enqueueBtn.addActionListener(e -> {
            if (verifyInput(valueField) && verifyInput(priorityField)) {
                int value = Integer.parseInt(valueField.getText());
                int priority = Integer.parseInt(priorityField.getText());
                if (priority < 0 || priority > 9) {
                    JOptionPane.showMessageDialog(this, "Priority must be 0-9", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                visualQueue.enqueue(value, priority);
                valueField.setText("");
                priorityField.setText("");
            }
        });
        
        dequeueBtn.addActionListener(e -> visualQueue.dequeue());
        
        inputPanel.add(valueField);
        inputPanel.add(priorityField);
        inputPanel.add(enqueueBtn);
        inputPanel.add(dequeueBtn);
        
        // Speed control
        JPanel speedPanel = new JPanel(new BorderLayout());
        speedPanel.setBackground(new Color(0, 18, 121));
        JLabel speedLabel = new JLabel("Animation Speed", SwingConstants.CENTER);
        speedLabel.setForeground(Color.WHITE);
        speedLabel.setFont(font);
        
        JSlider speedSlider = new JSlider(50, 500, 300);
        speedSlider.setBackground(new Color(0, 18, 121));
        speedSlider.setForeground(Color.WHITE);
        speedSlider.addChangeListener(e -> visualQueue.setAnimationSpeed(speedSlider.getValue()));
        
        speedPanel.add(speedLabel, BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);
        
        // Info panel
        JLabel infoLabel = new JLabel("Priority Queue - Elements ordered by Priority", SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(new Color(0, 18, 121));
        
        controlPanel.add(infoLabel);
        controlPanel.add(inputPanel);
        controlPanel.add(speedPanel);
        
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Key listeners
        priorityField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n' && verifyInput(valueField) && verifyInput(priorityField)) {
                    int value = Integer.parseInt(valueField.getText());
                    int priority = Integer.parseInt(priorityField.getText());
                    if (priority >= 0 && priority <= 9) {
                        visualQueue.enqueue(value, priority);
                        valueField.setText("");
                        priorityField.setText("");
                    }
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {}
            
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    
    private JTextField createTextField(String tooltip) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBackground(themeColorBG);
        field.setForeground(Color.WHITE);
        field.setToolTipText(tooltip);
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
    
    private boolean verifyInput(JTextField field) {
        return field.getInputVerifier().verify(field);
    }
}
