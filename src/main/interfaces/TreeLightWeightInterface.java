package main.interfaces;

import lombok.Getter;

public interface TreeLightWeightInterface extends TreeInterface {

    default void updateNode(TreeNode node, int val) {
        node.data = val;
    }

    class Tree implements TreeLightWeightInterface {
        @Getter
        public final TreeNode root;
        @Getter
        private final int maxPossibleNodes = 17;
        //TODO IMPLEMENT NODES AT EACH LEVEL
        @Getter
        private int depth;
        @Getter
        private int totalNodes;

        public Tree() {
            this.root = new TreeNode(0, width / 2 - nodeRadius, 100);
            this.depth = 0;
            this.totalNodes = 1;
        }

        public static void alignAllTreeNodes(Tree tree) {
            alignAllTreeNodesHelper(0, tree.root);
        }

        private static void alignAllTreeNodesHelper(int depth, TreeNode current) {
            if (current == null) return;
            int gap = width / (1 << (depth + 1));
            TreeNode right = current.getRight();
            TreeNode left = current.getLeft();
            if (right != null) {
                right.setYPos(current.getYPos() + 2 * nodeRadius);
                if (left == null) right.setXPos(current.getXPos());
                else right.setXPos(current.getXPos() + gap);
            }
            if (left != null) {
                left.setYPos(current.getYPos() + 2 * nodeRadius);
                if (right == null) left.setXPos(current.getXPos());
                else left.setXPos(current.getXPos() - gap);
            }
            alignAllTreeNodesHelper(depth + 1, current.getRight());
            alignAllTreeNodesHelper(depth + 1, current.getLeft());
        }

        public void treeReset() {
            this.root.setLeft(null);
            this.root.setRight(null);
            this.depth = 0;
            this.totalNodes = 1;
        }

        private void setChildren(TreeNode parent, TreeNode child, TreeNode.NodeDirection direction) throws ArithmeticException {
            child.setYPos(parent.getYPos() + 2 * nodeRadius);
            if (parent.isLastNode()) {
                if (direction.direction.equalsIgnoreCase(TreeNode.NodeDirection.RIGHT.direction))
                    parent.setRight(child);
                else parent.setLeft(child);
                child.setXPos(parent.getXPos());
            } else {
                int gap = width / Math.toIntExact(Math.round(Math.pow(2, depth + 1)));
                if (parent.getRight() != null && direction.direction.equalsIgnoreCase(TreeNode.NodeDirection.LEFT.direction)) {
                    var right = parent.getRight();
                    right.setXPos(parent.getXPos() + gap);
                    child.setXPos(parent.getXPos() - gap);
                    parent.setLeft(child);
                    return;
                }
                if (parent.getLeft() != null && direction.direction.equalsIgnoreCase(TreeNode.NodeDirection.LEFT.direction)) {
                    var left = parent.getLeft();
                    left.setXPos(parent.getXPos() - gap);
                    child.setXPos(parent.getXPos() + gap);
                    parent.setRight(child);
                }
            }
        }

        public void extendTreeAsBinarySearchTree(TreeNode node, TreeNode target) {
            if (node == null)
                return;
            if (node.data < target.data) {
                if (node.getLeft() == null) node.setLeft(target);
                else extendTreeAsBinarySearchTree(node.getLeft(), target);
            } else {
                if (node.getRight() == null) node.setRight(target);
                else extendTreeAsBinarySearchTree(node.getRight(), target);
            }
        }

        public void extendTree(TreeNode parentNode, TreeNode node, TreeNode.NodeDirection direction) {
            setChildren(parentNode, node, direction);
            this.depth = calculateDepth(this.root);
            this.totalNodes++;
        }

        private int calculateDepth(TreeNode node) {
            if (node == null) return -1;
            return 1 + Math.max(calculateDepth(node.getLeft()), calculateDepth(node.getRight()));
        }

        public void removeNode(TreeNode target) {
            if (target != null) {
                this.removeNode(this.root, target);
                this.depth = calculateDepth(this.root);
                this.totalNodes = 0;
                calculateTotalNodes(root);
            }
        }

        private void calculateTotalNodes(TreeNode current) {
            if (current == null)
                return;
            this.totalNodes++;
            calculateTotalNodes(current.getLeft());
            calculateTotalNodes(current.getRight());
        }

        private void removeNode(TreeNode current, TreeNode target) {
            if (current == null) {
                return;
            }
            if (current.getRight() == target || current.getLeft() == target) {
                if (current.getRight() == target) {
                    current.setRight(null);
                }
                if (current.getLeft() == target) {
                    current.setLeft(null);
                }
                return;
            }
            removeNode(current.getLeft(), target);
            removeNode(current.getRight(), target);
        }
    }
}
