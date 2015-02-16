package boids.simulation.objects;

import boids.Main;
import boids.simulation.Vector2D;

import java.awt.*;
import java.util.HashMap;

public abstract class Entity {
    protected double x, y;

    protected Entity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Entity other) {
        return Math.sqrt(Math.pow(x-other.getX(), 2) + Math.pow(y-other.getY(), 2)) + 0.01;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public void move(Vector2D v) {
        this.x = modulo((int) (this.x + v.getX()), Main.SIMULATION_WIDTH);
        this.y = modulo((int) (this.y + v.getY()), Main.SIMULATION_HEIGHT);
    }

    private int modulo(int n, int m) {
        return (n < 0) ? (m - (Math.abs(n) % m) ) %m : (n % m);
    }

    public abstract void tick(HashMap<Entity, Double> distances);
    public abstract void draw(Graphics g);
}
