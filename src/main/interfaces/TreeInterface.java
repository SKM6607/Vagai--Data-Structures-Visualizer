package main.interfaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import main.interfaces.LinkedListInterface.Node;

import java.awt.*;

public interface TreeInterface extends DefaultWindowsInterface {
    int nodeRadius = 70;
    @Data
    class TreeNode extends Node {
        private int xPos, yPos;
        private Color textColor;
        private Color nodeColor;
        private TreeNode right, left;
        public enum NodeDirection{
            RIGHT("RIGHT"),
            LEFT("LEFT");
            final String direction;
            NodeDirection(String string){
                direction=string;
            }
        }

        public TreeNode(int data, int x, int y) {
            super(data);
            this.xPos = x;
            this.yPos = y;
            this.textColor = Color.WHITE;
            this.nodeColor = backgroundColor;
        }
        public TreeNode(int data) {
            super(data);
            this.xPos = this.yPos = 0;
            this.textColor = Color.WHITE;
            this.nodeColor = backgroundColor;
        }
        public boolean isLastNode() {
            return this.left == null && this.right == null;
        }

        public int getChildrenCount() {
            int n = 0;
            if (right != null) n++;
            if (left != null) n++;
            return n;
        }
    }
}
