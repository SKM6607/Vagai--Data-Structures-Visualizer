package Windows
//TODO SCROLLING MECHANISMS
import Windows.Interfaces.DefaultWindowsInterface
import Windows.Interfaces.GridInterface
import Windows.Interfaces.GridInterface.SPACING
import Windows.Interfaces.LinkedListInterface.VisualNode
import Windows.Interfaces.StackLightWeightInterface
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*

open class StackWindow : JPanel(), StackLightWeightInterface, GridInterface {
    val myWidth = StackLightWeightInterface.width;
    val myHeight = StackLightWeightInterface.height;
    private var top: VisualNode
    private var size = 1
    val nodeHeight = 80
    var nodeWidth = 100
    val startX = returnClosest(myWidth / 5, myWidth / 3, SPACING + 5)
    val endX = returnClosest(myWidth - 2 * nodeWidth, myWidth - nodeWidth, SPACING + 5)
    val startY = nodeHeight * 2
    val endY = returnClosest(myHeight - nodeHeight, myHeight, SPACING + 5)

    init {
        nodeWidth = endX - startX - 60
        top = VisualNode(0, startX + 30, (myHeight - 2 * nodeHeight))
        background = Color(0xA0F29)
        preferredSize = Dimension(myWidth, myHeight)
        push(3)
        push(5)
        pop()
    }

    protected override fun paintComponent(g1: Graphics) {
        super.paintComponent(g1);
        val g: Graphics2D = g1 as Graphics2D
        drawGrid(g)
        drawBasket(g)
        var temp: VisualNode = top
        while (temp != null) {
            drawNode(g, temp)
            temp = temp.nextNode
        }
    }

    override fun push(value: Int) {
        val newNode = VisualNode(value, top.xPos, top.yPos - nodeHeight - 10)
        newNode.nextNode = top;
        newNode.nextAddress = top.address
        top = newNode
        size++
        repaint()
    }

    override fun pop(): Int {
        if (sizeSt() == 1) {
            return top.data
        }
        val retData = top.data
        top.nextAddress = null
        val temp = top.nextNode
        top.nextNode = null
        //TODO: POSITION CHANGE
        top = temp
        size--
        repaint()
        return retData
    }

    override fun peek(): Int {
        return top.data
    }

    override fun sizeSt(): Int {
        return size;
    }

    override fun drawGrid(g: Graphics2D) {
        g.color = Color(0x1C233D)
        for (i in 0..myWidth step SPACING + 5) {
            g.drawLine(i, 0, i, myHeight)
        }
        for (i in 0..myHeight step SPACING + 5) {
            g.drawLine(0, i, myWidth, i)
        }
    }

    final override fun drawNode(g: Graphics2D, node: VisualNode) {
        val oldColor = g.color
        val resetStroke = g.stroke
        g.also {
            it.color = Color(0x1E3A8A)
            it.fillRect(node.xPos, node.yPos, nodeWidth, nodeHeight)
            it.color = Color(0xFFD700)
            it.font = Font(Font.SANS_SERIF, Font.BOLD, 30)
            it.stroke = BasicStroke(6f)
            it.drawString("Data: ${node.data}", node.xPos + nodeWidth / 6, node.yPos + nodeHeight / 2 + 10)
            it.drawLine(node.xPos + nodeWidth / 2, node.yPos + 5, node.xPos + nodeWidth / 2, node.yPos + nodeHeight - 5)
            it.drawString(
                "Next Address: ${
                    when (node.nextAddress) {
                        null -> "NULL"
                        else -> {
                            node.nextAddress
                        }
                    }
                }",
                node.xPos + 9 * nodeWidth / 15,
                node.yPos + nodeHeight / 2 + 10
            )
            it.color = Color.WHITE
            it.font = it.font.deriveFont(10)
            it.drawString("${node.address}  ----->", 50, node.yPos + nodeHeight / 2 + 10)
            it.color = oldColor
            it.stroke = resetStroke
        }
    }

    private fun returnClosest(startPoint: Int, endPoint: Int, divisor: Int): Int {
        for (i in startPoint..endPoint) {
            if (i % divisor == 0) {
                return i
            }
        }
        return startPoint
    }

    final override fun drawBasket(g: Graphics2D) {
        val stroke = g.stroke
        val color = g.color
        g.stroke = BasicStroke(15f)
        g.color = DefaultWindowsInterface.themeColorBG
        g.drawLine(startX, startY, startX, endY)
        g.drawLine(startX, endY, endX, endY)
        g.drawLine(endX, endY, endX, startY)
        g.stroke = stroke
        g.color = color
    }
}

