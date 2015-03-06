package boids.simulation;

import boids.simulation.objects.Boid;
import boids.simulation.objects.Entity;
import boids.simulation.objects.Obstacle;
import boids.simulation.objects.Predator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Simulation extends JPanel {
    private static Simulation simulation;
    private final ArrayList<Entity> entities = new ArrayList<>();


    private Simulation(int numBoids) {
        setBackground(Color.white);

        for(int i=0; i<numBoids; i++) {
            addEntity(new Boid());
        }
    }

    public static Simulation createSimulation(int numBouds) {
        simulation = new Simulation(numBouds);
        return simulation;
    }

    public static Simulation getSimulation() {
        return simulation;
    }


    public void run() {
        Entity entitiesCopy[];
        while(true) {
            long time = System.currentTimeMillis();

            entitiesCopy = entities.toArray(new Entity[entities.size()]);

            HashMap<Entity, HashMap<Entity, Double>> distanceMatrix = new HashMap<>();
            for(Entity e: entitiesCopy) distanceMatrix.put(e, new HashMap<>());

            for(int i=0; i<entitiesCopy.length; i++) {
                for(int j=i+1; j<entitiesCopy.length; j++) {
                    double dist = entitiesCopy[i].distanceTo(entitiesCopy[j]);
                    distanceMatrix.get(entitiesCopy[i]).put(entitiesCopy[j], dist);
                    distanceMatrix.get(entitiesCopy[j]).put(entitiesCopy[i], dist);
                }
            }

            Arrays.stream(entitiesCopy).parallel().forEach(b -> b.tick(distanceMatrix.get(b)));
            Arrays.stream(entitiesCopy).parallel().forEach(Entity::move);

            try {
                long sleepTime = 40 - (System.currentTimeMillis()-time);
                if(sleepTime > 0) Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Entity e: entities)
            e.draw(g);
    }


    private synchronized void addEntity(Entity e) {
        entities.add(e);
    }


    public void addObstacle() {
        addEntity(new Obstacle());
    }

    public synchronized void removeObstacles() {
        for(int i=entities.size()-1; i>=0; i--) {
            if(entities.get(i) instanceof Obstacle) entities.remove(i);
        }
    }


    public void addPredator() {
        addEntity(new Predator());
    }

    public synchronized void removePredators() {
        for(int i=entities.size()-1; i>=0; i--) {
            if(entities.get(i) instanceof Predator) entities.remove(i);
        }
    }
}
