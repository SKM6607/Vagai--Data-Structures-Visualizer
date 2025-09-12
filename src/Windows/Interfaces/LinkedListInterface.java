package Windows.Interfaces;
public interface LinkedListInterface extends DefaultWindowsInterface{
    int nodeWidth=350;
    int nodeHeight=200;
    final class VisualNode {
        public int xPos,yPos,data;
        public final String address="0x"+Integer.toHexString(System.identityHashCode(this));
        public String nextAddress;
        public VisualNode nextNode;
        public VisualNode(int data, int x, int y) {
            this.data=data;
            this.xPos=x;
            this.yPos=y;
            this.nextNode=null;
            this.nextAddress=null;
        }
        public boolean isLast(){
            return this.nextNode==null;
        }
    }
}
