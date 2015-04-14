package project4.cells;

import project4.Project4;

import java.awt.*;

public class Brick {
    private int brickLength, x, y=0;

    public Brick(int x, int brickLength) {
        this.brickLength = brickLength;
        this.x = x;
    }


    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.RED);

        for(int i=0; i<brickLength; i++)
            g.fillRect(offsetX + (x+i)*Project4.CELL_SIZE + (int) (Project4.CELL_SIZE * 0.15), offsetY + y*Project4.CELL_SIZE + (int) (Project4.CELL_SIZE * 0.15),
                    (int) (0.7 * Project4.CELL_SIZE), (int) (0.7 * Project4.CELL_SIZE));
    }
}
