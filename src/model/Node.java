package model;

import java.util.ArrayList;
import utils.*;

public class Node {

    public Position pos;
    public ArrayList<Node> neighbors = new ArrayList<>();

    public boolean isEnabled = true;

    public Node(Position pos) {
        this.pos = pos;
    }

    public Node(int x, int y) {
        this(new Position(x, y));
    }

    public float distanceWith(Node b) {
        return Position.Distance(pos, b.pos);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node otherNode = (Node)o;

        return pos.equals(otherNode.pos);
    }

    @Override
    public int hashCode() {
        return pos.hashCode();
    }
}
