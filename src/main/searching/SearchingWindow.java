package main.searching;

import main.interfaces.DefaultWindowsInterface;
import main.interfaces.NodeListener;
import main.interfaces.TreeInterface;

import javax.imageio.ImageIO;
import javax.naming.CannotProceedException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

public abstract class SearchingWindow extends JPanel implements DefaultWindowsInterface, NodeListener {
    private static final String SELECTED_NODE = "Selected Node: ";
    private static final String UPDATE_NODE = "Update Node";
    private static final String ADD_NODE = "Add Node";
    private static final String DELETE_NODE = "Delete Node";
    private static final String GOAL_STATE = "Set Goal State";
    private static final String START_SEARCHING = "Start Searching";
    private static final String RANGE_VALUES = "Set Range";
    private static final String RANDOMIZE_TREE = "Randomize Tree";
    private static final String NO_NODE_SELECTED = " NO NODE SELECTED ";
    private static final String BINARY_TREE = "Binary Tree";
    private static final String BINARY_SEARCH_TREE = "Binary Search Tree";
    protected final JLabel binaryTreeLabel = new JLabel(BINARY_TREE);
    protected final JLabel binarySearchTreeLabel = new JLabel(BINARY_SEARCH_TREE);
    private final String algorithmTitle;
    private final JLabel nodeSelectedLabel = new JLabel(NO_NODE_SELECTED);
    private final JTextField valueToAddTextField = new JTextField("");
    private final JTextField setRangeTextField = new JTextField("");
    private final JButton addNodeButton = new JButton(ADD_NODE);
    private final JButton deleteNodeButton = new JButton(DELETE_NODE);
    private final JButton setGoalStateButton = new JButton(GOAL_STATE);
    private final JButton beginSearchButton = new JButton(START_SEARCHING);
    private final JButton updateNodeButton = new JButton(UPDATE_NODE);
    private final JButton randomizeTreeButton = new JButton(RANDOMIZE_TREE);
    private final JButton setRangeButton = new JButton(RANGE_VALUES);
    private final JLabel depthTextLabel = new JLabel();
    private final JLabel totalNodesTextLabel = new JLabel();
    private final ImageIcon iconImageForToggleButtonDefault = new ImageIcon(readImageIntoArray("src/resources/pictures/ArrowLeft.png"));
    private final ImageIcon iconImageForToggleButtonPressed = new ImageIcon(readImageIntoArray("src/resources/pictures/ArrowRight.png"));
    protected SearchingVisual searchingVisual;
    protected SearchingAlgorithm searchingAlgorithm;
    protected JPanel controlPanel;
    protected JPanel floatingPanel;
    protected JToggleButton swapTheTypeOfTreeToggleButton = new JToggleButton(iconImageForToggleButtonDefault);
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private TreeInterface.TreeNode target;
    private TreeInterface.TreeNode selectedNode;
    private boolean onCancellationMode;

