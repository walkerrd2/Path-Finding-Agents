package path.agent;

import path.PathFinderApp;
import path.level.Level;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BreadthFirstAgent extends PathAgent{

    public BreadthFirstAgent(Level lvl){
        super(lvl);
    }
    @Override
    public List<Point> findPath() {
        if(start == null || goal == null){
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();
        queue.add(new Node(start,null,null)); //start with initial node
        visited.add(start);

        while(!queue.isEmpty()){
            Node curNode = queue.poll();

            // check to see if goal is reached
            if(curNode.getState().equals(goal)){
                path = pathFromNode(curNode);
                return path;
            }

            // Add neighbors to the queue
            for(Action action : Action.values()){
                Point nextState = getNextState(curNode.getState(),action);
                if(level.isValid(nextState) && !visited.contains(nextState)){
                    Node child = new Node(nextState, curNode, action);
                    queue.add(child);
                    visited.add(nextState);
                }
            }
        }
        return null; // null returned if no path is found
    }

    //Helper method to get the next state
    private Point getNextState(Point current, Action action){
        // returns the next state based on the action
        switch (action){
            case N: return new Point(current.x, current.y - PathFinderApp.STRIDE);
            case S: return new Point(current.x, current.y + PathFinderApp.STRIDE);
            case E: return new Point(current.x + PathFinderApp.STRIDE, current.y);
            case W: return new Point(current.x - PathFinderApp.STRIDE, current.y);
        }
        return current;
    }


    public String toString() {
        return "Bread First";
    }
}
