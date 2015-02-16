package boids.simulation.objects;

import java.awt.*;
import java.util.HashMap;

public class Obstacle extends Entity {
    public Obstacle(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(HashMap<Entity, Double> distances) { }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(getX()-8, getY()-8, 15, 15);
    }
}
