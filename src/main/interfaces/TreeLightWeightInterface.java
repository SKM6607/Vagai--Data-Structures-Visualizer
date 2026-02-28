package main.interfaces;

import lombok.Getter;

public interface TreeLightWeightInterface extends TreeInterface {
    default TreeNode setChildren(TreeNode parent, TreeNode child, TreeNode.NodeDirection direction) {
            /*if (root == null) {
                return new TreeNode(value);
            }
            if (value < root.value) {
                root.left = insert(root.left, value);
            } else if (value > root.value) {
                root.right = insert(root.right, value);
            }*/
        child.setYPos(parent.getYPos() + 2 * nodeRadius);
        if (parent.getRight() == null && direction.direction.equalsIgnoreCase("Right")) {
            child.setXPos(5 * parent.getXPos() / 4 - nodeRadius);
            parent.setRight(child);
            return parent.getRight();
        }
        if (parent.getLeft() == null && direction.direction.equalsIgnoreCase("Left")) {
            child.setXPos(3 * parent.getXPos() / 4 + nodeRadius);
            parent.setLeft(child);
            return parent.getLeft();
        }

        return parent;
    }

    default void deleteNode(TreeNode node, TreeNode.NodeDirection direction) {
        if (node.isLastNode()) return;
        if (node.getRight() != null && direction.direction.equalsIgnoreCase("Right")) {
            node.setRight(null);
        }
        if (node.getLeft() != null && direction.direction.equalsIgnoreCase("Left")) {
            node.setLeft(null);
        }
    }

    default void updateNode(TreeNode node, int val) {
        node.data = val;
    }

    class Tree implements TreeLightWeightInterface {
        @Getter
        public final TreeNode root;
        private TreeNode currentNode;
        //TODO IMPLEMENT NODES AT EACH LEVEL
        @Getter
        private int depth;
        @Getter
        private int totalNodes;

        public Tree() {
            this.root = new TreeNode(0, width / 2 - nodeRadius, 100);
            this.currentNode = root;
            this.depth = 0;
            this.totalNodes = 0;
        }

        public void extendTree(TreeNode node, TreeNode.NodeDirection direction) {
            extendTree(currentNode, node, direction);
        }

        public void extendTree(TreeNode parentNode, TreeNode node, TreeNode.NodeDirection direction) {
            this.currentNode = setChildren(parentNode, node, direction);
            this.totalNodes++;
        }

        public void shortenTreeThroughDirection(TreeNode node, TreeNode.NodeDirection direction) {
            deleteNode(node, direction);
            this.totalNodes--;
        }

        public void updateCurrentNode(int val) {
            updateSpecificNode(currentNode, val);
        }

        public void updateSpecificNode(TreeNode node, int val) {
            updateNode(node, val);
        }
    }
}
