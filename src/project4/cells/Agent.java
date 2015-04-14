package project4.cells;

import project4.Board;
import project4.Project4;

import java.awt.*;

public class Agent {
    public static final int AGENT_LENGTH = 5;
    private int x = (int) (Math.random()*(Board.DIMENSION_X-AGENT_LENGTH));


    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.BLUE);

        for(int i=0; i<AGENT_LENGTH; i++)
            g.fillRect(offsetX + (x+i)*Project4.CELL_SIZE + (int) (Project4.CELL_SIZE * 0.15), offsetY + (int) (Project4.CELL_SIZE * 0.15), (int) (0.7 * Project4.CELL_SIZE), (int) (0.7 * Project4.CELL_SIZE));
    }
}
