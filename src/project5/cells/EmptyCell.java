package project5.cells;

import project5.Project5;

import java.awt.*;

public class EmptyCell {
    private boolean isStart = false;
    private Direction direction = Direction.NONE;

    public EmptyCell() {}

    public EmptyCell(boolean isStart) {
        this.isStart = isStart;
    }


    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public boolean isStart() {
        return isStart;
    }


    public void draw(Graphics g, int offsetX, int offsetY) {
        if(!isStart) g.setColor(Color.LIGHT_GRAY);
        else g.setColor(Color.CYAN);

        g.fillRect(offsetX + (int) (Project5.CELL_SIZE*0.05), offsetY + (int) (Project5.CELL_SIZE*0.05), (int) (Project5.CELL_SIZE*0.9), (int) (Project5.CELL_SIZE*0.9));

        direction.drawDirection(g, offsetX, offsetY);
    }
}
