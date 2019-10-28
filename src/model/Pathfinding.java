package model;

import utils.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Pathfinding {


    public ArrayList<Node> getPathAStar(Node startNode, Node goalNode) {

        NodeAStar start = new NodeAStar(startNode);
        NodeAStar goal = new NodeAStar(goalNode);
        NodeAStar current;

        if (start.equals(goal)) {
            return new ArrayList<>();
        }

        HashSet<NodeAStar> closedSet = new HashSet<>();
        HashSet<NodeAStar> openSet = new HashSet<>();
        int g = 0;

        openSet.add(start);

        //FLOOD STOP
        int MAX_ITER = 1000;
        int nbIter = 0;

        while (openSet.size() > 0 && nbIter < MAX_ITER) {
            nbIter++;

            current = getNodeWithMinF(openSet);

            closedSet.add(current);
            openSet.remove(current);

            if (current.equals(goal)) {

                ArrayList<Node> path = new ArrayList<>();

                goal.parent = current.parent;
                //The END
                current = goal;
                path.add(current.node);
                while (current.parent != null) {

                    path.add(current.parent.node);
                    current = current.parent;

                }

                Collections.reverse(path);

                return path;
            }

            //get neighbors
            ArrayList<NodeAStar> neighbors = getValidNeighbors(current);
            g++;

            for (NodeAStar neighbor : neighbors) {

                //if this neighbor is already in the closed list, ignore it
                if (closedSet.contains(neighbor))
                    continue;

                // if it's not in the open list...
                if (!openSet.contains(neighbor))
                {
                    // compute its scores, set the parent
                    neighbor.GScore = g;
                    neighbor.HScore = Position.Distance(neighbor.node.pos, goal.node.pos);
                    neighbor.parent = current;

                    // and add it to the open list
                    openSet.add(neighbor);
                }
                else
                {
                    // test if using the current G score makes the neighbors F score
                    // lower, if yes update the parent because it means it's a better path
                    if (g + neighbor.HScore < neighbor.getFScore())
                    {
                        neighbor.GScore = g;
                        neighbor.parent = current;
                    }
                }
            }
        }

        System.out.println("No path found! Flood count : " + nbIter);
        return new ArrayList<>();
    }

    private ArrayList<NodeAStar> getValidNeighbors(NodeAStar node) {

        ArrayList<NodeAStar> neighbors = new ArrayList<>();
        for (Node neighbor : node.node.neighbors)  {

            if (neighbor.isEnabled) {
                neighbors.add(new NodeAStar(neighbor));
            }
        }

        return neighbors;
    }

    private NodeAStar getNodeWithMinF(HashSet<NodeAStar> openSet) {
        NodeAStar bestNode = null;
        float minF = 0f;

        for (NodeAStar node : openSet) {
            if (bestNode == null) {
                bestNode = node;
                minF = node.getFScore();
            }

            if (node.getFScore() < minF) {
                bestNode = node;
                minF = node.getFScore();
            }
        }

        return bestNode;
    }

    static class NodeAStar {

        public Node node;
        public NodeAStar parent;
        public float GScore = 0f; //Start to node
        public float HScore = 0f; //Node to Goal (Heuristic)

        public NodeAStar(Node node) {
            this.node = node;
        }

        public float getFScore() {
            return HScore + GScore;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            NodeAStar otherNode = (NodeAStar)obj;

            return node.pos.equals(otherNode.node.pos);
        }

        @Override
        public int hashCode() {
            return node.hashCode();
        }


    }
}

