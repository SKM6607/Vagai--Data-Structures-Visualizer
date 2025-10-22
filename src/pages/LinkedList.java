package pages;
import shapes.MyArrow;
import pages.interfaces.DefaultWindowsInterface;
import pages.interfaces.GridInterface;
import pages.interfaces.LinkedListLightWeightInterface;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
class LinkedListVisual extends JPanel implements LinkedListLightWeightInterface, GridInterface {
    public static final int nodeSpacing = (3 * nodeWidth) / 2 + SPACING;
    private static final MyArrow arrow = new MyArrow(nodeWidth / 2, 20);
    VisualNode head = new VisualNode(0, width / 4, height / 2 - nodeHeight);
    private int dynamicWidth = width;
    private int size = 1;
    private boolean isAnimating = false;

    LinkedListVisual() {
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0xA0F29));
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        drawGrid(g, new Color(0x1C233D));
        drawTitle(g);
        
        VisualNode temp = head;
        while (temp != null) {
            drawNode(g, temp);
            temp = temp.nextNode;
        }
    }
    
    private void drawTitle(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        g.drawString("Linked List Visualization", width / 2 - 180, 50);
        
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.setColor(new Color(0xFFD700));
        g.drawString("Size: " + size, width / 2 - 30, 80);
        
        // Draw HEAD pointer
        g.setColor(Color.GREEN);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString("HEAD", head.xPos + nodeWidth / 2 - 25, head.yPos - 40);
        
        // Draw arrow to head
        g.setStroke(new BasicStroke(3f));
        g.drawLine(head.xPos + nodeWidth / 2, head.yPos - 35, head.xPos + nodeWidth / 2, head.yPos - 10);
        int[] xPoints = {head.xPos + nodeWidth / 2, head.xPos + nodeWidth / 2 - 8, head.xPos + nodeWidth / 2 + 8};
        int[] yPoints = {head.yPos - 10, head.yPos - 20, head.yPos - 20};
        g.fillPolygon(xPoints, yPoints, 3);
    }
    @Override
    public void drawGrid(Graphics2D g, Color color){
        Color retColor=g.getColor();
        g.setColor(color);
        for (int i = 0; i < dynamicWidth; i+=SPACING) {
            g.drawLine(i,0,i,height);
        }
        for (int i = 0; i < height; i+=SPACING) {
            g.drawLine(0,i,dynamicWidth,i);
        }
        g.setColor(retColor);
    }

    @Override
    public void append(int value) {
        if (isAnimating) return;
        
        int targetX = head.xPos + size * nodeSpacing;
        VisualNode newNode = new VisualNode(value, targetX, -nodeHeight);
        VisualNode temp = head;
        while (temp.nextNode != null) {
            temp = temp.nextNode;
        }
        temp.nextAddress = newNode.address;
        temp.nextNode = newNode;
        size++;
        resize();
        
        // Smooth drop animation
        animateAppend(newNode, head.yPos);
    }
    
    private void animateAppend(VisualNode node, int targetY) {
        isAnimating = true;
        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            if (node.yPos < targetY) {
                node.yPos += 12;
                repaint();
            } else {
                node.yPos = targetY;
                isAnimating = false;
                timer.stop();
                repaint();
            }
        });
        timer.start();
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(dynamicWidth,height);
    }

    private void resize() {
        int targetWidth = calculateSize() + 8 * SPACING;
        targetWidth = Math.max(width, targetWidth);
        if (Math.abs(dynamicWidth - targetWidth) > nodeSpacing / 2) {
            dynamicWidth = targetWidth;
            setPreferredSize(new Dimension(dynamicWidth, height));
            revalidate();
            repaint();
        }
    }

    public void setCamCentered(JScrollPane scrollPane) {
        JViewport viewport = scrollPane.getViewport();
        Rectangle rectangle = viewport.getViewRect();
        viewport.setViewPosition(new Point(
                 Math.max(rectangle.width/2,calculateSize()- rectangle.width/2), height/2
        ));
        resize();
        scrollPane.setViewport(viewport);
    }

    @Override
    public void pop() {
        if (size == 1) return;
        VisualNode temp = head, prev = head;
        while (temp.nextNode != null) {
            prev = temp;
            temp = temp.nextNode;
        }
        prev.nextNode = null;
        prev.nextAddress = null;
        size--;

    }

    @Override
    public int sizeLL() {
        return size;
    }

    protected int calculateSize() {
        return head.xPos + sizeLL() * nodeSpacing;
    }

    @Override
    public void drawNode(Graphics2D g, VisualNode node) {
        int x = node.xPos;
        int y = node.yPos;
        int value = node.data;
        g.setColor(new Color(0x1E3A8A));
        g.fillRect(x, y, nodeWidth, nodeHeight);
        g.setStroke(new BasicStroke(4f));
        g.setColor(new Color(0xFFD700));
        g.drawLine(x + nodeWidth / 2, y, x + nodeWidth / 2, y + nodeHeight);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        g.drawString(String.valueOf(value), x + (float) nodeWidth / 4, y + nodeWidth / 3.5f);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString((node.isLast()) ? "NULL" : node.nextAddress, x + nodeWidth / 1.75f, y + nodeHeight/2f);
        g.setColor(Color.WHITE);
        g.drawString(node.address, x + nodeWidth / 3, y - SPACING);
        if (!node.isLast()) arrow.draw(g, x + nodeWidth, y + nodeHeight / 2, Color.YELLOW);
    }
}

