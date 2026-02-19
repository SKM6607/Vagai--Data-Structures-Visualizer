package main.interfaces;

public interface TreeLightWeightInterface extends TreeInterface
{
    class Tree
    {
        public final TreeNode root;
        //TODO IMPLEMENT NODES AT EACH LEVEL
        private int depth;
        public Tree(){
            this.root=new TreeNode(0,0,0,null);
            this.depth=0;
            this.totalNodes=0;
        }
        private int totalNodes;

        public int getDepth() {
            return depth;
        }

        public int getTotalNodes() {
            return totalNodes;
        }


    }
    default void insertNode(TreeNode node,TreeNode... children){
        for (TreeNode child : children) {
            node.addChild(child);
        }
    }
    default void deleteNode(TreeNode node,int deleteIdx){
        node.removeChild(deleteIdx);
    }
    default void updateNode(TreeNode node,int val){
        node.data=val;
    }
}
