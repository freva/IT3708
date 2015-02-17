package boids.simulation.objects;

import boids.Main;

import java.awt.*;
import java.util.HashMap;

public abstract class Entity {
    protected double x, y;

    protected Entity() {
        this.x = Math.random() * Main.SIMULATION_WIDTH;
        this.y = Math.random() * Main.SIMULATION_HEIGHT;
    }

    public double distanceTo(Entity other) {
        return Math.sqrt(Math.pow(x-other.getX(), 2) + Math.pow(y-other.getY(), 2)) + 0.01;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void tick(HashMap<Entity, Double> distances);
    public abstract void draw(Graphics g);
}
