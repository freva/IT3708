package boids.simulation;

import boids.Main;
import boids.simulation.objects.Boid;
import boids.simulation.objects.Entity;
import boids.simulation.objects.Obstacle;
import boids.simulation.objects.Predator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Simulation extends JPanel {
    private static Simulation simulation;
    private final ArrayList<Entity> entities = new ArrayList<>();


    private Simulation() {
        setBackground(Color.white);

        for(int i=0; i<400; i++) {
            addEntity(new Boid((int) (Math.random()* Main.SIMULATION_WIDTH), (int) (Math.random()*Main.SIMULATION_HEIGHT)));
        }
    }

    public static Simulation getSimulation() {
        if(simulation == null) simulation = new Simulation();
        return simulation;
    }


    public void run() {
        ArrayList<Entity> entitiesCopy;
        while(true) {
            long time = System.currentTimeMillis();

            synchronized (entities) {
                entitiesCopy = (ArrayList<Entity>) entities.clone();
            }

            HashMap<Entity, HashMap<Entity, Double>> distanceMatrix = new HashMap<>();

            for(Entity e: entitiesCopy) distanceMatrix.put(e, new HashMap<>());

            for(int i=0; i<entitiesCopy.size(); i++) {
                for(int j=i+1; j<entitiesCopy.size(); j++) {
                    double dist = entitiesCopy.get(i).distanceTo(entitiesCopy.get(j));
                    distanceMatrix.get(entitiesCopy.get(i)).put(entitiesCopy.get(j), dist);
                    distanceMatrix.get(entitiesCopy.get(j)).put(entitiesCopy.get(i), dist);
                }
            }

            entitiesCopy.parallelStream().forEach(b -> b.tick(distanceMatrix.get(b)));

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
        addEntity(new Obstacle((int) (Math.random() * Main.SIMULATION_WIDTH), (int) (Math.random() * Main.SIMULATION_HEIGHT)));
    }

    public synchronized void removeObstacles() {
        for(int i=entities.size()-1; i>=0; i--) {
            if(entities.get(i) instanceof Obstacle) entities.remove(i);
        }
    }


    public void addPredator() {
        addEntity(new Predator((int) (Math.random() * Main.SIMULATION_WIDTH), (int) (Math.random() * Main.SIMULATION_HEIGHT)));
    }

    public synchronized void removePredators() {
        for(int i=entities.size()-1; i>=0; i--) {
            if(entities.get(i) instanceof Predator) entities.remove(i);
        }
    }
}
