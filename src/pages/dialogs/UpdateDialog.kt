package pages.dialogs

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import java.awt.Window
import javax.swing.JDialog
import javax.swing.JLabel
class UpdateDialog(parent: Window?) : JDialog(parent){
    init {
        val font = Font(Font.SANS_SERIF, Font.BOLD, 25)
        val textLabels = arrayOfNulls<JLabel>(5)
        textLabels[0] = JLabel("What is Left?")
        textLabels[1] = JLabel("Cool animations and mechanisms")
        textLabels[2] = JLabel("Introduction of more algorithms")
        textLabels[3] = JLabel("Fixing and updating scrolling issues")
        textLabels[4] = JLabel("and more...")

        layout = GridLayout(5, 1, 0, 12)
        title = "Update"
        isResizable = false
        size = Dimension(520, 420)

        // Dark navy background
        background = Color(0x0F172A)

        val headerColor = Color(0x38BDF8)   // bright cyan (for title)
        val textColor = Color(0xE2E8F0)     // soft white-gray
        val accentColor = Color(0x14B8A6)   // teal accent

        var i = 0
        for (textLabel in textLabels) {
            textLabel?.font = font
            textLabel?.isOpaque = true
            textLabel?.background = background
            textLabel?.foreground = when (i) {
                0 -> headerColor      // first line cyan
                1, 4 -> accentColor   // accent lines
                else -> textColor     // neutral body lines
            }
            textLabel?.horizontalAlignment = if (i == 0) JLabel.CENTER else JLabel.LEFT
            i++
            add(textLabel)
        }
        setLocationRelativeTo(parent)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        isVisible = true
    }

}
