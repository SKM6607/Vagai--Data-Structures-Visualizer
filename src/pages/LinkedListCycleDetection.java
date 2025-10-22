package pages;

import pages.interfaces.DefaultWindowsInterface;
import pages.interfaces.GridInterface;
import pages.interfaces.LinkedListInterface.VisualNode;
import shapes.MyArrow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CycleDetectionVisual extends JPanel implements GridInterface {
    private final List<VisualNode> nodes = new ArrayList<>();
    private final MyArrow arrow = new MyArrow(80, 15);
    private final int nodeWidth = 100;
    private final int nodeHeight = 80;
    private final int spacing = 30;
    private int dynamicWidth = width;
    private int animationSpeed = 500;
    
    private VisualNode slowPointer = null;
    private VisualNode fastPointer = null;
    private Set<VisualNode> visitedNodes = new HashSet<>();
    private boolean detectionRunning = false;
    private String detectionResult = "";
    private String currentAlgorithm = "";
    
    CycleDetectionVisual() {
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0xA0F29));
        
        // Initialize with sample nodes
        initializeSampleList();
    }
    
    private void initializeSampleList() {
        nodes.clear();
        int startX = 150;
        int startY = 200;
        
        for (int i = 0; i < 5; i++) {
            VisualNode node = new VisualNode(i + 1, startX + i * (nodeWidth + spacing), startY);
            if (i > 0) {
                nodes.get(i - 1).nextNode = node;
                nodes.get(i - 1).nextAddress = node.address;
            }
            nodes.add(node);
        }
        resize();
    }
    
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g, new Color(0x1C233D));
        
        drawTitle(g);
        drawNodes(g);
        drawPointers(g);
        drawResult(g);
    }
    
    private void drawTitle(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        g.drawString("Linked List Cycle Detection", width / 2 - 250, 50);
        
        if (!currentAlgorithm.isEmpty()) {
            g.setColor(new Color(0xFFD700));
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString("Algorithm: " + currentAlgorithm, width / 2 - 150, 90);
        }
    }
    
    private void drawNodes(Graphics2D g) {
        // Draw connections first
        for (VisualNode node : nodes) {
            if (node.nextNode != null) {
                int fromX = node.xPos + nodeWidth;
                int fromY = node.yPos + nodeHeight / 2;
                int toX = node.nextNode.xPos;
                int toY = node.nextNode.yPos + nodeHeight / 2;
                
                // Check if this is a cycle connection
                if (nodes.indexOf(node.nextNode) <= nodes.indexOf(node)) {
                    // Draw curved arrow for cycle
                    drawCurvedArrow(g, fromX, fromY, toX, toY);
                } else {
                    // Normal arrow
                    arrow.draw(g, fromX, fromY, new Color(0xFFD700));
                }
            }
        }
        
        // Draw nodes
        for (VisualNode node : nodes) {
            drawNode(g, node);
        }
    }
    
    private void drawNode(Graphics2D g, VisualNode node) {
        int x = node.xPos;
        int y = node.yPos;
        
        // Determine color based on state
        Color nodeColor = new Color(0x1E3A8A);
        if (visitedNodes.contains(node)) {
            nodeColor = new Color(0x9333EA); // Purple for visited (HashSet)
        }
        if (node == slowPointer) {
            nodeColor = new Color(0x059669); // Green for slow pointer
        }
        if (node == fastPointer) {
            nodeColor = new Color(0xDC2626); // Red for fast pointer
        }
        if (node == slowPointer && node == fastPointer) {
            nodeColor = new Color(0xFF00FF); // Magenta when both meet
        }
        
        // Draw shadow
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRoundRect(x + 4, y + 4, nodeWidth, nodeHeight, 10, 10);
        
        // Draw node
        g.setColor(nodeColor);
        g.fillRoundRect(x, y, nodeWidth, nodeHeight, 10, 10);
        
        // Draw border
        g.setColor(new Color(0xFFD700));
        g.setStroke(new BasicStroke(3f));
        g.drawRoundRect(x, y, nodeWidth, nodeHeight, 10, 10);
        
        // Draw data
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        g.setColor(Color.WHITE);
        String dataStr = String.valueOf(node.data);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(dataStr, x + (nodeWidth - fm.stringWidth(dataStr)) / 2, y + nodeHeight / 2 + 10);
        
        // Draw address
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g.setColor(Color.YELLOW);
        g.drawString(node.address.substring(0, Math.min(10, node.address.length())), x + 5, y - 5);
    }
    
    private void drawCurvedArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.setColor(new Color(0xFF6600));
        g.setStroke(new BasicStroke(3f));
        
        // Calculate control points for bezier curve
        int controlY = y1 - 100;
        
        // Draw curved line
        for (float t = 0; t <= 1.0f; t += 0.01f) {
            int x = (int) ((1 - t) * (1 - t) * x1 + 2 * (1 - t) * t * ((x1 + x2) / 2) + t * t * x2);
            int y = (int) ((1 - t) * (1 - t) * y1 + 2 * (1 - t) * t * controlY + t * t * y2);
            g.fillRect(x, y, 3, 3);
        }
        
        // Draw arrow head
        g.setColor(new Color(0xFF6600));
        int[] xPoints = {x2, x2 - 10, x2 - 10};
        int[] yPoints = {y2, y2 - 8, y2 + 8};
        g.fillPolygon(xPoints, yPoints, 3);
        
        // Draw "CYCLE" label
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        g.drawString("CYCLE", (x1 + x2) / 2 - 20, controlY - 10);
    }
    
    private void drawPointers(Graphics2D g) {
        if (slowPointer != null) {
            drawPointerLabel(g, slowPointer, "SLOW", new Color(0x059669), -40);
        }
        if (fastPointer != null) {
            drawPointerLabel(g, fastPointer, "FAST", new Color(0xDC2626), -20);
        }
    }
    
    private void drawPointerLabel(Graphics2D g, VisualNode node, String label, Color color, int yOffset) {
        g.setColor(color);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        int x = node.xPos + nodeWidth / 2 - 20;
        int y = node.yPos + yOffset;
        g.drawString(label, x, y);
        
        // Draw arrow pointing down
        g.setStroke(new BasicStroke(2f));
        g.drawLine(x + 20, y + 5, x + 20, node.yPos - 5);
        int[] xPoints = {x + 20, x + 15, x + 25};
        int[] yPoints = {node.yPos - 5, node.yPos - 15, node.yPos - 15};
        g.fillPolygon(xPoints, yPoints, 3);
    }
    
    private void drawResult(Graphics2D g) {
        if (!detectionResult.isEmpty()) {
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(detectionResult);
            
            // Draw background
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRoundRect(width / 2 - textWidth / 2 - 20, height - 150, textWidth + 40, 60, 15, 15);
            
            // Draw text
            if (detectionResult.contains("FOUND")) {
                g.setColor(new Color(0xFF0000));
            } else {
                g.setColor(new Color(0x00FF00));
            }
            g.drawString(detectionResult, width / 2 - textWidth / 2, height - 110);
        }
    }
    
    public void appendNode(int value) {
        if (detectionRunning) {
            JOptionPane.showMessageDialog(this, "Wait for detection to complete!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int x = nodes.isEmpty() ? 150 : nodes.get(nodes.size() - 1).xPos + nodeWidth + spacing;
        VisualNode newNode = new VisualNode(value, x, 200);
        
        if (!nodes.isEmpty()) {
            VisualNode last = nodes.get(nodes.size() - 1);
            // Remove any existing cycle first
            if (last.nextNode != null && nodes.indexOf(last.nextNode) < nodes.size() - 1) {
                last.nextNode = null;
                last.nextAddress = null;
            }
            last.nextNode = newNode;
            last.nextAddress = newNode.address;
        }
        
        nodes.add(newNode);
        animateNodeAppear(newNode);
        resize();
    }
    
    private void animateNodeAppear(VisualNode node) {
        int originalY = node.yPos;
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
    
    public void createCycle(int cycleToIndex) {
        if (detectionRunning) return;
        if (nodes.size() < 2) {
            JOptionPane.showMessageDialog(this, "Need at least 2 nodes!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cycleToIndex < 0 || cycleToIndex >= nodes.size()) {
            JOptionPane.showMessageDialog(this, "Invalid cycle index!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        VisualNode last = nodes.get(nodes.size() - 1);
        last.nextNode = nodes.get(cycleToIndex);
        last.nextAddress = nodes.get(cycleToIndex).address;
        repaint();
    }
    
    public void removeCycle() {
        if (detectionRunning) return;
        if (!nodes.isEmpty()) {
            VisualNode last = nodes.get(nodes.size() - 1);
            last.nextNode = null;
            last.nextAddress = null;
        }
        repaint();
    }
    
    public void detectCycleFloyd() {
        if (nodes.isEmpty()) return;
        if (detectionRunning) {
            JOptionPane.showMessageDialog(this, "Detection already running!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        detectionRunning = true;
        currentAlgorithm = "Floyd's Cycle Detection (Tortoise & Hare)";
        detectionResult = "";
        visitedNodes.clear();
        slowPointer = nodes.get(0);
        fastPointer = nodes.get(0);
        
        Timer timer = new Timer(animationSpeed, null);
        timer.addActionListener(e -> {
            // Move slow pointer one step
            if (slowPointer.nextNode != null) {
                slowPointer = slowPointer.nextNode;
            } else {
                detectionResult = "NO CYCLE FOUND";
                slowPointer = null;
                fastPointer = null;
                detectionRunning = false;
                timer.stop();
                repaint();
                return;
            }
            
            // Move fast pointer two steps
            if (fastPointer.nextNode != null) {
                fastPointer = fastPointer.nextNode;
                if (fastPointer.nextNode != null) {
                    fastPointer = fastPointer.nextNode;
                } else {
                    detectionResult = "NO CYCLE FOUND";
                    slowPointer = null;
                    fastPointer = null;
                    detectionRunning = false;
                    timer.stop();
                    repaint();
                    return;
                }
            } else {
                detectionResult = "NO CYCLE FOUND";
                slowPointer = null;
                fastPointer = null;
                detectionRunning = false;
                timer.stop();
                repaint();
                return;
            }
            
            // Check if they meet
            if (slowPointer == fastPointer) {
                detectionResult = "CYCLE FOUND! Pointers met at node " + slowPointer.data;
                detectionRunning = false;
                timer.stop();
            }
            
            repaint();
        });
        timer.start();
    }
    
    public void detectCycleHashSet() {
        if (nodes.isEmpty()) return;
        if (detectionRunning) {
            JOptionPane.showMessageDialog(this, "Detection already running!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        detectionRunning = true;
        currentAlgorithm = "Hash Set Based Detection";
        detectionResult = "";
        visitedNodes.clear();
        slowPointer = null;
        fastPointer = null;
        
        final VisualNode[] current = {nodes.get(0)};
        
        Timer timer = new Timer(animationSpeed, null);
        timer.addActionListener(e -> {
            if (visitedNodes.contains(current[0])) {
                detectionResult = "CYCLE FOUND! Revisited node " + current[0].data;
                detectionRunning = false;
                timer.stop();
                repaint();
                return;
            }
            
            visitedNodes.add(current[0]);
            
            if (current[0].nextNode == null) {
                detectionResult = "NO CYCLE FOUND";
                detectionRunning = false;
                timer.stop();
                repaint();
                return;
            }
            
            current[0] = current[0].nextNode;
            repaint();
        });
        timer.start();
    }
    
    public void detectCycleBrent() {
        if (nodes.isEmpty()) return;
        if (detectionRunning) {
            JOptionPane.showMessageDialog(this, "Detection already running!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        detectionRunning = true;
        currentAlgorithm = "Brent's Cycle Detection";
        detectionResult = "";
        visitedNodes.clear();
        slowPointer = nodes.get(0);
        fastPointer = nodes.get(0);
        
        final int[] power = {1};
        final int[] lambda = {1};
        
        Timer timer = new Timer(animationSpeed, null);
        timer.addActionListener(e -> {
            // Move fast pointer
            if (fastPointer.nextNode != null) {
                fastPointer = fastPointer.nextNode;
            } else {
                detectionResult = "NO CYCLE FOUND";
                slowPointer = null;
                fastPointer = null;
                detectionRunning = false;
                timer.stop();
                repaint();
                return;
            }
            
            // Check if they meet
            if (slowPointer == fastPointer) {
                detectionResult = "CYCLE FOUND! (Brent's Algorithm) at node " + slowPointer.data;
                detectionRunning = false;
                timer.stop();
                repaint();
                return;
            }
            
            // Check if power needs to be updated
            if (lambda[0] == power[0]) {
                slowPointer = fastPointer;
                power[0] *= 2;
                lambda[0] = 0;
            }
            
            lambda[0]++;
            repaint();
        });
        timer.start();
    }
    
    public void reset() {
        detectionRunning = false;
        currentAlgorithm = "";
        detectionResult = "";
        slowPointer = null;
        fastPointer = null;
        visitedNodes.clear();
        repaint();
    }
    
    private void resize() {
        if (nodes.isEmpty()) {
            dynamicWidth = width;
        } else {
            int lastX = nodes.get(nodes.size() - 1).xPos;
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

public class LinkedListCycleDetection extends JPanel implements DefaultWindowsInterface {
    private final CycleDetectionVisual visualPanel;
    private final JTextField valueField;
    private final JTextField cycleField;
    private final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    
    public LinkedListCycleDetection() {
        setLayout(new BorderLayout());
        
        visualPanel = new CycleDetectionVisual();
        JScrollPane scrollPane = new JScrollPane(visualPanel,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        
        // Control panel
        JPanel controlPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        controlPanel.setBackground(new Color(0, 18, 121));
        
        // Node operations
        JPanel nodePanel = new JPanel(new GridLayout(1, 2, 5, 5));
        nodePanel.setBackground(new Color(0, 18, 121));
        
        valueField = createTextField("Node Value");
        JButton addBtn = createButton("ADD NODE", new Color(0, 18, 121));
        addBtn.addActionListener(e -> {
            if (verifyInput(valueField)) {
                visualPanel.appendNode(Integer.parseInt(valueField.getText()));
                valueField.setText("");
            }
        });
        
        nodePanel.add(valueField);
        nodePanel.add(addBtn);
        
        // Cycle operations
        JPanel cyclePanel = new JPanel(new GridLayout(1, 3, 5, 5));
        cyclePanel.setBackground(new Color(0, 18, 121));
        
        cycleField = createTextField("Cycle to Index");
        JButton createCycleBtn = createButton("CREATE CYCLE", new Color(0xFF6600));
        JButton removeCycleBtn = createButton("REMOVE CYCLE", Color.BLACK);
        
        createCycleBtn.addActionListener(e -> {
            if (verifyInput(cycleField)) {
                visualPanel.createCycle(Integer.parseInt(cycleField.getText()));
                cycleField.setText("");
            }
        });
        removeCycleBtn.addActionListener(e -> visualPanel.removeCycle());
        
        cyclePanel.add(cycleField);
        cyclePanel.add(createCycleBtn);
        cyclePanel.add(removeCycleBtn);
        
        // Detection buttons
        JPanel detectionPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        detectionPanel.setBackground(new Color(0, 18, 121));
        
        JButton floydBtn = createButton("FLOYD'S ALGORITHM", new Color(0, 100, 0));
        JButton hashSetBtn = createButton("HASHSET METHOD", new Color(0, 0, 139));
        JButton resetBtn = createButton("RESET", new Color(139, 0, 0));
        
        floydBtn.addActionListener(e -> visualPanel.detectCycleFloyd());
        hashSetBtn.addActionListener(e -> visualPanel.detectCycleHashSet());
        resetBtn.addActionListener(e -> visualPanel.reset());
        
        detectionPanel.add(floydBtn);
        detectionPanel.add(hashSetBtn);
        detectionPanel.add(resetBtn);
        
        // Speed control
        JPanel speedPanel = new JPanel(new BorderLayout());
        speedPanel.setBackground(new Color(0, 18, 121));
        JLabel speedLabel = new JLabel("Animation Speed", SwingConstants.CENTER);
        speedLabel.setForeground(Color.WHITE);
        speedLabel.setFont(font);
        
        JSlider speedSlider = new JSlider(200, 1000, 500);
        speedSlider.setBackground(new Color(0, 18, 121));
        speedSlider.setForeground(Color.WHITE);
        speedSlider.addChangeListener(e -> visualPanel.setAnimationSpeed(speedSlider.getValue()));
        
        speedPanel.add(speedLabel, BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);
        
        controlPanel.add(nodePanel);
        controlPanel.add(cyclePanel);
        controlPanel.add(detectionPanel);
        controlPanel.add(speedPanel);
        
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Key listener
        valueField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n' && verifyInput(valueField)) {
                    visualPanel.appendNode(Integer.parseInt(valueField.getText()));
                    valueField.setText("");
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
