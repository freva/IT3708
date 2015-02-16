package boids.simulation.objects;

import boids.simulation.Vector2D;

import java.awt.*;
import java.util.HashMap;

public class Predator extends Boid {
    protected double separationWeight = -20, predatorWeight = 50;


    public Predator(int x, int y) {
        super(x, y);
        maxSpeed = 6;
        separationRadius = 80;
    }

    @Override
    public void tick(HashMap<Entity, Double> distances) {
        Vector2D separationVector = getSeparationVector(distances).scale(separationWeight);
        Vector2D predatorVector = getPredatorVector(distances).scale(predatorWeight);
        accelerate(separationVector.sum(predatorVector));
        move(velocity);
    }


    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(getX() - 6, getY() - 6, 11, 11);

        g.setColor(Color.black);
        g.drawLine(getX(), getY(), getX() + (int) (7 * Math.cos(angle)), getY() + (int) (7 * Math.sin(angle)));
    }
}
