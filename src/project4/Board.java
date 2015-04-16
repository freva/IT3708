package project4;

import project4.cells.Agent;
import project4.cells.Brick;

import java.awt.*;


public class Board {
    public static final int DIMENSION_X = 30, DIMENSION_Y = 10;
    private int numberOfIntercepts, numberOfAvoided, numberOfTicks;
    private Agent agent = new Agent();
    private Brick brick = new Brick();


    public int getNumberOfIntercepted() {
        return numberOfIntercepts;
    }

    public int getNumberOfAvoided() {
        return numberOfAvoided;
    }

    public int getNumberOfTicks() {
        return numberOfTicks;
    }


    public void move(Action action) {
        agent.setX((agent.getX()+action.getVector()+DIMENSION_X)%DIMENSION_X);
    }


    public double[] sense() {
        double[] sensing = new double[Agent.AGENT_LENGTH];

        for(int i=0; i<Agent.AGENT_LENGTH; i++) {
            int posX = (agent.getX()+i)%DIMENSION_X;

            sensing[i] = posX >= brick.getX() && posX <= (brick.getX()+brick.getBrickLength())%DIMENSION_X ? 1 : 0;
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
    }


    public enum Action {
        FOUR_TO_LEFT(-4), THREE_TO_LEFT(-3), TWO_TO_LEFT(-2), ONE_TO_LEFT(-1), NONE(0), ONE_TO_RIGHT(1), TWO_TO_RIGHT(2), THREE_TO_RIGHT(3), FOUR_TO_RIGHT(4);

        private int vector;
        Action(int vector) {
            this.vector = vector;
        }

        public int getVector() {
            return vector;
        }
    }
}
