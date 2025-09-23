package Windows.dialogs

import Windows.interfaces.DefaultWindowsInterface
import org.jetbrains.annotations.NotNull
import java.awt.Color
import java.awt.FlowLayout
import java.awt.Font
import java.awt.GridLayout
import java.awt.Window
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JPanel

class LegendDialog(parent: Window?, title: String?, legend:Map<String, Color>): JDialog(parent,title){
    init {
        this.title=title
        contentPane.background= Color.WHITE
        layout= GridLayout(legend.size, 1, 2, 2)
        defaultCloseOperation=DISPOSE_ON_CLOSE
        for(entry in legend.entries){
            add(createDialogBox(entry.key,entry.value))
        }
        pack()
        isResizable=false
        isAlwaysOnTop=true
        setLocationRelativeTo(parent)
    }
    private fun createDialogBox(string: String,color: Color): @NotNull JPanel {
        val jPanel= JPanel(FlowLayout(FlowLayout.LEFT))
        val jLabel= JLabel(string)
        jLabel.background= Color.BLACK
        jLabel.font= Font(Font.SANS_SERIF, Font.BOLD, 20)
        jLabel.foreground= color
        jPanel.background= Color.BLACK
        jPanel.add(jLabel)
        return jPanel
    }
}