    protected SearchingWindow(SearchingVisual searchingVisual) {
        this.searchingVisual = searchingVisual;
        this.searchingAlgorithm = searchingVisual.algorithm;
        this.algorithmTitle = this.searchingAlgorithm.algorithmName;
        setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        this.controlPanel = setupControlPanel();
        this.floatingPanel = setupFloatingPanel();
        int visualWidth = width;
        int visualHeight = 5 * height / 6;
        searchingVisual.setBounds(0, 0, visualWidth, visualHeight);
        layeredPane.add(searchingVisual, JLayeredPane.DEFAULT_LAYER);
        int floatW = 600, floatH = 150;
        floatingPanel.setBounds(width / 2 - 3 * floatW / 2, 10, floatW, floatH);
        layeredPane.add(floatingPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.setPreferredSize(new Dimension(visualWidth, visualHeight));
        this.searchingVisual.addNodeListener(this);
        this.setupActionListeners();
        add(layeredPane, BorderLayout.CENTER);
        controlPanel.setPreferredSize(new Dimension(width, height / 6));
        add(controlPanel, BorderLayout.SOUTH);
    }

    private byte[] readImageIntoArray(final String filePath) {
        try {
            BufferedImage reader = ImageIO.read(new File(filePath));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(reader, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    private JPanel setupTreeOrder() {
        JPanel treeTogglePanel = new JPanel(new GridLayout(1, 3, 5, 5));
        treeTogglePanel.add(binaryTreeLabel);
        binaryTreeLabel.setFont(menuFont.deriveFont(18.0f));
        binaryTreeLabel.setBackground(backgroundColor);
        binaryTreeLabel.setForeground(Color.WHITE);
        binaryTreeLabel.setOpaque(true);
        binaryTreeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        treeTogglePanel.add(swapTheTypeOfTreeToggleButton);
        treeTogglePanel.add(binarySearchTreeLabel);
        binarySearchTreeLabel.setFont(menuFont.deriveFont(18.0f));
        binarySearchTreeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        binarySearchTreeLabel.setForeground(Color.WHITE);
        binarySearchTreeLabel.setBackground(backgroundColor);
        binarySearchTreeLabel.setOpaque(true);
        swapTheTypeOfTreeToggleButton.setOpaque(true);
        swapTheTypeOfTreeToggleButton.setBackground(Color.WHITE);
        swapTheTypeOfTreeToggleButton.setSelectedIcon(iconImageForToggleButtonPressed);
        return treeTogglePanel;
    }

    //TODO Itha Implement pannitu namma algo's ah ezhutalam as per our wish, itha oru legend mathirunu vechipome?
    protected JPanel setupDetailsPanel() {
        JPanel detailsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel depthLabel = new JLabel("Tree Depth: ");
        JLabel totalNumberOfNodes = new JLabel("Total Number of Nodes");
        detailsPanel.add(depthLabel);
        detailsPanel.add(depthTextLabel);
        detailsPanel.add(totalNumberOfNodes);
        detailsPanel.add(totalNodesTextLabel);
        depthLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        return detailsPanel;
    }

    protected abstract JPanel setupLegendPanel();

    private JPanel setupFloatingPanel() {
        JPanel floatingPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        floatingPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        JLabel randomizer = new JLabel("Randomizer Panel");
        randomizer.setFont(menuFont.deriveFont(18.0f));
        randomizer.setHorizontalAlignment(SwingConstants.CENTER);
        randomizer.setOpaque(true);
        randomizer.setBackground(backgroundColor);
        randomizer.setForeground(Color.WHITE);
        floatingPanel.add(randomizer);
        JPanel innerPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        innerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        innerPanel.setOpaque(true);
        innerPanel.setBackground(backgroundColor);
        innerPanel.add(setRangeTextField);
        setRangeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        setRangeTextField.setBackground(backgroundColor);
        setRangeTextField.setFont(menuFont.deriveFont(18.0f));
        setRangeTextField.setForeground(Color.WHITE);
        setRangeTextField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setRangeTextField.setOpaque(true);
        innerPanel.add(setRangeButton);
        setRangeButton.setFont(menuFont.deriveFont(18.0f));
        setRangeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setRangeButton.setOpaque(true);
        setRangeButton.setBackground(Color.BLACK);
        setRangeButton.setForeground(Color.WHITE);
        floatingPanel.add(innerPanel);
        floatingPanel.add(randomizeTreeButton);
        randomizeTreeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        randomizeTreeButton.setFont(menuFont.deriveFont(18.0f));
        randomizeTreeButton.setOpaque(true);
        randomizeTreeButton.setBackground(Color.RED);
        randomizeTreeButton.setForeground(Color.WHITE);
        randomizeTreeButton.setEnabled(false);
        floatingPanel.add(setupTreeOrder());
        return floatingPanel;
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
        row2.setLayout(new GridBagLayout());
        row2.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(2, 4, 2, 4); // padding between components

// --- Style your components (unchanged) ---
        selectedNode.setFont(font);
        selectedNode.setOpaque(true);
        selectedNode.setBackground(backgroundColor);
        selectedNode.setForeground(Color.WHITE);

        nodeSelectedLabel.setFont(font);
        nodeSelectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nodeSelectedLabel.setOpaque(true);
        nodeSelectedLabel.setBackground(backgroundColor);
        nodeSelectedLabel.setForeground(Color.WHITE);

        valueToAddTextField.setFont(font);
        valueToAddTextField.setHorizontalAlignment(SwingConstants.CENTER);
        valueToAddTextField.setBackground(backgroundColor);
        valueToAddTextField.setForeground(Color.WHITE);
        valueToAddTextField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addNodeButton.setFont(font);
        addNodeButton.setBackground(new Color(20, 82, 5));
        addNodeButton.setForeground(Color.WHITE);
        addNodeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        updateNodeButton.setFont(font);
        updateNodeButton.setHorizontalAlignment(SwingConstants.CENTER);
        updateNodeButton.setBackground(Color.LIGHT_GRAY);
        updateNodeButton.setEnabled(true);
        updateNodeButton.setForeground(Color.BLACK);

// --- Add with weights ---
// selectedNode - medium space
        gbc.gridx = 0;
        gbc.weightx = 2.0;
        row2.add(selectedNode, gbc);

// nodeSelectedLabel - medium space
        gbc.gridx = 1;
        gbc.weightx = 2.0;
        row2.add(nodeSelectedLabel, gbc);

// valueToAddTextField - LESS space
        gbc.gridx = 2;
        gbc.weightx = 0.8;
        row2.add(valueToAddTextField, gbc);

// addNodeButton - more space
        gbc.gridx = 3;
        gbc.weightx = 1.5;
        row2.add(addNodeButton, gbc);

// updateNodeButton - more space
        gbc.gridx = 4;
        gbc.weightx = 1.5;
        row2.add(updateNodeButton, gbc);

        controlPanel.add(row2);
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

    private void resetSelected(){
        nodeSelectedLabel.setText(NO_NODE_SELECTED);
        valueToAddTextField.setText("");
        toggleFields(false);
    }
    @Override
    public void onSelected(TreeInterface.TreeNode node) {
        if(node==null){
            resetSelected();
            return;
        }
        String nodeData = (node.data == 0) ? "ROOT" : String.valueOf(node.data);
        String nodeAddress = "Address: " + node.getAddress();
        nodeSelectedLabel.setText(String.format("[Value: %s, %s]", nodeData, nodeAddress));
        selectedNode = node;
            if (target != null && selectedNode == target) {
                onCancellationMode = true;
                changeToCancellationMode();
            }
        toggleFields(true);
    }

    private void changeToCancellationMode() {
        setGoalStateButton.setBackground(new Color(126, 8, 8));
        setGoalStateButton.setForeground(Color.WHITE);
        setGoalStateButton.setText("Cancel Selected Goal State Node");
    }

    private void changeToNormalMode() {
        setGoalStateButton.setBackground(Color.YELLOW);
        setGoalStateButton.setForeground(Color.BLACK);
        setGoalStateButton.setText(GOAL_STATE);
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
            if (value < -999 || value > 999) {
                JOptionPane.showMessageDialog(this, "Valid Range (-100,100) - {0}", "Invalid Range", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (value != 0) {
                searchingVisual.extendTreeAtSelectedNode(selectedNode, value);
            }
            resetSelected();
            searchingVisual.resetSelectedNode();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Only Numeric Input Allowed", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void basicDeletion() {
        try {
            searchingVisual.removeSelectedNode(selectedNode);
            toggleFields(false);
            nodeSelectedLabel.setText(NO_NODE_SELECTED);
            searchingVisual.resetSelectedNode();
        } catch (NoSuchElementException | CannotProceedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void basicRandomisation() {
        try {
            int range = Integer.parseInt(setRangeTextField.getText());
            if (range > 999 || range < -999 || range == 0) {
                throw new CannotProceedException("Cannot Randomize a tree. Ranges should be within (-999,999) -{0}");
            }
            searchingVisual.randomizeTree(range);
            setRangeTextField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Input Provided For Randomization");
        } catch (CannotProceedException ec) {
            JOptionPane.showMessageDialog(this, ec.getMessage());
        }
    }

    private void toggleRandomizerFields(boolean b) {
        setRangeTextField.setEnabled(b);
        setRangeButton.setEnabled(b);
    }

    private void setupActionListeners() {
        valueToAddTextField.addActionListener(_ -> basicInputChecker());
        addNodeButton.addActionListener(_ -> basicInputChecker());
        deleteNodeButton.addActionListener(_ -> basicDeletion());
        setGoalStateButton.addActionListener(_ -> {
            if (onCancellationMode) {
                onCancellationMode = false;
                target = null;
                changeToNormalMode();
                return;
            }
            searchingVisual.setGoalState(selectedNode);
            target = selectedNode;
            toggleFields(false);
            nodeSelectedLabel.setText(NO_NODE_SELECTED);
        });
        beginSearchButton.addActionListener(_ -> {
            try {
                searchingVisual.searchForNode();
            } catch (NoSuchElementException | InterruptedException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            toggleFields(false);
            nodeSelectedLabel.setText(NO_NODE_SELECTED);
        });
        setRangeButton.addActionListener(_ -> {
            randomizeTreeButton.setEnabled(true);
            toggleRandomizerFields(false);
        });
        randomizeTreeButton.addActionListener(_ -> {
            if (JOptionPane.showConfirmDialog(this, "Are you sure? All your tree data will be erased.") == JOptionPane.YES_OPTION)
                basicRandomisation();
            randomizeTreeButton.setEnabled(false);
            toggleRandomizerFields(true);
        });
        updateNodeButton.addActionListener(_ -> {
            try {
                int value = Integer.parseInt(valueToAddTextField.getText());
                if (value < -999 || value > 999 || value == 0) {
                    JOptionPane.showMessageDialog(null, "Valid Range (-999,999) - {0}", "Invalid Range", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                searchingVisual.updateNode(selectedNode, value);
                valueToAddTextField.setText("");
                toggleFields(false);
                nodeSelectedLabel.setText(NO_NODE_SELECTED);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Only Numeric Input Allowed", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            } catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        swapTheTypeOfTreeToggleButton.addActionListener(_->searchingVisual.toggleTreeType());
    }


}
