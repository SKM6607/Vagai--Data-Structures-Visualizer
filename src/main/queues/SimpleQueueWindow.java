package main.queues;
import shapes.MyArrow;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
final class SimpleQueue extends Queue {
    private final ArrayList<VisualNode> queue = new ArrayList<>();
    private static final Color gridColor=new Color(0x1C233D);
    private final MyArrow arrow = new MyArrow(80, 12);
    SimpleQueue() {
        animationSpeed = 300;
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0xA0F29));
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g, gridColor);
        drawQueueLabels(g);
        drawNodes(g);
    }

    private void drawNodes(Graphics2D g){
        for (VisualNode visualNode : queue) {
            drawNode(g, visualNode);
        }
    }

    private void drawQueueLabels(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        if (!isEmpty()) {
            g.drawString("FRONT →", 50, height / 2 + 10);
            int rearX = queue.getLast().xPos + nodeWidth + 40;
            g.drawString("← REAR", rearX, height / 2 + 10);
        } else {
            g.drawString("QUEUE EMPTY", width / 2 - 100, height / 2);
        }
    }

    public void enqueue(int value) {

    }


    @Override
    public Object[] dequeue() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        VisualNode front = queue.getFirst();
        Object[] o={
                front.data,
                front.getAddress(),
                front.getNextAddress()
        };
        // Animate removal
        animateDequeue(front);
        return o;
    }

    private void animateDequeue(VisualNode node) {
        Timer timer = new Timer(15, null);
        timer.addActionListener(_ -> {
            node.yPos -= 10;
            if (node.yPos < -nodeHeight - 50) {
                timer.stop();
                queue.removeFirst();
                if (!queue.isEmpty()) {
                    queue.get(0).setNextAddress(queue.size() > 1 ? queue.get(1).getAddress() : null);
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
        timer.addActionListener(_ -> {
            step[0]++;
            float progress = (float) step[0] / steps;

            for (int i = 0; i < queue.size(); i++) {
                int targetPos = targetX + i * (nodeWidth + spacing);
                queue.get(i).xPos = (int) (queue.get(i).xPos + (targetPos - queue.get(i).xPos) * progress);
            }

            repaint();

            if (step[0] >= steps) {
                timer.stop();
                super.resize(queue);
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

    @Override
    protected void drawNode(Graphics2D g, Node n, int... args) {
        VisualNode node=(VisualNode) n;
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
        g.drawString(node.getAddress(), x + 5, y - 5);

        // Draw arrow to next node
        if (node.getNextNode() != null) {
            arrow.draw(g, x + nodeWidth, y + nodeHeight / 2, new Color(0xFFD700));
        }
    }

    public void enqueue(Node n,int ...args){
        VisualNode node=(VisualNode) n;
        if (isFull()) {
            JOptionPane.showMessageDialog(this, "Queue is full!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isEmpty()) {
            queue.getLast().setNextNode(node);
        }
        queue.add(node);
    }

    @Override
    public void enqueue(int... args) {
        int xPos = queue.isEmpty() ? 200 : queue.getLast().xPos + nodeWidth + spacing;
        int yPos = height / 2 - nodeHeight / 2;
        enqueue(new VisualNode(args[0],xPos,yPos));
    }
}

public final class SimpleQueueWindow extends QueueWindow<SimpleQueue> {
    private static SimpleQueueWindow singleton = null;

    private SimpleQueueWindow() {
        super(new SimpleQueue());
        final Color color = new Color(0, 18, 121);
        JScrollPane scrollPane = new JScrollPane(visualQueue, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        controlPanel.setBackground(color);
        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        inputPanel.setBackground(color);
        enqueueButton.addActionListener(_ -> {
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
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        inputPanel.setSize(new Dimension(0, 60));
        containerPanel.add(inputPanel);
        containerPanel.add(controlPanel);
        add(containerPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static SimpleQueueWindow createSimpleQueueWindow() {
        return (singleton == null) ? singleton = new SimpleQueueWindow() : singleton;
    }
}
