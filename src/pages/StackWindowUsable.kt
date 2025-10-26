package pages
import pages.interfaces.DefaultWindowsInterface
import pages.interfaces.GridInterface
import pages.interfaces.GridInterface.SPACING
import pages.interfaces.LinkedListInterface.VisualNode
import pages.interfaces.StackLightWeightInterface
import shapes.MyArrow
import utils.AnimationHelper
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*

class StackWindow : JPanel(), StackLightWeightInterface, GridInterface {
    private val myWidth = StackLightWeightInterface.width
    private val myHeight = StackLightWeightInterface.height
    private val myArrow = MyArrow(80, 12)
    private var top: VisualNode
    private var size = 1
    private val nodeHeight = 80
    private var nodeWidth = 100
    private val startX = returnClosest(myWidth / 5, myWidth / 3, SPACING + 5)
    private val endX = returnClosest(myWidth - 2 * nodeWidth, myWidth - nodeWidth, SPACING + 5)
    private var startY = nodeHeight * 2
    private var endY = returnClosest(myHeight - 4 * nodeHeight, myHeight - 3 * nodeHeight, SPACING + 5)
    private var dynamicHeight = startY
    private var stopPop = false
    private var isAnimating = false
    private val animationSpeed = 15

    init {
        nodeWidth = endX - startX - 60
        top = VisualNode(0, startX + 30, endY - nodeHeight - SPACING)
        background = Color(0xA0F29)
        preferredSize = Dimension(myWidth, myHeight)
    }

    @Suppress("SENSELESS_COMPARISON")
    protected override fun paintComponent(g1: Graphics) {
        super.paintComponent(g1)
        val g: Graphics2D = g1 as Graphics2D
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        drawGrid(g, Color(0x1C233D))
        drawTitle(g)
        drawBasket(g)
        var temp: VisualNode = top
        while (temp != null) {
            drawNode(g, temp)
            temp = temp.nextNode
        }
    }
    
    private fun drawTitle(g: Graphics2D) {
        g.color = Color.WHITE
        g.font = Font(Font.SANS_SERIF, Font.BOLD, 28)
        g.drawString("Stack Visualization (LIFO)", myWidth / 2 - 170, 40)
        
        g.font = Font(Font.SANS_SERIF, Font.BOLD, 18)
        g.color = Color(0xFFD700)
        g.drawString("Size: $size", myWidth / 2 - 40, 70)
    }

    @Suppress("SpellCheckingInspection")
    fun setCamCentered(scrollPane: JScrollPane) {
        if (isAnimating) return // Don't scroll during animation
        
        SwingUtilities.invokeLater {
            val viewport = scrollPane.viewport
            val viewRect = viewport.viewRect
            val currentX = viewRect.x
            val contentHeight = preferredSize.height
            val viewportHeight = viewRect.height
            val targetCenterY = top.yPos + nodeHeight / 2
            val unclampedY = targetCenterY - viewportHeight / 2
            val maxY = (contentHeight - viewportHeight).coerceAtLeast(0)
            val clampedY = unclampedY.coerceIn(0, maxY)
            
            // Smooth scroll animation
            val startY = viewRect.y
            val distance = clampedY - startY
            val steps = 10
            var currentStep = 0
            
            val scrollTimer = Timer(20) {
                currentStep++
                val progress = AnimationHelper.easeInOut(currentStep.toFloat() / steps)
                val newY = startY + (distance * progress).toInt()
                viewport.viewPosition = Point(currentX, newY)
                
                if (currentStep >= steps) {
                    (it.source as Timer).stop()
                    viewport.viewPosition = Point(currentX, clampedY)
                }
            }
            scrollTimer.start()
        }
    }

