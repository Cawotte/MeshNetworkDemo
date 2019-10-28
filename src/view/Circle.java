package view;

import utils.Position;

import java.awt.*;

public class Circle {

    public Position center;
    public int radius;

    public Color color;

    public Circle(Position center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public Circle(int x, int y, int radius, Color color) {
        this(new Position(x, y), radius, color);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(center.x - radius / 2, center.y - radius / 2,
                radius, radius);
    }
}
