package model;


import java.util.ArrayList;
import java.util.Random;

public class MeshNetwork {


    public Graph network;
    public Node relay;
    public int averageDistBetweenNode;

    public ArrayList<Node> path;
    private Pathfinding pathfinding = new Pathfinding();



    public MeshNetwork(int width, int height, int averageDistBetweenNode) {
        this.averageDistBetweenNode = averageDistBetweenNode;
        this.network = generateGraph(width, height, averageDistBetweenNode);
    }

    public ArrayList<Node> computePath(Node start, Node goal) {
        path = pathfinding.getPathAStar(start, goal);
        return path;
    }

    public Graph generateGraph(int width, int height, int distance) {

        //Add nodes on one half of the network.
        int halfW = width / 2;

        int offsetH = (int)(height * 0.1f);

        int nbNodeWidth = halfW / distance;
        int nbNodeHeight = (height - offsetH) / distance;

        int offsetNode = (int)(distance * 0.9f);

        //temp array for better gen
        Node[][] arrayGraph = new Node[nbNodeWidth][nbNodeHeight];

        Random rand = new Random();
        for (int i = 0; i < nbNodeWidth; i++) {
            for (int j = 0; j < nbNodeHeight; j++) {

                int x = halfW + i * distance;
                int y = offsetH + j * distance;

                //apply offset
                x += rand.nextInt(offsetNode) - offsetNode / 2;
                y += rand.nextInt(offsetNode) - offsetNode / 2;
                arrayGraph[i][j] = new Node(x, y);
            }
        }
        //set neighbors
        for (int i = 0; i < nbNodeWidth; i++) {
            for (int j = 0; j < nbNodeHeight; j++) {
                if (i > 0) {
                    arrayGraph[i][j].neighbors.add(arrayGraph[i - 1][j]);
                }
                if (j > 0) {
                    arrayGraph[i][j].neighbors.add(arrayGraph[i][j - 1]);
                }
                if (i < nbNodeWidth - 1) {
                    arrayGraph[i][j].neighbors.add(arrayGraph[i + 1][j]);
                }
                if (j < nbNodeHeight - 1) {
                    arrayGraph[i][j].neighbors.add(arrayGraph[i][j + 1]);
                }
            }
        }

        this.relay = arrayGraph[0][nbNodeHeight / 2]; //center left

        return new Graph(arrayGraph);
    }

}
