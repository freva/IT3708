package project1.simulation;

public class Vector2D {
    private double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public Vector2D normalize() {
        double len = getSpeed();
        if(len == 0) return this;
        this.x = x/len;
        this.y = y/len;
        return this;
    }


    public double getSpeed() {
        return Math.sqrt(x*x + y*y);
    }


    public Vector2D scale(double d) {
        return new Vector2D(x*d, y*d);
    }


    public Vector2D sum(Vector2D other) {
        return new Vector2D(x+other.getX(), y+other.getY());
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public String toString() {
        return "X: " + x + " | Y: " + y;
    }
}
