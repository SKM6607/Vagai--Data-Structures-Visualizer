package main.searching;

import main.interfaces.DefaultWindowsInterface;
import main.interfaces.NodeListener;
import main.interfaces.TreeInterface;

import javax.naming.CannotProceedException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.NoSuchElementException;

public abstract class SearchingWindow extends JPanel implements DefaultWindowsInterface, NodeListener {
    private static final String SELECTED_NODE = "Selected Node: ";
    private static final String UPDATE_NODE="Update Node";
    private static final String ADD_NODE = "Add Node";
    private static final String DELETE_NODE = "Delete Node";
    private static final String GOAL_STATE = "Set Goal State";
    private static final String START_SEARCHING = "Start Searching";
    private static final String NO_NODE_SELECTED = " NO NODE SELECTED ";
    private final String algorithmTitle;
    private final JLabel nodeSelectedLabel = new JLabel(NO_NODE_SELECTED);
    private final JTextField valueToAddTextField = new JTextField("");
    private final JButton addNodeButton = new JButton(ADD_NODE);
    private final JButton deleteNodeButton = new JButton(DELETE_NODE);
    private final JButton setGoalStateButton = new JButton(GOAL_STATE);
    private final JButton beginSearchButton = new JButton(START_SEARCHING);
    private final JButton updateNodeButton=new JButton(UPDATE_NODE);
    protected SearchingVisual searchingVisual;
    protected SearchingAlgorithm searchingAlgorithm;
    protected JPanel controlPanel;
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private TreeInterface.TreeNode selectedNode;

    protected SearchingWindow(SearchingVisual searchingVisual) {
        this.searchingVisual = searchingVisual;
        this.searchingAlgorithm = searchingVisual.algorithm;
        this.algorithmTitle = this.searchingAlgorithm.algorithmName;
        setLayout(new BorderLayout());
        this.controlPanel = setupControlPanel();
        this.searchingVisual.addNodeListener(this);
        this.setupActionListeners();
        searchingVisual.setPreferredSize(new Dimension(width, 5 * height / 6));
        add(searchingVisual, BorderLayout.CENTER);
        controlPanel.setPreferredSize(new Dimension(width, height / 6));
        add(controlPanel, BorderLayout.SOUTH);

    }

    private JPanel setupControlPanel() {
        JLabel selectedNode = new JLabel(SELECTED_NODE, SwingConstants.CENTER);
        // Main control panel
        JLabel title = new JLabel(algorithmTitle, SwingConstants.CENTER);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1, 10, 10));
        controlPanel.setBackground(backgroundColor);
        title.setFont(font.deriveFont(22.0f));
// --- Title at top ---
        title.setOpaque(true);
        title.setBackground(backgroundColor);
        title.setForeground(Color.WHITE);
        controlPanel.add(title);

// --- Second row panel: labels, text field, add button ---
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2,BoxLayout.X_AXIS));
        //new GridLayout(1, 5, 5, 5)
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
        valueToAddTextField.setSize(new Dimension(25,5));
        valueToAddTextField.setFont(font);
        valueToAddTextField.setHorizontalAlignment(SwingConstants.CENTER);
        valueToAddTextField.setBackground(backgroundColor);
        valueToAddTextField.setForeground(Color.WHITE);
        valueToAddTextField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateNodeButton.setFont(font);
        updateNodeButton.setHorizontalAlignment(SwingConstants.CENTER);
        updateNodeButton.setBackground(Color.LIGHT_GRAY);
        updateNodeButton.setEnabled(true);
        updateNodeButton.setForeground(Color.BLACK);
        row2.add(valueToAddTextField);
        addNodeButton.setFont(font);
        addNodeButton.setBackground(new Color(20, 82, 5));
        addNodeButton.setForeground(Color.WHITE);
        addNodeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row2.add(addNodeButton);
        row2.add(updateNodeButton);
        controlPanel.add(row2);
        System.out.println(controlPanel.getLayout());
        JPanel row3 = new JPanel(new GridLayout(1, 4, 5, 5)); // 1 row, 4 columns
        row3.setBackground(backgroundColor);
        deleteNodeButton.setFont(font);
        deleteNodeButton.setBackground(Color.RED);
        deleteNodeButton.setForeground(Color.WHITE);
        deleteNodeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row3.add(deleteNodeButton);
        setGoalStateButton.setFont(font);
        setGoalStateButton.setBackground(Color.YELLOW);
        setGoalStateButton.setForeground(Color.BLACK);
        setGoalStateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row3.add(setGoalStateButton);

// Leave one empty panel for spacing
        beginSearchButton.setFont(font);
        toggleFields(false);
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
        BevelBorder border = new BevelBorder(BevelBorder.RAISED) {
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

    @Override
    public void onSelected(TreeInterface.TreeNode node) {
        String nodeData = (node.data == 0) ? "ROOT" : String.valueOf(node.data);
        String nodeAddress = "Address: " + node.getAddress();
        nodeSelectedLabel.setText(String.format("[Value: %s, %s]", nodeData, nodeAddress));
        selectedNode = node;
        toggleFields(true);
    }

    private void toggleFields(boolean b) {
        valueToAddTextField.setEnabled(b);
        addNodeButton.setEnabled(b);
        deleteNodeButton.setEnabled(b);
        setGoalStateButton.setEnabled(b);
        beginSearchButton.setEnabled(b);
    }

    private void basicInputChecker() {
        try {
            int value = Integer.parseInt(valueToAddTextField.getText());
            if (value < -100 || value > 100) {
                JOptionPane.showMessageDialog(null, "Valid Range (-100,100) - {0}", "Invalid Range", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (value != 0) {
                searchingVisual.extendTreeAtSelectedNode(selectedNode, value);
            }
            valueToAddTextField.setText("");
            toggleFields(false);
            nodeSelectedLabel.setText(NO_NODE_SELECTED);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Only Numeric Input Allowed", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void basicDeletion() {
        try {
            searchingVisual.removeSelectedNode(selectedNode);
            toggleFields(false);
            nodeSelectedLabel.setText(NO_NODE_SELECTED);
        } catch (NoSuchElementException | CannotProceedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupActionListeners() {
        valueToAddTextField.addActionListener(_ -> basicInputChecker());
        addNodeButton.addActionListener(_ -> basicInputChecker());
        deleteNodeButton.addActionListener(_ -> basicDeletion());
        setGoalStateButton.addActionListener(a -> {
            searchingVisual.setGoalState(selectedNode);
            toggleFields(false);
            nodeSelectedLabel.setText(NO_NODE_SELECTED);
        });
        beginSearchButton.addActionListener(a -> {
            toggleFields(false);
            nodeSelectedLabel.setText(NO_NODE_SELECTED);
        });
    }


}
