# This is an implementation of A* pathfinding algorithm. 
There is a maze consisting of two players a bad guy and the user controlled player. 
The bad guy implements the algorithm every time the player moves and the algorithm is able to recalculate the best/shortest path through the maze to get to the user player. 
The bad guy moves at 3 frames per second and the user can be controlled using the arrow keys on your computer. 
The algorithm stores the final path in a stack and the bad guys next node is popped from the stack, a new node is popped from the stack each movement until the bad guy arrives at the same node as the player.
The maze consists of 40x40 node map conisting of boolean variables to note whether the current position on the map is a wall or not.
The bad guy is unable to pass through walls and hench must find a path around a wall everytie it is encountered.
