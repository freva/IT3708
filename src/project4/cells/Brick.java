package project4.cells;

import project4.Board;
import project4.Project4;

import java.awt.*;

public class Brick {
    private int brickLength, x, y;

    public Brick() {
        refresh();
    }

    public Brick(int x, int y, int brickLength) {
        this.brickLength = brickLength;
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void tick() {
        y++;
    }

    public void refresh() {
        brickLength = 1 + (int) (Math.random()*6);
        x = (int) (Math.random()*(Board.DIMENSION_X-brickLength));
        y = 0;
    }

    public int getBrickLength() {
        return brickLength;
    }

    public Brick getClone() {
        return new Brick(x, y, brickLength);
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.RED);

        for(int i=0; i<brickLength; i++)
            g.fillRect(offsetX + (x+i)*Project4.CELL_SIZE + (int) (Project4.CELL_SIZE * 0.15), offsetY + y*Project4.CELL_SIZE + (int) (Project4.CELL_SIZE * 0.15),
                    (int) (0.7 * Project4.CELL_SIZE), (int) (0.7 * Project4.CELL_SIZE));
    }
}
