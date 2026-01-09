package main.interfaces;

public interface LinkedListInterface extends DefaultWindowsInterface {
    int nodeWidth = 350;
    int nodeHeight = 200;

    /**
     * <li>This class provides the template to create a basic node.</li>
     * <li>A basic node consists of value and address of the node created.</li>
     *
     * @author Sri Koushik JK
     * @version v0.0.5
     * @since v0.0.5
     *
     */
    class Node {
        private final String address = "0x" + Integer.toHexString(System.identityHashCode(this));
        public int data;

        public Node(int data) {
            this.data = data;
        }

        public final String getAddress() {
            return address;
        }
    }

    /**
     * <li>This class provides the template to create a visual node.</li>
     * <li>A visual node inherits properties from a basic node.</li>
     * <li>A visual node contains (x,y) positions, address of the next node and a link to the next node</li>
     *
     * @author Sri Koushik JK
     * @version v0.0.5
     * @see main.interfaces.LinkedListInterface.Node
     * @since v0.0.5
     *
     */
    class VisualNode extends Node {
        public int xPos, yPos;
        private VisualNode nextNode;
        private String nextAddress;

        public VisualNode(int data, int x, int y) {
            super(data);
            this.xPos = x;
            this.yPos = y;
            this.nextNode = null;
            this.nextAddress = null;
        }

        public boolean isLast() {
            return this.nextNode == null;
        }

        public final VisualNode getNextNode() {
            return nextNode;
        }

        //TODO WORK THIS OUT
        public final void setNextNode(VisualNode node) {
            this.nextNode = node;
            if (node == null) {
                setNextAddress(null);
                return;
            }
            setNextAddress(node.nextAddress);
        }

        public final String getNextAddress() {
            return nextAddress;
        }

        public final void setNextAddress(String nextAddress) {
            this.nextAddress = nextAddress;
        }
    }
}
