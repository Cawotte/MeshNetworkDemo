package utils;

import java.util.Objects;

public class Position {

    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position() {
        this(0, 0);
    }


    public static float Distance(Position a, Position b) {

        return (float)Math.sqrt(
                Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position otherPos = (Position)o;
        return (x == otherPos.x && y == otherPos.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
