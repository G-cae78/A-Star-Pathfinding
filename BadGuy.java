
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import static java.lang.Math.abs;

public class BadGuy {

    Image myImage;
    int x=0,y=0;
    boolean hasPath=false;
    boolean finishedSearch=false;
    boolean solvable=false;
    int prevTx=0;
    int prevTy=0;

    public BadGuy( Image i ) {
        myImage=i;
        x = 30;
        y = 10;
    }

    //initlaising array list's for closed and open list and a stack to store the computed path
    ArrayList<Node> openList= new ArrayList<>();
    ArrayList<Node> closedList= new ArrayList<>();
    Stack<Node> finalPath= new Stack<>();

    public boolean reCalcPath(boolean map[][],int targx, int targy) {
        // TO DO: calculate A* path to targx,targy, taking account of walls defined in map[][]
        Node[][] nodes = new Node[map.length][map[0].length];// initialising array of Nodes the same size as the map
        Node startNode= new Node(x,y); // storing start node
        Node targetNode= new Node(targx,targy); // storing target node
        Node nextNode= startNode; // setting initial nextNode to start node
        int minF=Integer.MAX_VALUE; // initialising minF to 100000

        //clearing all lists and paths before recalculating
       openList.clear();
       closedList.clear();
       finalPath.clear();
        //pushing start node to
        //finalPath.push(startNode);

        for (int x=0;x<40;x++) {
            for (int y=0;y<40;y++) {
                nodes[x][y]=new Node(x,y);
               if(map[x][y]){
                    closedList.add(nodes[x][y]);//adding all walls to closed list
                  // System.out.println("Added wall node at (" + x + ", " + y + ") to closedList.");
               }
            }
        }
        openList.add(startNode);//adding the initial position of the bad guy to the open list
        //System.out.println("Added start node at (" + startNode.x + ", " + startNode.y + ") to openList.");

        while(!openList.isEmpty()) {
              minF=Integer.MAX_VALUE;
              // looping through openList to find node with lowest F value
              for (Node node : openList) {
                  if (node.f < minF) {
                      nextNode = node;// making node with lowest F next node
                      minF = node.f;
                  }
              }

              //removing node from open list
             // openList.remove(nextNode);
            //System.out.println("Removed node at (" + nextNode.x + ", " + nextNode.y + ") from openList and added to closedList.");

              //finalPath.add(nextNode);// should use recursion to find parents and then add them to the stack
              if (nextNode.equals(targetNode)) { //if the target has been found start to create the final path
                  createPath(nextNode);// calling function to create final path
                  solvable = true;
                  break;
              }
            expandNode(nodes, nextNode, targetNode);// expand  current node
            closedList.add(nextNode); // adding the node to the closedList after it has been expanded
            openList.remove(nextNode);// removing node from open List after it has been expanded
            System.out.println(nextNode.x+"and "+nextNode.y);

              //closedList.add(nextNode);// after node is expanded add node to closed list

          }
          if(solvable){
              return true;
          }
         return false;
    }

    public void createPath(Node nextStep){
        // using recursion to find the node's parent's an work backwards to the badguy's initial position
        if(nextStep==null){
            return;
        }
        else{
            Node step= nextStep.parent;
            finalPath.push(step);// psuh each node onto the stack
            createPath(nextStep.parent);// repeat for all nodes in the path
        }
    }
    public void expandNode(Node[][] nodes, Node nextNode,Node target){
        nodes[nextNode.x][nextNode.y].isExpanded=true;
        if(!finishedSearch) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if ((i == 0 && j == 0) || (i != 0 && j != 0)) {
                        continue; // making sure the node is only expanded sideways and above and bottom
                    }
                    //looping around if at the edge of the map
                        int newX = nextNode.x + i;
                        int newY = nextNode.y + j;
                        if (newX >= 0 && newX < nodes.length && newY >= 0 && newY < nodes[0].length) { // checking that the newX and NewY are within the map bounds
                            Node beside= nodes[newX][newY]; // creating new neighbour Node
                            if(!closedList.contains(beside) && !openList.contains(beside)) {
                                openList.add(beside); // expanding nodes ad adding surrounding nodes to open list
                                beside.parent = nextNode; // setting the expanded node parent
                                //calculating H for each node using the Manhattan algorithm
                                beside.h = calculateHManhattanDistance(beside, target);// calculating h
                                beside.calculateG();// calculating G with method
                                beside.calculateF();// calculating F
                            }

                        }
                }
            }
        }
    }

    private int calculateHManhattanDistance(Node node, Node target){
        return abs(node.x- target.x)+abs(node.y-target.y); // Algorithm to calculate the heuristic cost
    }

    public void move(boolean map[][],int targx, int targy) {
        if(prevTx!=targx && prevTy!=targy){ // if the target has moved create a new path
            if(reCalcPath(map, targx, targy)){
                hasPath=true;// if maze is solvable return true
            }
            prevTx=targx;
            prevTy=targy;
        }
        else if (hasPath && !finalPath.isEmpty()) { // if there is a path start movement towards target
            Node nextNode = finalPath.pop();// popping node from stack
            if (nextNode != null) { // repeat proess till stack is empty
                //System.out.println("to "+nextNode.x+","+nextNode.y);
                x = nextNode.x;// setting x to the node's x from stack
                y = nextNode.y;// setting x to the node's x from stack
            }
        }
        else {
            // no path known, so just do a dumb 'run towards' behaviour
            int newx=x, newy=y;
            if (targx<x)
                newx--;
            else if (targx>x)
                newx++;
            if (targy<y)
                newy--;
            else if (targy>y)
                newy++;
            if (!map[newx][newy]) {
                x=newx;
                y=newy;
            }
        }
    }

    public void paint(Graphics g) {

        g.drawImage(myImage, x*20, y*20, null);
    }

}

