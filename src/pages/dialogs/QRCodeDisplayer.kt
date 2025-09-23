package Windows.dialogs
import Windows.interfaces.DefaultWindowsInterface
import java.awt.Dimension
import java.awt.Image
import java.awt.Window
import javax.swing.ImageIcon
import javax.swing.JDialog
import javax.swing.JLabel
class QRCodeDisplayer(parent: Window?) : JDialog(parent){
    private val qrLabel: JLabel
    init{
        title="Scan to Visit!"
        val qrImg= ImageIcon("ReadmeImages/GitHubVisai.png")
        val scaledImage=qrImg.image.getScaledInstance(256,256,Image.SCALE_SMOOTH)
        qrLabel= JLabel(ImageIcon(scaledImage))
        isResizable=false
        qrLabel.preferredSize= Dimension(256,256)
        size= Dimension(300,300)
        qrLabel.background= DefaultWindowsInterface.themeColorBG
        this.add(qrLabel)
        setLocationRelativeTo(parent)
    }
}
