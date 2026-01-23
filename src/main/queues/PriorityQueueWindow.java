package main.queues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

final class PriorityQueue extends Queue {
    private final ArrayList<PriorityNode> queue = new ArrayList<>();
    // ===== Colors =====
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 50);
    private static final Color BADGE_TEXT_COLOR = Color.WHITE;
    private static final Color VALUE_TEXT_COLOR = Color.WHITE;
    private static final Color LABEL_COLOR = Color.YELLOW;

    // ===== Fonts =====
    private static final Font BADGE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    private static final Font VALUE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 28);
    private static final Font LABEL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    private static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
    private static final Font TEXT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
    private static final Font EMPTY_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 32);

    // ===== Stroke =====
    private static final BasicStroke BORDER_STROKE = new BasicStroke(4f);
    // ===== Arrow styling =====
    private static final Color ARROW_COLOR = new Color(0xFFD700);
    private static final BasicStroke ARROW_STROKE = new BasicStroke(3f);
    private static final int ARROW_SIZE = 8;

    // ===== Legend styling =====
    private static final Color LEGEND_BG_COLOR = new Color(0, 0, 0, 180);
    private static final Color LEGEND_TEXT_COLOR = Color.WHITE;
    private static final Font LEGEND_TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    private static final Font LEGEND_ITEM_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

    // ===== Priority colors =====
    private static final Color PRIORITY_CRITICAL_COLOR = new Color(0xFF0000);
    private static final Color PRIORITY_HIGH_COLOR = new Color(0xFF6600);
    private static final Color PRIORITY_MEDIUM_COLOR = new Color(0xFFD700);
    private static final Color PRIORITY_LOW_COLOR = new Color(0x00FF00);

    // ===== Legend labels =====
    private static final String[] PRIORITY_LABELS = {
            "P0 - Critical",
            "P1 - High",
            "P2 - Medium",
            "P3+ - Low"
    };

    // ===== Legend color map =====
    private static final Color[] PRIORITY_COLORS = {
            PRIORITY_CRITICAL_COLOR,
            PRIORITY_HIGH_COLOR,
            PRIORITY_MEDIUM_COLOR,
            PRIORITY_LOW_COLOR
    };

    PriorityQueue() {
        animationSpeed = 300;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintGrid(g, null, gridColor);
        drawTitle(g);
        drawNodes(g);
        drawPriorityLegend(g);
    }

    private void drawTitle(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(TITLE_FONT);
        g.drawString("Priority Queue (Higher Priority → Lower Number)", width / 2 - 300, 50);

        if (isEmpty()) {
            g.setFont(EMPTY_FONT);
            g.drawString("QUEUE EMPTY", width / 2 - 120, height / 2);
        } else {
            g.setFont(TEXT_FONT);
            g.setColor(Color.GREEN);
            g.drawString("HIGHEST PRIORITY →", 20, startY + nodeHeight / 2);
        }
    }

    private void drawNodes(Graphics2D g) {
        for (PriorityNode node : queue) drawNode(g, node);
    }

    private void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.setColor(ARROW_COLOR);
        g.setStroke(ARROW_STROKE);
        g.drawLine(x1, y1, x2, y2);

        // Arrow head
        int[] xPoints = {x2, x2 - ARROW_SIZE, x2 - ARROW_SIZE};
        int[] yPoints = {y1, y1 - ARROW_SIZE, y1 + ARROW_SIZE};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawPriorityLegend(Graphics2D g) {
        int legendX = width - 250;
        int legendY = 100;

        g.setColor(LEGEND_BG_COLOR);
        g.fillRoundRect(legendX - 10, legendY - 30, 230, 180, 10, 10);

        g.setColor(LEGEND_TEXT_COLOR);
        g.setFont(LEGEND_TITLE_FONT);
        g.drawString("Priority Legend:", legendX, legendY);

        for (int i = 0; i < PRIORITY_LABELS.length; i++) {
            int y = legendY + 30 + i * 30;

            g.setColor(PRIORITY_COLORS[i]);
            g.fillRect(legendX, y - 12, 20, 20);

            g.setColor(LEGEND_TEXT_COLOR);
            g.setFont(LEGEND_ITEM_FONT);
            g.drawString(PRIORITY_LABELS[i], legendX + 30, y);
        }
    }

    private Color getPriorityColor(int priority) {
        return switch (priority) {
            case 0 -> PRIORITY_CRITICAL_COLOR;
            case 1 -> PRIORITY_HIGH_COLOR;
            case 2 -> PRIORITY_MEDIUM_COLOR;
            default -> PRIORITY_LOW_COLOR;
        };
    }


    private void enqueue(int value, int priority) {
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
        node.xPos = startX + index * (nodeWidth + spacing);
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


    public Object[] dequeue() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(this, "Queue is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        PriorityNode front = queue.getFirst();
        Object[] o = {
                front.data,
                front.priority,
                front.getAddress(),
                front.getNextAddress()
        };
        animateRemoval(front);
        return o;
    }

    @Override
    public int sizeQ() {
        return queue.size();
    }

    private void animateRemoval(PriorityNode node) {
        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            node.yPos -= 10;
            if (node.yPos < -nodeHeight - 50) {
                timer.stop();
                queue.removeFirst();
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


    protected void drawNode(Graphics2D g, Node n, int... args) {
        PriorityNode node = (PriorityNode) n;

        // Draw shadow
        g.setColor(SHADOW_COLOR);
        g.fillRoundRect(node.xPos + 4, node.yPos + 4, nodeWidth, nodeHeight, 15, 15);

        // Draw node with gradient effect (must stay dynamic)
        GradientPaint gradient = new GradientPaint(
                node.xPos, node.yPos, node.color,
                node.xPos, node.yPos + nodeHeight, node.color.darker()
        );
        g.setPaint(gradient);
        g.fillRoundRect(node.xPos, node.yPos, nodeWidth, nodeHeight, 15, 15);

        // Draw border
        g.setColor(getPriorityColor(node.priority));
        g.setStroke(BORDER_STROKE);
        g.drawRoundRect(node.xPos, node.yPos, nodeWidth, nodeHeight, 15, 15);

        // Draw priority badge
        int badgeSize = 30;
        g.setColor(getPriorityColor(node.priority));
        g.fillOval(node.xPos + nodeWidth - badgeSize - 5, node.yPos + 5, badgeSize, badgeSize);

        g.setColor(BADGE_TEXT_COLOR);
        g.setFont(BADGE_FONT);
        String priorityStr = "P" + node.priority;
        FontMetrics fm = g.getFontMetrics();
        g.drawString(priorityStr,
                node.xPos + nodeWidth - badgeSize / 2 - fm.stringWidth(priorityStr) / 2 - 5,
                node.yPos + 22);

        // Draw value
        g.setColor(VALUE_TEXT_COLOR);
        g.setFont(VALUE_FONT);
        String valueStr = String.valueOf(node.data);
        fm = g.getFontMetrics();
        g.drawString(valueStr,
                node.xPos + nodeWidth / 2 - fm.stringWidth(valueStr) / 2,
                node.yPos + nodeHeight / 2 + 10);

        // Draw label
        g.setFont(LABEL_FONT);
        g.setColor(LABEL_COLOR);
        g.drawString("Value", node.xPos + nodeWidth / 2 - 15, node.yPos + nodeHeight - 10);

        // Draw arrow
        if (queue.indexOf(node) < queue.size() - 1) {
            drawArrow(g,
                    node.xPos + nodeWidth, node.yPos + nodeHeight / 2,
                    node.xPos + nodeWidth + spacing, node.yPos + nodeHeight / 2);
        }
    }


    /**
     * @param args 0 should be value, 1 should be priority
     *
     */
    @Override
    public void enqueue(int... args) {
        int value = args[0];
        int priority = args[1];
        enqueue(value, priority);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean isFull() {
        //TODO ADD SOME CAPACITY
        return false;
    }

}

public final class PriorityQueueWindow extends QueueWindow<PriorityQueue> {
    private static PriorityQueueWindow singleton = null;
    private final JTextField priorityField;

    private PriorityQueueWindow() {
        super(new PriorityQueue());
        JScrollPane scrollPane = new JScrollPane(visualQueue,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        controlPanel.setBackground(backgroundColor);
        JPanel inputPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        inputPanel.setBackground(backgroundColor);
        textField = createTextField("Value");
        priorityField = createTextField("Priority (0-9)");
        enqueueButton.addActionListener(_ -> {
            if (verifyInput(textField) && verifyInput(priorityField)) {
                int value = Integer.parseInt(textField.getText());
                int priority = Integer.parseInt(priorityField.getText());
                if (priority < 0 || priority > 9) {
                    JOptionPane.showMessageDialog(this, "Priority must be 0-9", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                visualQueue.enqueue(value, priority);
                textField.setText("");
                priorityField.setText("");
            }
        });
        dequeueButton.addActionListener(_ -> visualQueue.dequeue());
        inputPanel.add(textField);
        inputPanel.add(priorityField);
        inputPanel.add(enqueueButton);
        inputPanel.add(dequeueButton);
        infoLabel = setInfoLabel("Priority Queue - Elements ordered by Priority");
        controlPanel.add(infoLabel);
        controlPanel.add(inputPanel);
        controlPanel.add(speedPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        priorityField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n' && verifyInput(textField) && verifyInput(priorityField)) {
                    int value = Integer.parseInt(textField.getText());
                    int priority = Integer.parseInt(priorityField.getText());
                    if (priority >= 0 && priority <= 9) {
                        visualQueue.enqueue(value, priority);
                        textField.setText("");
                        priorityField.setText("");
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public static PriorityQueueWindow createPriorityQueueWindow() {
        return (singleton == null) ? singleton = new PriorityQueueWindow() : singleton;
    }

    private JTextField createTextField(String tooltip) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBackground(backgroundColor);
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

    private boolean verifyInput(JTextField field) {
        return field.getInputVerifier().verify(field);
    }
}