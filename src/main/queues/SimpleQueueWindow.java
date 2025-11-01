package main.queues;
import shapes.MyArrow;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
final class SimpleQueue extends Queue{
    private final ArrayList<VisualNode> queue = new ArrayList<>();
    private final MyArrow arrow = new MyArrow(80, 12);
    private int animationSpeed = 300;
    SimpleQueue() {
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
    public void dequeue() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        VisualNode front = queue.get(0);
        int value = front.data;
        
        // Animate removal
        animateDequeue(front);

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
    public int sizeQ() {
        return queue.size();
    }
    
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    @Override
    public boolean isFull() {
        int maxCapacity = 15;
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

    protected void drawNode(Graphics2D g, VisualNode node) {
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

}
public final class SimpleQueueWindow extends QueueWindow<SimpleQueue> {
    public SimpleQueueWindow() {
        super(new SimpleQueue());
        JScrollPane scrollPane = new JScrollPane(visualQueue,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        controlPanel.setBackground(new Color(0, 18, 121));
        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        inputPanel.setBackground(new Color(0, 18, 121));
        enqueueButton.addActionListener(e -> {
            if (verifyInput()) {
                visualQueue.enqueue(Integer.parseInt(textField.getText()));
                textField.setText("");
            }
        });
        dequeueButton.addActionListener(_ -> visualQueue.dequeue());
        inputPanel.add(textField);
        inputPanel.add(enqueueButton);
        inputPanel.add(dequeueButton);
        JLabel infoLabel = setInfoLabel("Simple Queue (FIFO) - First In First Out");
        controlPanel.add(infoLabel);
        controlPanel.add(inputPanel);
        controlPanel.add(speedPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
}
