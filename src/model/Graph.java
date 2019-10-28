package model;

import utils.Position;

import java.util.ArrayList;
import java.util.Random;

public class Graph {

    public ArrayList<Node> nodes = new ArrayList<>();


    public Graph(Node[][]nodeArray) {

        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = 0; j < nodeArray[0].length; j++) {
                nodes.add(nodeArray[i][j]);
            }
        }
    }

    public Node getRandomNode() {
        Random rand = new Random();

        return nodes.get(rand.nextInt(nodes.size()));
    }

    public Node getClosestNode(Position pos) {

        if (nodes.size() == 0) return null;

        Node closestNode = nodes.get(0);
        float shortestDistance = Position.Distance(pos, closestNode.pos);

        for (Node node : nodes) {
            if (!node.isEnabled) {
                continue;
            }
            float dist = Position.Distance(pos, node.pos);
            if ( dist < shortestDistance) {
                // new best
                closestNode = node;
                shortestDistance = dist;
            }
        }

        return closestNode;
    }
}
