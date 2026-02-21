package main.searching;

import main.interfaces.DefaultWindowsInterface;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

public abstract class SearchingWindow extends JPanel implements DefaultWindowsInterface {
    private static final String SELECTED_NODE = "Selected Node: ";
    private static final String ADD_NODE = "Add Node";
    private static final String DELETE_NODE = "Delete Node";
    private static final String GOAL_STATE = "Set Goal State";
    private static final String START_SEARCHING = "Start Searching";
    private final String algorithmTitle;
    private final JLabel nodeSelectedLabel = new JLabel("<--NO-NODE-SELECTED-->");
    private final JTextField valueToAddTextField = new JTextField("");
    private final JButton addNodeButton = new JButton(ADD_NODE);
    private final JButton deleteNodeButton = new JButton(DELETE_NODE);
    private final JButton setGoalStateButton = new JButton(GOAL_STATE);
    private final JButton beginSearchButton = new JButton(START_SEARCHING);
    protected SearchingVisual searchingVisual;
    protected SearchingAlgorithm searchingAlgorithm;
    protected JPanel controlPanel;
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    protected SearchingWindow(SearchingVisual searchingVisual) {
        this.searchingVisual = searchingVisual;
        this.searchingAlgorithm = searchingVisual.algorithm;
        this.algorithmTitle = this.searchingAlgorithm.algorithmName;
        setLayout(new BorderLayout());
        this.controlPanel = setupControlPanel();
        this.setupActionListeners();
        searchingVisual.setPreferredSize(new Dimension(width,5*height/6));
        add(searchingVisual,BorderLayout.CENTER);
        controlPanel.setPreferredSize(new Dimension(width,height/6));
        add(controlPanel,BorderLayout.SOUTH);
    }
    private JPanel setupControlPanel() {

        JLabel selectedNode = new JLabel(SELECTED_NODE,SwingConstants.CENTER);
        // Main control panel
        JLabel title = new JLabel(algorithmTitle, SwingConstants.CENTER);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3,1,10,10));
        controlPanel.setBackground(backgroundColor);
        title.setFont(font.deriveFont(22.0f));
// --- Title at top ---
        title.setOpaque(true);
        title.setBackground(backgroundColor);
        title.setForeground(Color.WHITE);
        controlPanel.add(title);

// --- Second row panel: labels, text field, add button ---
        JPanel row2 = new JPanel(new GridLayout(1, 4, 5, 5)); // 1 row, 4 columns, 5px gap
        row2.setBackground(backgroundColor);
        selectedNode.setFont(font);
        selectedNode.setOpaque(true);
        selectedNode.setBackground(backgroundColor);
        selectedNode.setForeground(Color.WHITE);
        row2.add(selectedNode);
        nodeSelectedLabel.setFont(font);
        nodeSelectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nodeSelectedLabel.setOpaque(true);
        nodeSelectedLabel.setBackground(backgroundColor);
        nodeSelectedLabel.setForeground(Color.WHITE);
        row2.add(nodeSelectedLabel);
        valueToAddTextField.setFont(font);
        valueToAddTextField.setHorizontalAlignment(SwingConstants.CENTER);
        valueToAddTextField.setBackground(backgroundColor);
        valueToAddTextField.setForeground(Color.WHITE);
        valueToAddTextField.setEnabled(false);
        valueToAddTextField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row2.add(valueToAddTextField);
        addNodeButton.setFont(font);
        addNodeButton.setBackground(new Color(20, 82, 5));
        addNodeButton.setForeground(Color.WHITE);
        addNodeButton.setEnabled(false);
        addNodeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row2.add(addNodeButton);
        controlPanel.add(row2);
        JPanel row3 = new JPanel(new GridLayout(1, 4, 5, 5)); // 1 row, 4 columns
        row3.setBackground(backgroundColor);
        deleteNodeButton.setFont(font);
        deleteNodeButton.setEnabled(false);
        deleteNodeButton.setBackground(Color.RED);
        deleteNodeButton.setForeground(Color.WHITE);
        deleteNodeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row3.add(deleteNodeButton);
        setGoalStateButton.setFont(font);
        setGoalStateButton.setEnabled(false);
        setGoalStateButton.setBackground(Color.YELLOW);
        setGoalStateButton.setForeground(Color.BLACK);
        setGoalStateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row3.add(setGoalStateButton);

// Leave one empty panel for spacing
        beginSearchButton.setFont(font);
        beginSearchButton.setEnabled(false);
// Begin search button spans last 2 columns
        beginSearchButton.setBackground(Color.BLACK);
        beginSearchButton.setForeground(Color.WHITE);
        beginSearchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
// Use a panel to span 2 columns
        JPanel beginSearchWrapper = new JPanel(new BorderLayout());
        beginSearchWrapper.add(beginSearchButton, BorderLayout.CENTER);
        beginSearchWrapper.setBackground(backgroundColor);
        row3.add(beginSearchWrapper);
        controlPanel.add(row3);
        BevelBorder border=new BevelBorder(BevelBorder.RAISED){
            @Override
            public Color getHighlightInnerColor() {
                return Color.WHITE;
            }

            @Override
            public Color getHighlightOuterColor() {
                return Color.WHITE;
            }

            @Override
            public Color getShadowInnerColor() {
                return Color.WHITE;
            }

            @Override
            public Color getShadowOuterColor() {
                return Color.WHITE;
            }
        };
        controlPanel.setBorder(border);
       return controlPanel;
    }

    private void setupActionListeners() {
        valueToAddTextField.addActionListener(e -> {
        });
        addNodeButton.addActionListener(a -> {
        });
        deleteNodeButton.addActionListener(a -> {
        });
        setGoalStateButton.addActionListener(a -> {
        });
        beginSearchButton.addActionListener(a -> {
        });
    }


}
