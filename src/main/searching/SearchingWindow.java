package main.searching;
import javax.swing.*;
import java.awt.*;
public abstract class SearchingWindow extends JPanel {
    private static final String SELECTED_NODE = "Selected Node: ";
    private static final String ADD_NODE = "Add Node";
    private static final String DELETE_NODE = "Delete Node";
    private static final String GOAL_STATE = "Set Goal State";
    private static final String START_SEARCHING = "Start Searching";
    private final String algorithmTitle;
    private final JLabel nodeSelectedLabel = new JLabel("");
    private final JTextField valueToAddTextField = new JTextField("");
    private final JButton addNodeButton = new JButton(ADD_NODE);
    private final JButton deleteNodeButton = new JButton(DELETE_NODE);
    private final JButton setGoalStateButton = new JButton(GOAL_STATE);
    private final JButton beginSearchButton = new JButton(START_SEARCHING);
    protected SearchingVisual searchingVisual;
    protected SearchingAlgorithm searchingAlgorithm;
    protected JPanel controlPanel;

    protected SearchingWindow(SearchingVisual searchingVisual) {
        this.searchingVisual = searchingVisual;
        this.searchingAlgorithm = searchingVisual.algorithm;
        this.algorithmTitle = this.searchingAlgorithm.algorithmName;
        this.controlPanel = setupControlPanel();
        this.setupActionListeners();
        add(searchingVisual, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void setConstraints(GridBagConstraints gB, int... c) {
        gB.gridx = c[0];
        gB.gridy = c[1];
        if (c.length == 3)
            gB.gridwidth = c[2];
    }

    private JPanel setupControlPanel() {
        if (this.controlPanel != null) return null;
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JLabel titleOfAlgorithm = new JLabel(algorithmTitle);
        setConstraints(gridBagConstraints, 0, 0, 3);
        controlPanel.add(titleOfAlgorithm, gridBagConstraints);
        JLabel selectedNode = new JLabel(SELECTED_NODE);
        setConstraints(gridBagConstraints, 1, 0, 1);
        controlPanel.add(selectedNode, gridBagConstraints);
        setConstraints(gridBagConstraints, 1, 1, 1);
        controlPanel.add(nodeSelectedLabel, gridBagConstraints);
        setConstraints(gridBagConstraints, 1, 2, 1);
        controlPanel.add(valueToAddTextField, gridBagConstraints);
        setConstraints(gridBagConstraints, 1, 3, 1);
        controlPanel.add(addNodeButton, gridBagConstraints);
        setConstraints(gridBagConstraints, 2, 0, 1);
        controlPanel.add(deleteNodeButton, gridBagConstraints);
        setConstraints(gridBagConstraints, 2, 1, 1);
        controlPanel.add(setGoalStateButton, gridBagConstraints);
        setConstraints(gridBagConstraints, 2, 2, 1);
        controlPanel.add(beginSearchButton, gridBagConstraints);
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
