package view;

import model.MeshNetwork;
import model.Node;
import utils.Position;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class GraphCanvas extends JPanel {

    public boolean drawLinks = true;

    private final int nodeRadius = 10;
    private final Color colorPath = Color.blue;
    private final Color colorGraph = Color.gray;
    private final Color colorDisabledGraph = Color.black;


    private final Color colorShortDistance = Color.blue;
    private final Color colorMediumDistance = Color.orange;
    private final Color colorLongDistance = Color.red;

    private MeshNetwork meshNetwork;

    private int width;
    private int height;

    private Circle pointer = new Circle(0, 0, 5, Color.red);

    public GraphCanvas(MeshNetwork meshNetwork, int width, int height) {
        this.meshNetwork = meshNetwork;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintComponent(Graphics g) {

        paintBaseGraph(g);
        pointer.draw(g);

        Node closestNode = drawLineToClosestNode(g);

        if (pointerIsInRightSide()) {
            paintPath(g, meshNetwork.computePath(closestNode, meshNetwork.relay));
        }

    }

    private void paintBaseGraph(Graphics g) {

        //Draw all nodes
        for (Node node : meshNetwork.network.nodes) {
            if (node.isEnabled) {
                drawCircle(g, node.pos, nodeRadius, colorGraph);
            }
            else {
                drawCircle(g, node.pos, nodeRadius, colorDisabledGraph);
            }
        }

        //Draw relay
        drawCircle(g, meshNetwork.relay.pos, nodeRadius, Color.red);

        if (drawLinks)
            drawLink(g);
    }

    private Color getColorDependingOnDistance(float distance) {

        if (distance < meshNetwork.averageDistBetweenNode * 1f) {
            return colorShortDistance;
        }
        else if (distance < meshNetwork.averageDistBetweenNode * 5f) {
            return colorMediumDistance;
        }
        else {
            return colorLongDistance;
        }
    }

    private void drawLink(Graphics g) {
        //Draw all links on top of them
        for (Node node : meshNetwork.network.nodes) {
            for (Node neighbor : node.neighbors) {
                if (neighbor.isEnabled && node.isEnabled) {
                    drawLine(g, node.pos, neighbor.pos, colorGraph);
                }
            }
        }
    }

    private void paintPath(Graphics g, ArrayList<Node> path) {

        Node previousNode = null;
        for (Node node : path) {
            //skip relay
            if (node != path.get(path.size() - 1)) {
                drawCircle(g, node.pos, nodeRadius, colorPath);
            }
            if (previousNode != null) {
                drawLine(g, previousNode.pos, node.pos, colorPath);
            }

            previousNode = node;
        }
    }

    private void drawLine(Graphics g, Position a, Position b, Color color) {
        g.setColor( color );
        g.drawLine( a.x, a.y, b.x, b.y);
    }

    private Node drawLineToClosestNode(Graphics g) {


        Node closestNode;
        if (pointerIsInRightSide()) {
            closestNode = meshNetwork.network.getClosestNode(pointer.center);
        }
        else {
            closestNode = meshNetwork.relay;
        }

        float distance = Position.Distance(pointer.center, closestNode.pos);
        drawLine(g, pointer.center, closestNode.pos, getColorDependingOnDistance(distance));
        return closestNode;
    }

    private void drawCircle(Graphics g, Position center, int radius, Color color) {
        g.setColor(color);
        g.fillOval(center.x - radius / 2, center.y - radius / 2,
                radius, radius);
    }

    private boolean pointerIsInRightSide() {
        return pointer.center.x > width / 2;
    }

    public void setPosPointer(int x, int y) {
        pointer.center.x = x;
        pointer.center.y = y;
    }

}
