package boids.simulation.objects;

import boids.Main;
import boids.simulation.Vector2D;

import java.awt.*;
import java.util.HashMap;

public class Boid extends Entity {
    protected Vector2D velocity = new Vector2D(5*Math.random() - 5, 5*Math.random() - 5);
    private static double separationWeight = 2, alignmentWeight = 0.5, cohesionWeight = 0.01;
    private static final double obstacleWeight = 1.5, predatorWeight = 5;
    protected int maxSpeed = 5;

    protected int cohesionRadius = 60, separationRadius = 15, alignmentRadius = 40, obstacleRadius = 30, predatorRadius = 60;


    @Override
    public void tick(HashMap<Entity, Double> distances) {
        Vector2D separationVector = getSeparationVector(distances).scale(separationWeight);
        Vector2D alignmentVector = getAlignmentVector(distances).scale(alignmentWeight);
        Vector2D cohesionVector = getCohesionVector(distances).scale(cohesionWeight);
        Vector2D obstacleVector = getObstacleVector(distances).scale(obstacleWeight);
        Vector2D predatorVector = getPredatorVector(distances).scale(predatorWeight);

        accelerate(separationVector.sum(alignmentVector).sum(cohesionVector).sum(obstacleVector).sum(predatorVector));
        move(velocity);
    }


    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillOval((int) getX()-6, (int) getY()-6, 11, 11);

        g.setColor(Color.black);
        g.drawLine((int) getX(), (int) getY(), (int) (getX() + 1.2*velocity.getX()), (int) (getY() + 1.2*velocity.getY()));
    }


    public void move(Vector2D velocity) {
        this.x = modulo((int) (this.x + velocity.getX()), Main.SIMULATION_WIDTH);
        this.y = modulo((int) (this.y + velocity.getY()), Main.SIMULATION_HEIGHT);
    }


    public Vector2D getVelocity() {
        return velocity;
    }


    protected Vector2D getSeparationVector(HashMap<Entity, Double> distances) {
        double sumX=0, sumY=0;
        int numBoids = 0;

        for(Entity entity: distances.keySet()) {
            if(entity instanceof Boid && !(entity instanceof Predator)) {
                double dist = distances.get(entity);
                if(dist > separationRadius) continue;

                sumX += (x-entity.getX())/dist;
                sumY += (y-entity.getY())/dist;
                numBoids++;
            }
        }

        if(numBoids > 0) return new Vector2D(sumX/numBoids, sumY/numBoids);
        else return new Vector2D(0, 0);
    }


    protected Vector2D getAlignmentVector(HashMap<Entity, Double> distances) {
        double sumX = 0, sumY = 0;
        int numBoids = 0;

        for(Entity entity: distances.keySet()) {
            if(entity instanceof Boid) {
                if(distances.get(entity) > alignmentRadius) continue;
                sumX += ((Boid) entity).getVelocity().getX();
                sumY += ((Boid) entity).getVelocity().getY();
                numBoids++;
            }
        }

        if(numBoids > 0) return new Vector2D(sumX/numBoids, sumY/numBoids);
        else return new Vector2D(0, 0);
    }


    protected Vector2D getCohesionVector(HashMap<Entity, Double> distances) {
        double sumX = 0, sumY = 0;
        int numBoids = 0;

        for(Entity entity: distances.keySet()) {
            if(entity instanceof Boid && !(entity instanceof Predator)) {
                double dist = distances.get(entity);
                if(dist > cohesionRadius) continue;
                sumX += entity.getX();
                sumY += entity.getY();
                numBoids++;
            }
        }

        if(numBoids > 0) return new Vector2D(sumX/numBoids - x, sumY/numBoids - y);
        else return new Vector2D(0, 0);
    }


    protected Vector2D getObstacleVector(HashMap<Entity, Double> distances) {
        double sumX=0, sumY=0;

        for(Entity entity: distances.keySet()) {
            if(entity instanceof Obstacle) {
                double dist = distances.get(entity);
                if(dist > obstacleRadius) continue;
                sumX += (x-entity.getX())/(Math.max(dist, 10) - 9.8);
                sumY += (y-entity.getY())/(Math.max(dist, 10) - 9.8);
            }
        }
        return new Vector2D(sumX, sumY);
    }


    protected Vector2D getPredatorVector(HashMap<Entity, Double> distances) {
        double sumX=0, sumY=0;

        for(Entity entity: distances.keySet()) {
            if(entity instanceof Predator) {
                double dist = distances.get(entity);
                if(dist > predatorRadius) continue;
                sumX += (x-entity.getX())/dist;
                sumY += (y-entity.getY())/dist;
            }
        }
        return new Vector2D(sumX, sumY);
    }



    protected void accelerate(Vector2D v) {
        velocity = velocity.sum(v);

        if(velocity.getSpeed() > maxSpeed) {
            velocity = velocity.scale(maxSpeed/velocity.getSpeed());
        }
    }


    public static void setSeparationWeight(double separationWeight) {
        Boid.separationWeight = separationWeight;
    }

    public static void setAlignmentWeight(double alignmentWeight) {
        Boid.alignmentWeight = alignmentWeight;
    }

    public static void setCohesionWeight(double cohesionWeight) {
        Boid.cohesionWeight = cohesionWeight;
    }



    private static int modulo(int n, int m) {
        return (n < 0) ? (m - (Math.abs(n) % m) ) %m : (n % m);
    }
}
