package main.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class LegendDialog extends JDialog {

    public LegendDialog(Window parent, String title, Map<String, Color> legend) {
        super(parent, title);
        this.setTitle(title);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridLayout(legend.size(), 1, 2, 2));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Note: JDialog doesn't have EXIT_ON_CLOSE usually, but DISPOSE is
                                                    // better. KT used DISPOSE.

        for (Map.Entry<String, Color> entry : legend.entrySet()) {
            add(createDialogBox(entry.getKey(), entry.getValue()));
        }
        pack();
        setResizable(false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(parent);
    }

    private JPanel createDialogBox(String string, Color color) {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jLabel = new JLabel(string);
        // jLabel.setBackground(Color.BLACK); // JLabel background only works if opaque
        // jLabel.setOpaque(true);
        // usage in KT: jLabel.background= Color.BLACK

        jLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        jLabel.setForeground(color);

        jPanel.setBackground(Color.BLACK);
        jPanel.add(jLabel);
        return jPanel;
    }
}
