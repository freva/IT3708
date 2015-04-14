package project4;

import project4.cells.Agent;
import project4.cells.Brick;

import java.awt.*;


public class Board {
    public static final int DIMENSION_X = 30, DIMENSION_Y = 10;
    private int numberOfIntercepts, numberOfAvoided, numberOfTicks;
    private Agent agent = new Agent();
    private Brick brick = new Brick(12, 3);


    public int getNumberOfIntercepted() {
        return numberOfIntercepts;
    }

    public int getNumberOfAvoided() {
        return numberOfAvoided;
    }

    public int getNumberOfTicks() {
        return numberOfTicks;
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
}
