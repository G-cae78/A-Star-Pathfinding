public class Node {
    int x, y;
    int g,h,f;
    Node parent;
    boolean isExpanded;
    boolean isWall;

    public Node(int x, int y){
        this.x=x;
        this.y=y;
        g=0;
        h=0;
        isWall=false;
        isExpanded=false;
    }
    public void calculateF(){
        f=g+h; // calculating F
    }
    public void calculateG(){
        g= parent.g+1; // calculating g by increasing the parent's g by 1
    }
    @Override // Overriding the pre set equals method
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(obj instanceof Node){// checking if object is of type Node
            Node object = (Node) obj;
            return this.x==object.x && this.y==object.y; // cehcking fields of both objects to see if they are the same
        }
        return false;
    }

}
