package project4;

import project4.cells.Agent;
import project4.cells.Brick;

import java.awt.*;


public class Board {
    public static final int DIMENSION_X = 30, DIMENSION_Y = 15;
    private int numberOfIntercepts, numberOfAvoided, numberOfTicks, numberOfCrashes, numberOfMisses;
    private Agent agent = new Agent();
    private Brick brick = new Brick();


    public int getNumberOfIntercepted() {
        return numberOfIntercepts;
    }

    public int getNumberOfAvoided() {
        return numberOfAvoided;
    }

    public int getNumberOfCrashes() {
        return numberOfCrashes;
    }

    public int getNumberOfMisses() {
        return numberOfMisses;
    }

    public int getNumberOfTicks() {
        return numberOfTicks;
    }


    public void move(Action action) {
        if(action == Action.PULL) brick.pull();
        else agent.setX(BeerTracker.getScenario().getNewPosition(agent.getX(), action.getVector()));
    }


    public void tick() {
        numberOfTicks++;

        if(brick.getY() == DIMENSION_Y-1) {
            if(brick.getBrickLength() < 5)
                if(agent.getX()<=brick.getX() && brick.getX()+brick.getBrickLength()<=agent.getX()+Agent.AGENT_LENGTH) numberOfIntercepts++;
                else numberOfMisses++;
            else
                if(isAgentBrickOverlap()) numberOfCrashes++;
                else numberOfAvoided++;
            brick.refresh();
        } else brick.tick();
    }

    public boolean isAgentBrickOverlap() {
        return !(brick.getX()+brick.getBrickLength()<agent.getX() || agent.getX()+Agent.AGENT_LENGTH<brick.getX());
    }

    public double[] sense() {
        double[] sensing = new double[Agent.AGENT_LENGTH + (BeerTracker.getScenario() == BeerTracker.Scenario.NO_WRAP ? 2 : 0)];

        for(int i=0; i<Agent.AGENT_LENGTH; i++) {
            int posX = (agent.getX()+i)%DIMENSION_X;

            sensing[i] = posX >= brick.getX() && posX <= (brick.getX()+brick.getBrickLength())%DIMENSION_X ? 1 : 0;
        }

        if(BeerTracker.getScenario() == BeerTracker.Scenario.NO_WRAP) {
            sensing[Agent.AGENT_LENGTH] = agent.getX() == 0 ? 1 : 0;
            sensing[Agent.AGENT_LENGTH+1] = agent.getX() == DIMENSION_X-Agent.AGENT_LENGTH ? 1 : 0;
        }

        return sensing;
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.LIGHT_GRAY);
        for (int i=0; i<DIMENSION_X; i++) {
            for (int j=0; j<DIMENSION_Y; j++) {
                g.fillRect(offsetX + i*Project4.CELL_SIZE + (int) (Project4.CELL_SIZE*0.05), offsetY + j*Project4.CELL_SIZE + (int) (Project4.CELL_SIZE*0.05),
                        (int) (Project4.CELL_SIZE*0.9), (int) (Project4.CELL_SIZE*0.9));
            }
        }

        agent.draw(g, offsetX, offsetY + (DIMENSION_Y-1)*Project4.CELL_SIZE);
        brick.draw(g, offsetX, offsetY);
        tick();
    }

    public double getBoardScore() {
        double[] rewards = BeerTracker.getScenario().getRewards();
        return rewards[0] * numberOfIntercepts + rewards[1] * numberOfAvoided + rewards[2] * numberOfCrashes + rewards[3] * numberOfMisses;
    }

    public enum Action {
        FOUR_TO_LEFT(-4), THREE_TO_LEFT(-3), TWO_TO_LEFT(-2), ONE_TO_LEFT(-1), NONE(0), ONE_TO_RIGHT(1), TWO_TO_RIGHT(2), THREE_TO_RIGHT(3), FOUR_TO_RIGHT(4), PULL(0);

        private int vector;
        Action(int vector) {
            this.vector = vector;
        }

        public int getVector() {
            return vector;
        }
    }
}