    override fun drawGrid(g: Graphics2D, color: Color?) {
        val retColor = g.color
        g.color = color
        run {
            var i = 0
            while (i < DefaultWindowsInterface.width) {
                g.drawLine(i, 0, i, preferredSize.height)
                i += SPACING
            }
        }
        var i = 0
        while (i < preferredSize.height) {
            g.drawLine(0, i, DefaultWindowsInterface.width, i)
            i += SPACING
        }
        g.color = retColor
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun shiftElements() {
        val shiftY = nodeHeight * 4
        var temp = top
        while (temp != null) {
            temp.yPos += shiftY
            temp = temp.nextNode
        }
    }

    private fun resizePush() {
        if (top.yPos % myHeight < 20 ) {
            endY += nodeHeight * 4
            shiftElements()
        }
        preferredSize = Dimension(myWidth, endY + nodeHeight * 2)
        revalidate()
        repaint()
    }

    override fun push(value: Int) {
        if (isAnimating) return
        
        size++
        resizePush()
        stopPop = false
        
        val newNode = VisualNode(value, top.xPos, -nodeHeight)
        newNode.nextNode = top
        newNode.nextAddress = top.address
        val oldTop = top
        top = newNode
        
        // Smooth animation for push
        animatePush(newNode, oldTop.yPos - nodeHeight - 10)
    }
    
    private fun animatePush(node: VisualNode, targetY: Int) {
        isAnimating = true
        val timer = Timer(animationSpeed) {
            if (node.yPos < targetY) {
                node.yPos += 12
                repaint()
            } else {
                node.yPos = targetY
                isAnimating = false
                (it.source as Timer).stop()
                repaint()
            }
        }
        timer.start()
    }

    override fun pop(): Int {
        if (isAnimating) return top.data
        
        if (stopPop) {
            dynamicHeight = myHeight
            preferredSize = Dimension(myWidth, myHeight)
            this.scrollRectToVisible(Rectangle(preferredSize))
            repaint()
            return top.data
        }
        
        if (sizeSt() == 1 && !stopPop) {
            stopPop = true
            dynamicHeight -= nodeHeight * 2
            animatePop(top, -nodeHeight * 3, true)
            endY -= nodeHeight * 2
            return top.data
        }
        
        val retData = top.data
        val nodeToRemove = top
        top.nextAddress = null
        val temp = top.nextNode
        top.nextNode = null
        top = temp
        size--
        
        // Smooth animation for pop
        animatePop(nodeToRemove, -nodeHeight * 2, false)
        
        return retData
    }
    
    private fun animatePop(node: VisualNode, targetY: Int, isSingleElement: Boolean) {
        isAnimating = true
        val timer = Timer(animationSpeed) {
            if (node.yPos > targetY) {
                node.yPos -= 12
                repaint()
            } else {
                node.yPos = targetY
                isAnimating = false
                (it.source as Timer).stop()
                if (!isSingleElement) {
                    resizePop()
                }
                repaint()
            }
        }
        timer.start()
    }

    private fun resizePop() {
        if (preferredSize.height % top.yPos > 100 && sizeSt() > 1) {
            dynamicHeight = top.yPos - nodeHeight * 2
        }
        preferredSize = Dimension(myWidth, endY)
        this.scrollRectToVisible(Rectangle(preferredSize))
        revalidate()
        repaint()
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
        
        // Draw shadow for depth
        g.color = Color(0, 0, 0, 50)
        g.fillRoundRect(node.xPos + 4, node.yPos + 4, nodeWidth, nodeHeight, 10, 10)
        
        // Draw main node with rounded corners
        g.color = Color(0x1E3A8A)
        g.fillRoundRect(node.xPos, node.yPos, nodeWidth, nodeHeight, 10, 10)
        
        // Draw border
        g.color = Color(0xFFD700)
        g.stroke = BasicStroke(4f)
        g.drawRoundRect(node.xPos, node.yPos, nodeWidth, nodeHeight, 10, 10)
        
        // Draw vertical divider
        g.stroke = BasicStroke(3f)
        g.drawLine(node.xPos + nodeWidth / 2, node.yPos + 5, node.xPos + nodeWidth / 2, node.yPos + nodeHeight - 5)
        
        // Draw data section
        g.font = Font(Font.SANS_SERIF, Font.BOLD, 26)
        g.color = Color.WHITE
        val dataStr = "${node.data}"
        val fm = g.getFontMetrics(g.font)
        g.drawString(dataStr, node.xPos + nodeWidth / 4 - fm.stringWidth(dataStr) / 2, node.yPos + nodeHeight / 2 + 10)
        
        // Draw label
        g.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        g.color = Color(0xFFD700)
        g.drawString("Data", node.xPos + nodeWidth / 4 - 15, node.yPos + 20)
        
        // Draw next address
        g.font = Font(Font.SANS_SERIF, Font.BOLD, 14)
        g.color = Color.WHITE
        val nextAddr = if (node.nextAddress == null) "NULL" else node.nextAddress!!.substring(0, minOf(6, node.nextAddress!!.length))
        g.drawString(nextAddr, node.xPos + 3 * nodeWidth / 5 - 10, node.yPos + nodeHeight / 2 + 10)
        
        g.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        g.color = Color(0xFFD700)
        g.drawString("Next", node.xPos + 3 * nodeWidth / 5 - 5, node.yPos + 20)
        
        // Draw address pointer
        g.color = Color.WHITE
        g.font = Font(Font.SANS_SERIF, Font.PLAIN, 11)
        val shortAddr = node.address.substring(0, minOf(8, node.address.length))
        g.drawString(shortAddr, 30, node.yPos + nodeHeight / 2 + 5)
        
        myArrow.draw(
            g,
            g.getFontMetrics(g.font).stringWidth(shortAddr) + 35,
            node.yPos + nodeHeight / 2,
            Color(0xFFD700)
        )
        
        g.color = oldColor
        g.stroke = resetStroke
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
    private var scrollPane: JScrollPane
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