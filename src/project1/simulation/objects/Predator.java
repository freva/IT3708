package project1.simulation.objects;

import project1.simulation.Vector2D;

import java.awt.*;
import java.util.HashMap;

public class Predator extends Boid {
    protected double separationWeight = -20, predatorWeight = 50;


    public Predator() {
        maxSpeed = 6;
        separationRadius = 80;
    }

    @Override
    public void tick(HashMap<Entity, Double> distances) {
        Vector2D separationVector = getSeparationVector(distances).scale(separationWeight);
        Vector2D predatorVector = getPredatorVector(distances).scale(predatorWeight);
        accelerate(separationVector.sum(predatorVector));
    }


    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int) getX() - 6, (int) getY() - 6, 11, 11);

        g.setColor(Color.black);
        g.drawLine((int) getX(), (int) getY(), (int) (getX() + 1.2*velocity.getX()), (int) (getY() + 1.2*velocity.getY()));
    }
}