public class LinkedList extends JPanel implements DefaultWindowsInterface {
    private final JTextField textField;
    private static final Font font =  new Font(Font.SANS_SERIF, Font.BOLD, 20);
    public LinkedList() {
        JLayeredPane layeredPane = new JLayeredPane();
        LinkedListVisual visualPanel = new LinkedListVisual();
        JScrollPane wrapper = new JScrollPane(visualPanel);
        wrapper.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        wrapper.getHorizontalScrollBar().setUnitIncrement(35);
        JPanel mainPanel = new JPanel();
        JButton[] basicButtons = new JButton[2];
        layeredPane.setPreferredSize(new Dimension(width, height));
        wrapper.setBounds(0, 0, width, height);
        layeredPane.add(wrapper, JLayeredPane.DEFAULT_LAYER);
        mainPanel.setLayout(new GridLayout(2, 1, 10, 10));
        mainPanel.setBackground(new Color(0, 0, 0, 180));
        mainPanel.setBounds(width / 2 - 170, height -200, 300, 100);
        {
            basicButtons[0] = new JButton("APPEND");
            basicButtons[1] = new JButton("POP");
            textField = appendTextInput();
            {
                basicButtons[0].setFont(font);
                basicButtons[1].setFont(font);
                basicButtons[0].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                basicButtons[1].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                basicButtons[0].setBackground(new Color(0, 18, 121));
                basicButtons[0].setOpaque(true);
                basicButtons[0].setForeground(Color.WHITE);
                basicButtons[1].setBackground(new Color(0, 18, 121));
                basicButtons[1].setOpaque(true);
                basicButtons[1].setForeground(Color.WHITE);
                textField.setBackground(new Color(0, 18, 121));
                textField.setOpaque(true);
                textField.setForeground(Color.WHITE);
            }
            JPanel subPanel = new JPanel(new GridLayout(1, 2, 10, 0));
            subPanel.setBackground(Color.BLACK);
            subPanel.add(textField);
            subPanel.add(basicButtons[0]);
            mainPanel.add(subPanel);
            mainPanel.add(basicButtons[1]);
        }
        layeredPane.add(mainPanel, JLayeredPane.PALETTE_LAYER);
        add(layeredPane);
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (verifyTextField(textField) && e.getKeyChar()=='\n'){
                    visualPanel.append(Integer.parseInt(textField.getText()));
                    textField.setText("");
                    visualPanel.setCamCentered(wrapper);
                    visualPanel.repaint();
                }
                if(e.getKeyChar()==127){
                    visualPanel.pop();
                    visualPanel.setCamCentered(wrapper);
                    visualPanel.repaint();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        basicButtons[0].addActionListener(_ -> {
            if (verifyTextField(textField)) {
                visualPanel.append(Integer.parseInt(textField.getText()));
                textField.setText("");
                visualPanel.setCamCentered(wrapper);
                visualPanel.repaint();
            }
        });
        basicButtons[1].addActionListener(_ -> {
            visualPanel.pop();
            visualPanel.setCamCentered(wrapper);
            visualPanel.repaint();
        });
    }

    private static @NotNull JTextField appendTextInput() {
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setToolTipText("Value for the next node (Integer)");
        textField.setInputVerifier(new InputVerifier() {
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
        return textField;
    }

    private static boolean verifyTextField(JTextField textField) {
        InputVerifier verifier = textField.getInputVerifier();
        return verifier.verify(textField);
    }

}