class StackWindowUsable : JPanel(), DefaultWindowsInterface {
    private val myHeight= DefaultWindowsInterface.height;
    private val myWidth= DefaultWindowsInterface.width
    private lateinit var textField: JTextField
    private lateinit var pushButton: JButton
    private lateinit var popButton: JButton
    private val font=Font(Font.SANS_SERIF, Font.BOLD, 20)
    private val visualStackWindow = StackWindow()
    init {
        layout= BorderLayout()
        val layeredPane = JLayeredPane().apply {
            preferredSize= Dimension(myWidth,myHeight)
        }
        val mainPanel = JPanel(GridLayout(2,1,5,5));
        val scrollPane = JScrollPane(
            visualStackWindow,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        {
            textField = textInput()
            pushButton = JButton("PUSH").also {
                it.background= DefaultWindowsInterface.themeColorBG
                it.foreground= Color.WHITE
                it.cursor= Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                it.font=font
                it.addActionListener {
                    if (textField.inputVerifier.verify(textField)) {
                        visualStackWindow.push(textField.text.toInt())
                        textField.text=""
                    }
                }
            }
            popButton = JButton("POP").also {
                it.cursor= Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                it.font=font
                it.foreground= Color.WHITE
                it.background=DefaultWindowsInterface.themeColorBG
                it.addActionListener {
                    visualStackWindow.pop();
                }
            }
        }
        val subPanel= JPanel(GridLayout(1,2,5,5));
        {
            layeredPane.preferredSize = Dimension(myWidth, myHeight)
            scrollPane.setBounds(0, 0, myWidth, myHeight)
            subPanel.add(textField)
            subPanel.add(pushButton)
            mainPanel.background= Color(0,0,0,180)
            mainPanel.add(subPanel)
            mainPanel.add(popButton)
            mainPanel.setBounds(myWidth / 2 - 170, myHeight - 200, 300, 100)
            layeredPane.add(visualStackWindow, JLayeredPane.DEFAULT_LAYER)
            layeredPane.add(mainPanel, JLayeredPane.PALETTE_LAYER)
            mainPanel.setBounds(myWidth / 2 - 170, myHeight -200, 300, 100)
        }
        add(layeredPane, BorderLayout.CENTER)
    }

    private fun textInput(): JTextField {
        return JTextField().also {
            it.cursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)
            it.font =font
            it.background=DefaultWindowsInterface.themeColorBG
            it.foreground= Color.WHITE
            it.horizontalAlignment = JTextField.CENTER
            it.toolTipText = "Value of the next block (Int): "
            it.inputVerifier = object : InputVerifier() {
                override fun verify(input: JComponent?): Boolean {
                    return try {
                        (input as? JTextField)?.text?.toInt()
                        true
                    } catch (e: NumberFormatException) {
                        false
                    }
                }
            }
            it.addKeyListener(object: KeyListener{
                override fun keyTyped(e: KeyEvent?) {
                    if(it.inputVerifier.verify(it) && e?.keyChar=='\n'){
                        visualStackWindow.push(it.text.toInt())
                    }
                    if(e?.keyChar== 127.toChar()){
                        visualStackWindow.pop()
                    }
                }

                //Discarded
                override fun keyPressed(e: KeyEvent?) {
                }
                override fun keyReleased(e: KeyEvent?) {
                }

            })
        }
    }
}

fun main() {
    SwingUtilities.invokeLater {
        val jFrame = JFrame("Stack Window")
        jFrame.also {
            it.add(StackWindow())
            it.size = Dimension(DefaultWindowsInterface.width, DefaultWindowsInterface.height)
            it.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            it.isVisible = true
        }
    }
}