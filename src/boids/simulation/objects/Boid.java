package boids.simulation.objects;

import boids.simulation.Vector2D;

import java.awt.*;
import java.util.HashMap;

public class Boid extends Entity {
    protected double angle = 0;
    protected Vector2D velocity = new Vector2D(5*Math.random() - 5, 5*Math.random() - 5);
    private static double separationWeight = 100, alignmentWeight = 200, cohesionWeight = 1;
    private static final double obstacleWeight = 30, predatorWeight = 500;
    protected int maxSpeed = 5;

    protected int cohesionRadius = 40, separationRadius = 12, alignmentRadius = 30, obstacleRadius = 30, predatorRadius = 60;

    public Boid(int x, int y) {
        super(x, y);
        move(velocity);
    }


    public double getAngle() {
        return angle;
    }


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
        g.fillOval(getX()-6, getY()-6, 11, 11);

        g.setColor(Color.black);
        g.drawLine(getX(), getY(), getX() + (int) (7*Math.cos(angle)), getY() + (int) (7*Math.sin(angle)));
    }


    @Override
    public void move(Vector2D velocity) {
        super.move(velocity);
        angle = Math.atan2(velocity.getY(), velocity.getX());
    }

    protected Vector2D getSeparationVector(HashMap<Entity, Double> distances) {
        double sumX=0, sumY=0;

        for(Entity entity: distances.keySet()) {
            if(entity instanceof Boid) {
                double dist = distances.get(entity);
                if(dist > separationRadius) continue;

                sumX += (x-entity.getX())/dist;
                sumY += (y-entity.getY())/dist;
            }
        }
        return new Vector2D(sumX, sumY);
    }


    protected Vector2D getAlignmentVector(HashMap<Entity, Double> distances) {
        double angleSum = 0;
        int numBoids = 0;

        for(Entity entity: distances.keySet()) {
            if(entity instanceof Boid) {
                if(distances.get(entity) > alignmentRadius) continue;
                angleSum += ((Boid) entity).getAngle();
                numBoids++;
            }
        }

        if(numBoids > 0) {
            angleSum /= numBoids;
            return new Vector2D(Math.cos(angleSum), Math.sin(angleSum));
        } else return new Vector2D(0, 0);
    }


    protected Vector2D getCohesionVector(HashMap<Entity, Double> distances) {
        int sumX=0, sumY=0, numBoids = 0;

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
                sumX += (x-entity.getX())/(Math.max(dist, 10) - 9.5);
                sumY += (y-entity.getY())/(Math.max(dist, 10) - 9.5);
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
}
