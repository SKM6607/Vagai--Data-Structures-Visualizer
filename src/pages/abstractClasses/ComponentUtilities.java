package pages.abstractClasses;

import javax.swing.*;
import java.awt.*;

public final class ComponentUtilities {
    private ComponentUtilities(){}
    public static JTextField createTextField(Font font,Color color) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBackground(color);
        field.setForeground(Color.WHITE);
        field.setToolTipText("Enter integer value");
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
    public static JButton createButton(String text, Color bgColor,Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        return button;
    }
    public static JTextField createTextField(Font font,Color color,String toolTip){
        JTextField field = createTextField(font,color);
        field.setToolTipText(toolTip);
        return field;
    }
}
