package project1.simulation.objects;

import java.awt.*;
import java.util.HashMap;

public class Obstacle extends Entity {

    @Override
    public void tick(HashMap<Entity, Double> distances) { }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillOval((int) getX() - 8, (int) getY() - 8, 15, 15);
    }

    @Override
    public void move() { }
}
