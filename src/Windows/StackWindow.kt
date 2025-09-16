package Windows

import Windows.Interfaces.DefaultWindowsInterface
import Windows.Interfaces.GridInterface
import Windows.Interfaces.GridInterface.SPACING
import Windows.Interfaces.LinkedListInterface.VisualNode
import Windows.Interfaces.StackLightWeightInterface
import java.awt.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

open class StackWindow : JPanel(), StackLightWeightInterface, GridInterface {
    val myWidth = StackLightWeightInterface.width;
    val myHeight = StackLightWeightInterface.height;
    private var top: VisualNode
    private var size = 0
    val nodeHeight = 90
    var nodeWidth = 100
    val startX = returnClosest(myWidth / 6, myWidth / 4, SPACING + 5)
    val endX = returnClosest(myWidth - 2*nodeWidth, myWidth - nodeWidth, SPACING +5)
    val startY = nodeHeight * 2
    val endY = returnClosest(myHeight - nodeHeight, myHeight, SPACING + 5)
    init {
        nodeWidth=endX-startX-60
        top = VisualNode(0, startX + 30, (myHeight - 2 * nodeHeight))
        background = Color.BLACK
        preferredSize = Dimension(myWidth, myHeight)
    }

    protected override fun paintComponent(g1: Graphics) {
        super.paintComponent(g1);
        val g: Graphics2D = g1 as Graphics2D
        drawGrid(g)
        drawBasket(g)
        drawNode(g,top)
    }

    override fun push(value: Int) {
        if (sizeSt() == 1) return;
        val newNode = VisualNode(value, top.xPos, top.yPos - nodeHeight)
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
        for (i in 0..myWidth step SPACING + 5) {
            g.drawLine(i, 0, i, myHeight)
        }
        for (i in 0..myHeight step SPACING + 5) {
            g.drawLine(0, i, myWidth, i)
        }
    }

    final override fun drawNode(g: Graphics2D, node: VisualNode) {
        val oldColor = g.color
        val resetStroke=g.stroke
        g.color = Color.ORANGE
        g.fillRect(node.xPos, node.yPos, nodeWidth, nodeHeight)
        g.color= Color.WHITE
        g.font=Font(Font.SANS_SERIF,Font.BOLD,25)
        g.stroke= BasicStroke(6f)
        g.drawString("Data: ${node.data}", node.xPos + nodeWidth / 4, node.yPos + nodeHeight / 2)
        g.drawLine(node.xPos + nodeWidth / 2, node.yPos, node.xPos + nodeWidth / 2, node.yPos + nodeHeight)
        g.drawString(
            "Address: ${node.nextAddress}",
            node.xPos + 3 * nodeWidth / 4,
            node.yPos + nodeHeight / 2
        )
        g.color = oldColor
        g.stroke=resetStroke
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