package path.agent;
import path.PathFinderApp;
import path.level.Level;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AstarAgent extends PathAgent{

    public AstarAgent(Level lvl) {
        super(lvl);
    }
    @Override
    public List<Point> findPath() {
        if(start == null || goal == null){
            return null;
        }
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<Point> visited = new HashSet<>();
        queue.add(new Node(start,null,null)); // Starting with initial node
        visited.add(start);

        while(!queue.isEmpty()){
            Node curNode = queue.poll();

            // checking to see if the goal was reached
            if(curNode.getState().equals(goal)){
                path = pathFromNode(curNode);
                return path;
            }

            // Add neighbors to the queue
            for(Action action : Action.values()){
                Point nextState = getNextState(curNode.getState(),action);
                if(level.isValid(nextState) && !visited.contains(nextState)){
                    Node child = new Node(nextState,curNode,action);
                    child.setFval(curNode.getCost() + heuristic(nextState));
                    queue.add(child);
                    visited.add(nextState);
                }
            }
        }
        return null; // if null returned, no path is found
    }

    //Helper same as Breadth First and Greedy
    private Point getNextState(Point current, Action action){
        switch (action){
            case N: return new Point(current.x, current.y - PathFinderApp.STRIDE);
            case S: return new Point(current.x, current.y + PathFinderApp.STRIDE);
            case E: return new Point(current.x + PathFinderApp.STRIDE, current.y);
            case W: return new Point(current.x - PathFinderApp.STRIDE, current.y);
        }
        return current;
    }

    //Helper for heuristic
    private double heuristic(Point p){
        return Math.abs(goal.x - p.x) + Math.abs(goal.y - p.y);
    }


    public String toString() {
        return "A-Star Agent";
    }
}
