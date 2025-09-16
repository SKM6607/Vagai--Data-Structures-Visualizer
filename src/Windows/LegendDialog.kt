package Windows
import Windows.Interfaces.DefaultWindowsInterface
import org.jetbrains.annotations.NotNull
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import java.awt.GridLayout
import java.awt.Window
import javax.swing.JDialog
import javax.swing.JPanel
import java.util.*;
import javax.swing.BoxLayout
import javax.swing.JLabel

/*
package Windows;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class LegendDialog extends JDialog {
    public LegendDialog(Window parent, String title, Map<String, Color> legend){
        super(parent,title);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridLayout(legend.size(), 1,2,2));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        for (Map.Entry<String,Color> entry:legend.entrySet()){
            add(createDialogBox(entry.getKey(),entry.getValue()));
        }
        pack();
        setLocation(1800,600);
        setResizable(false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(parent);
    }
    private @NotNull JPanel  createDialogBox(String text, Color color){
        JPanel field=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel colorText=new JLabel(text);
        colorText.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        colorText.setBackground(Color.BLACK);
        colorText.setForeground(color);
        field.setBackground(Color.BLACK);
        field.add(colorText);
        return field;
    }
}

* */
class LegendDialog(parent:Window?,title: String?,legend:Map<String,Color>): JDialog(parent,title){
    init {
        this.title=title
        contentPane.background= Color.WHITE
        layout= GridLayout(legend.size,1,2,2)
        defaultCloseOperation=DISPOSE_ON_CLOSE
        for(entry in legend.entries){
            add(createDialogBox(entry.key,entry.value))
        }
        pack()
        location.x= DefaultWindowsInterface.width/2
        location.y= DefaultWindowsInterface.height/2
        isResizable=false
        isAlwaysOnTop=true
        setLocationRelativeTo(parent)
    }
    private fun createDialogBox(string: String,color: Color): @NotNull JPanel{
        val jPanel= JPanel(FlowLayout(FlowLayout.LEFT))
        val jLabel= JLabel(string)
        jLabel.background= Color.BLACK
        jLabel.font= Font(Font.SANS_SERIF,Font.BOLD,20)
        jLabel.foreground= color
        jPanel.background= Color.BLACK
        jPanel.add(jLabel)
        return jPanel
    }
}