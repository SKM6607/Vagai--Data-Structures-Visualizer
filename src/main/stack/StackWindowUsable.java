package main.stack;

import main.interfaces.DefaultWindowsInterface;
import utils.main.MainCardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StackWindowUsable extends JPanel implements DefaultWindowsInterface {
    private final int myHeight = DefaultWindowsInterface.height;
    private final int myWidth = DefaultWindowsInterface.width;
    private JScrollPane scrollPane;
    private JTextField textField;
    private JButton pushButton;
    private JButton popButton;
    private final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    private final StackWindow visualStackWindow;

    public StackWindowUsable() {
        visualStackWindow = new StackWindow();
        visualStackWindow.setPreferredSize(new Dimension(myWidth, myHeight));

        setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(myWidth, myHeight));

        scrollPane = new JScrollPane(
                visualStackWindow,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(32);

        textField = textInput();
        pushButton = new JButton("PUSH");
        pushButton.setBackground(DefaultWindowsInterface.backgroundColor);
        pushButton.setForeground(Color.WHITE);
        pushButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pushButton.setFont(font);
        pushButton.addActionListener(e -> {
            if (textField.getInputVerifier().verify(textField)) {
                visualStackWindow.push(Integer.parseInt(textField.getText()));
                textField.setText("");
                visualStackWindow.setCamCentered(scrollPane);
            }
        });
        pushButton.setPreferredSize(new Dimension(100, 50));

        popButton = new JButton("POP");
        popButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        popButton.setFont(font);
        popButton.setForeground(Color.WHITE);
        popButton.setBackground(Color.BLACK);
        popButton.addActionListener(e -> {
            visualStackWindow.pop();
            visualStackWindow.setCamCentered(scrollPane);
        });
        popButton.setPreferredSize(new Dimension(200, 50));

        scrollPane.setBounds(0, 0, myWidth, myHeight);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel subPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        subPanel.add(textField);
        subPanel.add(pushButton);

        mainPanel.add(subPanel, BorderLayout.NORTH);
        mainPanel.add(popButton, BorderLayout.SOUTH);
        mainPanel.setBounds(myWidth / 2 - 150, myHeight - 200, 200, 100);

        layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);
        this.add(layeredPane, BorderLayout.CENTER);
        this.add(mainPanel, BorderLayout.SOUTH);
    }

    private JTextField textInput() {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(100, 50));
        tf.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        tf.setFont(font);
        tf.setBackground(DefaultWindowsInterface.backgroundColor);
        tf.setForeground(Color.WHITE);
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setToolTipText("Value of the next block (Int): ");
        tf.setInputVerifier(new InputVerifier() {
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
        tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (tf.getInputVerifier().verify(tf) && e.getKeyChar() == '\n') {
                    visualStackWindow.push(Integer.parseInt(tf.getText()));
                    visualStackWindow.setCamCentered(scrollPane);
                }
                if (e.getKeyChar() == 127) { // DELETE key
                    visualStackWindow.pop();
                    visualStackWindow.setCamCentered(scrollPane);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        return tf;
    }
}
