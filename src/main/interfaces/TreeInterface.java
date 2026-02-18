package main.interfaces;

import java.awt.*;
import java.util.ArrayList;
import main.interfaces.LinkedListInterface.Node;
public interface TreeInterface extends DefaultWindowsInterface {
    int nodeRadius=30;
    class TreeNode extends Node
    {
        private int xPos,yPos;
        private Color textColor;
        private Color nodeColor;
        private final ArrayList<TreeNode> children;
        public TreeNode(int data, int x, int y,ArrayList<TreeNode> children) {
            super(data);
            this.xPos=x;
            this.yPos=y;
            this.children=children;
            this.textColor=Color.BLACK;
            this.nodeColor=Color.WHITE;
        }
        public void setNodeColor(Color color){
            this.nodeColor=color;
        }
        public void setTextColor(Color color){
            this.textColor=color;
        }
        public boolean isLastNode(){
            return this.children==null;
        }
        public TreeNode getChild(int childIdx){
            return this.children.get(childIdx);
        }
        public void addChild(TreeNode child){
            this.children.add(child);
        }
        public void removeChild(int childIdx){
            this.children.remove(childIdx);
        }
        public int getChildrenCount(){
            return children.size();
        }

        public int getXPos() {
            return xPos;
        }

        public void setXPos(int xPos) {
            this.xPos = xPos;
        }

        public int getYPos() {
            return yPos;
        }

        public void setYPos(int yPos) {
            this.yPos = yPos;
        }

        public Color getTextColor() {
            return textColor;
        }

        public Color getNodeColor() {
            return nodeColor;
        }
    }
}
