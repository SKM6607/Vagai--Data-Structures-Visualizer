package Windows
//TODO SCROLLING MECHANISMS
import MyShapes.MyArrow
import Windows.interfaces.DefaultWindowsInterface
import Windows.interfaces.GridInterface
import Windows.interfaces.GridInterface.SPACING
import Windows.interfaces.LinkedListInterface.VisualNode
import Windows.interfaces.StackLightWeightInterface
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*

class StackWindow : JPanel(), StackLightWeightInterface, GridInterface {
    private val myWidth = StackLightWeightInterface.width;
    private val myHeight = StackLightWeightInterface.height;
    private val myArrow = MyArrow(80, 12);
    private var top: VisualNode
    private var size = 1
    private val nodeHeight = 80
    private var nodeWidth = 100
    private val startX = returnClosest(myWidth / 5, myWidth / 3, SPACING + 5)
    private val endX = returnClosest(myWidth - 2 * nodeWidth, myWidth - nodeWidth, SPACING + 5)
    private var startY = nodeHeight * 2
    private var endY = returnClosest(myHeight - 4 * nodeHeight, myHeight - 3 * nodeHeight, SPACING + 5)
    private var dynamicHeight = startY

    init {
        nodeWidth = endX - startX - 60
        top = VisualNode(0, startX + 30, endY - nodeHeight - SPACING)
        background = Color(0xA0F29)
        preferredSize = Dimension(myWidth, myHeight)
    }

    protected override fun paintComponent(g1: Graphics) {
        super.paintComponent(g1);
        val g: Graphics2D = g1 as Graphics2D
        drawGrid(g, Color(0x1C233D))
        drawBasket(g)
        var temp: VisualNode = top
        while (temp != null) {
            drawNode(g, temp)
            temp = temp.nextNode
        }
    }

    private fun shiftElements() {
        val shiftY = nodeHeight * 4
        var temp = top
        while (temp != null) {
            temp.yPos += shiftY
            temp = temp.nextNode
        }
    }

    private fun resize() {
        if (top.yPos % width < 50) {
            endY += nodeHeight * 4
            shiftElements()
        }
        // Ensure the panel grows so the JScrollPane can actually scroll
        preferredSize = Dimension(myWidth, endY + nodeHeight * 2)
        revalidate()
        repaint()
    }

    fun setCamCentered(scrollPane: JScrollPane) {
        resize()
        val viewport = scrollPane.viewport
        val viewRect = viewport.viewRect
        val currentX = viewRect.x
        val contentHeight = preferredSize.height
        val viewportHeight = viewRect.height
        val targetCenterY = top.yPos + nodeHeight / 2
        val unclampedY = targetCenterY - viewportHeight / 2
        val maxY = (contentHeight - viewportHeight).coerceAtLeast(0)
        val clampedY = unclampedY.coerceIn(0, maxY)
        viewport.viewPosition = Point(currentX, clampedY)
    }


    override fun push(value: Int) {
        val newNode = VisualNode(value, top.xPos, top.yPos - nodeHeight - 10)
        newNode.nextNode = top;
        newNode.nextAddress = top.address
        top = newNode
        resize()
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
        resize()
        repaint()
        return retData
    }

    override fun peek(): Int {
        return top.data
    }

    override fun sizeSt(): Int {
        return size;
    }

    override fun drawNode(g: Graphics2D, node: VisualNode) {
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
            it.drawString("${node.address}", 30, node.yPos + nodeHeight / 2 + 10)
            myArrow.draw(
                g,
                g.getFontMetrics(it.font).stringWidth(node.address) + 35,
                node.yPos + nodeHeight / 2,
                Color(0xFFD700)
            )
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

    override fun drawBasket(g: Graphics2D) {
        val stroke = g.stroke
        val color = g.color
        g.stroke = BasicStroke(15f)
        g.color = DefaultWindowsInterface.themeColorBG
        g.drawLine(startX, dynamicHeight, startX, endY)
        g.drawLine(startX, endY, endX, endY)
        g.drawLine(endX, endY, endX, dynamicHeight)
        g.stroke = stroke
        g.color = color
    }
}

class StackWindowUsable : JPanel(), DefaultWindowsInterface {
    private val myHeight = DefaultWindowsInterface.height;
    private val myWidth = DefaultWindowsInterface.width
    private lateinit var scrollPane: JScrollPane
    private var textField: JTextField
    private var pushButton: JButton
    private var popButton: JButton
    private val font = Font(Font.SANS_SERIF, Font.BOLD, 20)
    private val visualStackWindow = StackWindow().apply {
        preferredSize = Dimension(myWidth, myHeight)
    }

    init {
        layout = BorderLayout()
        val layeredPane = JLayeredPane().apply {
            preferredSize = Dimension(myWidth, myHeight)
        }
        scrollPane = JScrollPane(
            visualStackWindow,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        ).apply {
            verticalScrollBar.unitIncrement = 32
        }
        textField = textInput()
        pushButton = JButton("PUSH").apply {
            background = DefaultWindowsInterface.themeColorBG
            foreground = Color.WHITE
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            font = this@StackWindowUsable.font
            addActionListener {
                if (textField.inputVerifier.verify(textField)) {
                    visualStackWindow.push(textField.text.toInt())
                    textField.text = ""
                    visualStackWindow.setCamCentered(scrollPane)
                }
            }
            size = Dimension(100, 50)
        }
        popButton = JButton("POP").apply {
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            font = this@StackWindowUsable.font
            foreground = Color.WHITE
            background = Color.BLACK
            addActionListener {
                visualStackWindow.pop();
                visualStackWindow.setCamCentered(scrollPane)
            }
            size = Dimension(200, 50)
        }
        scrollPane.setBounds(0, 0, myWidth, myHeight)
        val mainPanel = JPanel().apply {
            layout = BorderLayout()
            val subPanel = JPanel(GridLayout(1, 2, 5, 5)).apply {
                add(textField)
                add(pushButton)
            }
            add(subPanel, BorderLayout.NORTH)
            add(popButton, BorderLayout.SOUTH)
            setBounds(myWidth / 2 - 150, myHeight - 200, 200, 100)
        }
        layeredPane.add(scrollPane)
        this.add(layeredPane, BorderLayout.CENTER)
        this.add(mainPanel, BorderLayout.SOUTH)
    }

    private fun textInput(): JTextField {
        return JTextField().apply {
            size = Dimension(100, 50)
            cursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)
            font = this@StackWindowUsable.font
            background = DefaultWindowsInterface.themeColorBG
            foreground = Color.WHITE
            horizontalAlignment = JTextField.CENTER
            toolTipText = "Value of the next block (Int): "
            inputVerifier = object : InputVerifier() {
                override fun verify(input: JComponent?): Boolean {
                    return try {
                        (input as? JTextField)?.text?.toInt()
                        true
                    } catch (_: NumberFormatException) {
                        false
                    }
                }
            }
            addKeyListener(object : KeyListener {
                override fun keyTyped(e: KeyEvent?) {
                    if (inputVerifier!!.verify(this@apply) && e?.keyChar == '\n') {
                        visualStackWindow.push(text.toInt())
                        this@StackWindowUsable.visualStackWindow.setCamCentered(this@StackWindowUsable.scrollPane)
                    }
                    if (e?.keyChar == 127.toChar()) {
                        visualStackWindow.pop()
                        this@StackWindowUsable.visualStackWindow.setCamCentered(this@StackWindowUsable.scrollPane)
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